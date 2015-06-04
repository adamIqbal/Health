package com.health.operations;


public class ColumnConnection {

	private String column1;
	private String column2;
	private String newName;

	public ColumnConnection(final String column1, final String column2, final String newName) {
		this.setColumn1(column1);
		this.setColumn2(column2);
		this.setNewName(newName);
	}

	/**
	 * @return the newName
	 */
	public final String getNewName() {
		return newName;
	}

	/**
	 * @param newName
	 *            the newName to set
	 */
	public final void setNewName(final String newName) {
		this.newName = newName;
	}

	/**
	 * @return the column2
	 */
	public final String getColumn2() {
		return column2;
	}

	/**
	 * @param column2
	 *            the column2 to set
	 */
	public final void setColumn2(final String column2) {
		this.column2 = column2;
	}

	/**
	 * @return the column1
	 */
	public final String getColumn1() {
		return column1;
	}

	/**
	 * @param column1
	 *            the column1 to set
	 */
	public final void setColumn1(final String column1) {
		this.column1 = column1;
	}
}
