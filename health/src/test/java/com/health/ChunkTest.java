package com.health;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Unit test for Chunk.
 */
public class ChunkTest {
	/**
	 * Tests whether {@link Chunk#addRecord(Record)} throws a {@link NullPointerException}
	 * when given a null reference.
	 */
	@Test(expected=NullPointerException.class)
	public void addRecord_givenRecordNull_throwsNullPointerException() {
		Chunk chunk = new Chunk();
		Record record = null;
		
		chunk.addRecord(record);
	}

	/**
	 * Tests whether {@link Chunk#addRecord(Record)} adds the given record
	 * when given a valid record.
	 */
	@Test
	public void addRecord_givenRecord_addsRecord() {
		Chunk chunk = new Chunk();
		Record record = mock(Record.class);
		
		chunk.addRecord(record);

		assertThat(chunk.getRecords(), hasItem(record));
	}

	/**
	 * Tests whether {@link Chunk#removeRecord(Record)} removes the given record
	 * when given a record that was already added.
	 */
	@Test
	public void removeRecord_givenRecordExists_removesRecord() {
		Chunk chunk = new Chunk();
		Record record = mock(Record.class);		
		chunk.addRecord(record);
		
		chunk.removeRecord(record);

		assertThat(chunk.getRecords(), not(hasItem(record)));
	}

	/**
	 * Tests whether {@link Chunk#removeRecord(Record)} does not remove any records
	 * when given a record that was not added.
	 */
	@Test
	public void removeRecord_givenRecordDoesNotExist_doesNotRemoveRecords() {
		Chunk chunk = new Chunk();
		Record record1 = mock(Record.class);	
		Record record2 = mock(Record.class);		
		chunk.addRecord(record1);
		
		chunk.removeRecord(record2);

		assertThat(chunk.getRecords(), hasItems(record1));
	}

	/**
	 * Tests whether {@link Chunk#removeRecord(Record)} does not remove any records
	 * when given a null reference.
	 */
	@Test
	public void removeRecord_givenRecordNull_doesNotRemoveRecords() {
		Chunk chunk = new Chunk();
		Record record = mock(Record.class);		
		chunk.addRecord(record);
		
		chunk.removeRecord(null);

		assertThat(chunk.getRecords(), hasItems(record));
	}
}
