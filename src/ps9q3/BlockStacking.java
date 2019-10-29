package ps9q3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BlockStacking {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Bad run argument. One line only.");
		}
		BlockStacking stacking = new BlockStacking();
		ArrayList<BlockDimensions> blocks = stacking.getBlockArray(new File(args[0]));
		
		// TODO: something else
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
				int length = Integer.parseInt(dims[0]), width = Integer.parseInt(dims[1]),
						height = Integer.parseInt(dims[2]);

				// TODO: Optimize this to remove duplicate entries?
				// Adds all 6 orientations possible for that one block into our list
				out.add(new BlockDimensions(length, width, height));
				out.add(new BlockDimensions(length, height, width));
				out.add(new BlockDimensions(width, length, height));
				out.add(new BlockDimensions(width, height, length));
				out.add(new BlockDimensions(height, length, width));
				out.add(new BlockDimensions(height, width, length));
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
