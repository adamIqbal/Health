package com.health.operations;

import com.health.Column;

public class ColumnConnection {

	private String column1;
	private String column2;
	private String newName;
	
	public ColumnConnection(String column1, String column2, String newName){
		this.setColumn1(column1);
		this.setColumn2(column2);
		this.setNewName(newName);
	}

	/**
	 * @return the newName
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * @param newName the newName to set
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}

	/**
	 * @return the column2
	 */
	public String getColumn2() {
		return column2;
	}

	/**
	 * @param column2 the column2 to set
	 */
	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	/**
	 * @return the column1
	 */
	public String getColumn1() {
		return column1;
	}

	/**
	 * @param column1 the column1 to set
	 */
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
}
