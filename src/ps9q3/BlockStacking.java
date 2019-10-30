package ps9q3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BlockStacking {

	public static void main(String[] args) {
		// Catch bad arguments
		if (args.length != 1) {
			throw new IllegalArgumentException("Bad run argument. One line only.");
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

		// Load first entry into our DP table.
		dpTable[0] = blocks.get(0).height();

		for (int i = 1; i < blocks.size(); i++) {
			int maxHeight = 0;
			int index = -1;
			
			for (int j = 0; j < i; j++) {
				if (blocks.get(i).canStackOnTop(blocks.get(j))) {
//					System.out.print(blocks.get(i) + " can stack on top of " + blocks.get(j));
					if (dpTable[j] > maxHeight) {
//						System.out.print("HIT");
						index = j;
						maxHeight = dpTable[j];
					}
				}
			}
			
			System.out.println((index >= 0 ? blocks.get(index) : "") + " " + blocks.get(i).toString());
			dpTable[i] = maxHeight + blocks.get(i).height();
		}

		int maxStackHeight = -1;
		System.out.println(Arrays.toString(dpTable));
		for (int stackHeight : dpTable) {
			maxStackHeight = Math.max(maxStackHeight, stackHeight);
		}
		System.out.println(blocks);
		System.out.println(maxStackHeight);
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
				int l = Integer.parseInt(dims[0]), w = Integer.parseInt(dims[1]),
						h = Integer.parseInt(dims[2]);

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
			throw new RuntimeException("Invalid filepath");
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Data format is invalid");
		}
	}

}
