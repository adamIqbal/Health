package com.health;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Unit test for Table.
 */
public class TableTest {
	private static Table arrangeTable() {
		Column column = mock(Column.class);
		Iterable<Column> columns = Arrays.asList(column);
		
		Table table = new Table(columns);
		
		return table;
	}
	
	private static Record arrangeRecord() {
		Record record = mock(Record.class);
		
		// TODO: Mock any potential important methods of record
		
		return record;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_givenColumnsNull_throwsIllegalArgumentException() {
		new Table(null);
	}
	
	@Test
	public void constructor_givenColumnsEmpty_setsColumns() {
		Iterable<Column> expected = Arrays.asList();
		
		Table table = new Table(expected);
		
		Iterable<Column> actual = table.getColumns();
		assertEquals(expected, actual);
	}

	@Test
	public void constructor_givenColumns_setsColumns() {
		Iterable<Column> expected = Arrays.asList(mock(Column.class));
		
		Table table = new Table(expected);
		
		Iterable<Column> actual = table.getColumns();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getColumn_givenNameOfExistingColumn_returnsColumn() {
		Column expected = mock(Column.class);
		when(expected.getName()).thenReturn("col1");
		
		Iterable<Column> columns = Arrays.asList(expected);
		Table table = new Table(columns);
		
		Column actual = table.getColumn("col1");
		
		assertEquals(expected, actual);
	}

	@Test
	public void getColumn_givenNameOfNonexistentColumn_returnsNull() {
		Column column = mock(Column.class);
		when(column.getName()).thenReturn("col1");
		
		Iterable<Column> columns = Arrays.asList(column);
		Table table = new Table(columns);
		
		Column actual = table.getColumn("col2");
		
		assertNull(actual);
	}

	@Test(expected=IllegalArgumentException.class)
	public void addRecord_givenRecordNull_throwsIllegalArgumentException() {
		Table table = arrangeTable();
		
		table.addRecord(null);
	}
	
	@Test
	public void addRecord_givenRecord_addsRecord() {
		Table table = arrangeTable();
		Record record = arrangeRecord();
				
		table.addRecord(record);

		assertThat(table.getRecords(), hasItem(record));
	}
	
	@Test
	public void removeRecord_givenRecordExists_removesRecord() {
		Table table = arrangeTable();
		Record record = arrangeRecord();		
		table.addRecord(record);
		
		table.removeRecord(record);

		assertThat(table.getRecords(), not(hasItem(record)));
	}
	
	@Test
	public void removeRecord_givenRecordDoesNotExist_doesNotRemoveRecords() {
		Table table = arrangeTable();
		Record record1 = arrangeRecord();	
		Record record2 = arrangeRecord();		
		table.addRecord(record1);
		
		table.removeRecord(record2);

		assertThat(table.getRecords(), hasItems(record1));
	}

	@Test
	public void removeRecord_givenRecordNull_doesNotRemoveRecords() {
		Table table = arrangeTable();
		Record record = arrangeRecord();		
		table.addRecord(record);
		
		table.removeRecord(null);

		assertThat(table.getRecords(), hasItems(record));
	}
}
