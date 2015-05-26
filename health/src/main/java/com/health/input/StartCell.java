package com.health.input;

/**
 * 
 * @author daan
 *
 *         A class used to store the startcell of a table in an xls file
 */
public final class StartCell {
	private int startRow;
	private int startColumn;

	/**
	 * constructor for the StartCell
	 * 
	 * @param startRow
	 *            is the row of the start cell
	 * @param startColumn
	 *            is the column of the start cell
	 */
	public StartCell(int startRow, int startColumn) {
		this.startRow = startRow;
		this.startColumn = startColumn;
	}

	/**
	 * getter for the row
	 * 
	 * @return startRow
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * getter for the column
	 * 
	 * @return startColumn
	 */
	public int getStartColumn() {
		return startColumn;
	}

}
