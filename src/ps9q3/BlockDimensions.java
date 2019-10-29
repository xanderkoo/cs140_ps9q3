package ps9q3;

/**
 * Stores length, width, height of block. Comparable so an array can be sorted.
 * 
 * @author xander
 *
 */
public class BlockDimensions implements Comparable<BlockDimensions> {

	/** Dimensions of block */
	private int length, width, height;

	/**
	 * Constructor for Dimensions object
	 * 
	 * @param length
	 * @param width
	 * @param height
	 */
	public BlockDimensions(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return length of block
	 */
	public int length() {
		return length;
	}

	/**
	 * @return width of block
	 */
	public int width() {
		return width;
	}

	/**
	 * @return height of block
	 */
	public int height() {
		return height;
	}

	/**
	 * Compares block dimension objects by length, then by width if equal.
	 * 
	 * @return -1 if this length is greater than other length, 0 if equal, 1 if less
	 */
	public int compareTo(BlockDimensions bd) {
		// TODO: double check to see if this is correct
		int comp = ((Integer) this.length).compareTo(bd.length());
		return (comp == 0) ? ((Integer) this.width).compareTo(bd.width()) : comp;
	}

	/**
	 * Checks if this block can stack on another block with given dimensions. Note:
	 * different orientations are not accounted for because all possible block
	 * orientations will be included in the data.
	 * 
	 * @param bd dimensions of the other block
	 * @return true if can, false if can't
	 */
	public boolean canStackOnTop(BlockDimensions bd) {
		return (this.length < bd.length() && this.width < bd.length());
	}
}
