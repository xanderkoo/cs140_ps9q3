package ps9q3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Block stacking algorithm,
 * 
 * @author franka1
 *
 */
public class BlockStacking {

	/**
	 * takes an input and output file and runs the algorithm on the blocks in the
	 * input file. Dumps an optimal solution into the output file
	 * 
	 * @param args the input and output files, separated by a space
	 */
	public static void main(String[] args) {
		// Catch bad arguments
		if (args.length != 2) {
			throw new IllegalArgumentException("Bad run arguments");

		}
		BlockStacking stacking = new BlockStacking();

		// Get array of all possible orientations for all blocks.
		ArrayList<BlockDimensions> blocks = stacking.getBlockArray(new File(args[0]));

		// We assume that since our blocks array is sorted, there is no block_p that can
		// stack upon block_q, s.t. p < q. We can justify this below:
//		for (int i = 1; i < blocks.size(); i++) {
//			for (int j = 0; j < i; j++) {
//				System.out.println("blocks.get(" + j + ").canStackOnTop(blocks.get(" + i + ")) = "
//						+ blocks.get(j).canStackOnTop(blocks.get(i)));
//			}
//		}

		// Our DP table is 0-indexed
		int[] dpTable = new int[blocks.size()];
		int[] pointerTable = new int[blocks.size()];

		// Load first entry into our DP table.
		dpTable[0] = blocks.get(0).height();
		pointerTable[0] = -1;

		/*
		 * Fill in the dp table, each entry is the maximum height of a tower using a
		 * series of blocks given that block i is on the top of the tower.
		 * 
		 * We do this by getting the maximum stack height of all DP table entries where
		 * block j is at the top of the stack, s.t. j < i, and block i fits on top of
		 * block j
		 */
		for (int i = 1; i < blocks.size(); i++) {
			int maxHeight = 0;
			int index = -1;

			// for every block j before i, check if i fits on top of j and check if this
			// table entry is the largest found so far
			for (int j = 0; j < i; j++) {
				if (blocks.get(i).canStackOnTop(blocks.get(j))) {
					if (dpTable[j] > maxHeight) {
						index = j; // keep track of index of tallest stack
						maxHeight = dpTable[j];
					}
				}
			}
			// now this table entry points back to the entry it was added onto (to
			// reconstruct the table)
			pointerTable[i] = index;

			// add the height of block i onto the tallest tower found before it
			dpTable[i] = maxHeight + blocks.get(i).height();
		}

		// find the max value in the whole DP table, this is the tallest tower possible
		// given all the blocks
		int maxStackHeight = -1;
		int maxIndex = -1;
		for (int i = 0; i < dpTable.length; i++) {
			if (dpTable[i] >= maxStackHeight) {
				maxStackHeight = dpTable[i];
				maxIndex = i;
			}
		}

		// reconstruct the optimal solution by tracing the index pointers starting at
		// the index of tallest tower
		ArrayList<BlockDimensions> answerBlocks = new ArrayList<BlockDimensions>();
		int pointerIndex = maxIndex;

		// keep going back to the next pointer until it is not pointing anywhere
		// (default value of -1)
		while (pointerIndex != -1) {
			answerBlocks.add(blocks.get(pointerIndex));
			pointerIndex = pointerTable[pointerIndex];
		}

		// write the solution to the output file
		try {
			PrintWriter outFileWriter = new PrintWriter(args[1]);
			outFileWriter.println(answerBlocks.size());
			for (int i = answerBlocks.size() - 1; i >= 0; i--) {
				outFileWriter.println(answerBlocks.get(i).toString());
			}
			outFileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException("Invalid output filepath");
		}
	}

	/**
	 * Returns a sorted list of all (TODO: unique?) orientations of each block type.
	 * 
	 * @param f file object of the data file
	 * @return sorted file
	 */
	private ArrayList<BlockDimensions> getBlockArray(File f) {
		try {
			Scanner s = new Scanner(f);

			// Get number of lines
			int numLines = Integer.parseInt(s.nextLine());
			ArrayList<BlockDimensions> out = new ArrayList<BlockDimensions>(numLines);

			// Reads thru the data file
			while (s.hasNextLine()) {

				// Gets dimensions of that block
				String[] dims = s.nextLine().split(" ");
				if (dims.length != 3)
					throw new NoSuchElementException();

				// Grab the length width and height
				int l = Integer.parseInt(dims[0]), w = Integer.parseInt(dims[1]), h = Integer.parseInt(dims[2]);

				// Adds all <=6 unique orientations possible for that one block into our list
				if (l == w && w == h) { // A cube
					out.add(new BlockDimensions(l, w, h));
				} else if (l == w) {
					out.add(new BlockDimensions(l, w, h));
					out.add(new BlockDimensions(l, h, w));
					out.add(new BlockDimensions(h, l, w));
				} else if (w == h) {
					out.add(new BlockDimensions(l, w, h));
					out.add(new BlockDimensions(w, l, h));
					out.add(new BlockDimensions(w, h, l));
				} else if (h == l) {
					out.add(new BlockDimensions(l, w, h));
					out.add(new BlockDimensions(l, h, w));
					out.add(new BlockDimensions(w, l, h));
				} else { // All sides different
					out.add(new BlockDimensions(l, w, h));
					out.add(new BlockDimensions(l, h, w));
					out.add(new BlockDimensions(w, l, h));
					out.add(new BlockDimensions(w, h, l));
					out.add(new BlockDimensions(h, l, w));
					out.add(new BlockDimensions(h, w, l));
				}
			}

			// Sort it by length, secondarily by width
			Collections.sort(out);

			s.close();

			return out;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Invalid input filepath");
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Data input is invalid");
		}
	}

}
