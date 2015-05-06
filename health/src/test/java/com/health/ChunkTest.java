package com.health;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Unit test for Table.
 */
public class ChunkTest {
	@Test(expected=NullPointerException.class)
	public void addRecord_givenRecordNull_throwsNullPointerException() {
		Chunk chunk = new Chunk();
		Record record = null;
		
		chunk.addRecord(record);
	}
	
	@Test
	public void addRecord_givenRecord_addsRecord() {
		Chunk chunk = new Chunk();
		Record record = mock(Record.class);
		
		chunk.addRecord(record);

		assertThat(chunk.getRecords(), hasItem(record));
	}
	
	@Test
	public void removeRecord_givenRecordExists_removesRecord() {
		Chunk chunk = new Chunk();
		Record record = mock(Record.class);		
		chunk.addRecord(record);
		
		chunk.removeRecord(record);

		assertThat(chunk.getRecords(), not(hasItem(record)));
	}
	
	@Test
	public void removeRecord_givenRecordDoesNotExist_doesNotRemoveRecords() {
		Chunk chunk = new Chunk();
		Record record1 = mock(Record.class);	
		Record record2 = mock(Record.class);		
		chunk.addRecord(record1);
		
		chunk.removeRecord(record2);

		assertThat(chunk.getRecords(), hasItems(record1));
	}

	@Test
	public void removeRecord_givenRecordNull_doesNotRemoveRecords() {
		Chunk chunk = new Chunk();
		Record record = mock(Record.class);		
		chunk.addRecord(record);
		
		chunk.removeRecord(null);

		assertThat(chunk.getRecords(), hasItems(record));
	}
}
