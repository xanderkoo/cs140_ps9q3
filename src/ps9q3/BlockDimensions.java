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
	 * @return Compares block dimension objects. -1 if this length is greater than
	 *         other length, 0 if equal, 1 if less than
	 */
	public int compareTo(BlockDimensions bd) {
		// TODO: make it secondarily compare by width, so that we end up with a sorted
		// list where "larger" elements can't be stacked on smaller ones
		return ((Integer) this.length).compareTo(bd.length());
	}
}
