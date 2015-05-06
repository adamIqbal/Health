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
	@Test(expected=NullPointerException.class)
	public void constructor_givenColumnsNull_throwsNullPointerException() {
		new Table(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_givenColumnsWithNullColumn_throwsIllegalArgumentException() {
		Iterable<Column> columns = Arrays.asList((Column)null);
		
		new Table(columns);
	}
	
	@Test
	public void constructor_givenColumnsEmpty_setsColumns() {
		Column[] expected = new Column[]{ };
		Iterable<Column> columns = Arrays.asList(expected);
		
		Table table = new Table(columns);
		
		Iterable<Column> actual = table.getColumns();
		assertThat(actual, hasItems(expected));
	}

	@Test
	public void constructor_givenColumns_setsColumns() {
		Column[] expected = new Column[]{ mock(Column.class) };
		Iterable<Column> columns = Arrays.asList(expected);
		
		Table table = new Table(columns);
		
		Iterable<Column> actual = table.getColumns();
		assertThat(actual, hasItems(expected));
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
}
