package com.health;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Table {
	private Map<String, Column> columnMap;
	private Collection<Record> records;
	
	public Table(Iterable<Column> columns) {
		Objects.requireNonNull(columns, "Argument columns cannot be null");
		
		this.columnMap = new HashMap<String, Column>();
		this.records = new ArrayList<Record>();
		
		for(Column column : columns) {
			if (column == null) {
				throw new IllegalArgumentException("Argument columns olumns cannot not contain null references");
			}
			
			this.columnMap.put(column.getName(), column);
		}
	}

	/**
	 * Returns a @see Iterable<Column> containing all columns in this table.
	 * 
	 * @return the collection of columns of this table. 
	 */
	public Iterable<Column> getColumns() {
		return this.columnMap.values();
	}
	
	public void addRecord(Record record) {
		Objects.requireNonNull(record, "Argument record cannot be null");
		
		this.records.add(record);
	}

	public void removeRecord(Record record) {
		this.records.remove(record);		
	}

	public Iterable<Record> getRecords() {
		return this.records;
	}

	public Column getColumn(String name) {
		return this.columnMap.get(name);
	}
}
