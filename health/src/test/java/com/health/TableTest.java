package com.health;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Unit test for Table.
 */
public class TableTest {
	private static Table arrangeTable() {
		Collection<Column> columns = new ArrayList<Column>();
		columns.add(mock(Column.class));
		
		Table table = new Table(columns);
		
		return table;
	}
	
	private static Record arrangeRecord() {
		Record record = mock(Record.class);
		
		// TODO: Mock any potential important methods of record
		
		return record;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorThrowsArgumentNullExceptionGivenColumnsNull() {
		new Table(null);
	}
	
	@Test
	public void testConstructorSetsColumns() {
		Collection<Column> expected = new ArrayList<Column>();
		expected.add(mock(Column.class));
		
		Table table = new Table(expected);
		
		Collection<Column> actual = table.getColumns();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetRecordsDoesNotReturnNull() {
		Table table = arrangeTable();

		assertNotEquals(table.getRecords(), null);
	}
	
	@Test
	public void testAddRecordAddsRecord() {
		Table table = arrangeTable();
		Record record = arrangeRecord();
				
		table.addRecord(record);

		assertThat(table.getRecords(), hasItem(record));
	}
	
	@Test
	public void testRemoveRecordRemovesRecord() {
		Table table = arrangeTable();
		Record record = arrangeRecord();		
		table.addRecord(record);
		
		table.removeRecord(record);

		assertThat(table.getRecords(), not(hasItem(record)));
	}
}
