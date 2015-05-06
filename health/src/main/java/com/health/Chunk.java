package com.health;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Chunk {
	private Collection<Record> records;
	
	public Chunk() {
		this.records = new ArrayList<Record>();
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
}
