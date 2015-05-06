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
    /**
     * Tests whether {@link Table#Table(Iterable)} throws a
     * {@link NullPointerException} when given a null reference.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenColumnsNull_throwsNullPointerException() {
        new Table(null);
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with null
     * elements.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWithNullColumn_throwsIllegalArgumentException() {
        Iterable<Column> columns = Arrays.asList((Column) null);

        new Table(columns);
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} sets the columns when given
     * an empty {@link Iterable} so that {@link Table#getColumns()} returns an
     * {@link Iterable} instance.
     */
    @Test
    public void constructor_givenColumnsEmpty_setsColumns() {
        Column[] expected = new Column[] {};
        Iterable<Column> columns = Arrays.asList(expected);

        Table table = new Table(columns);

        Iterable<Column> actual = table.getColumns();
        assertThat(actual, hasItems(expected));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} sets the columns when given
     * an {@link Iterable} so that {@link Table#getColumns()} returns an
     * {@link Iterable} that contains the same elements.
     */
    @Test
    public void constructor_givenColumns_setsColumns() {
        Column[] expected = new Column[] { mock(Column.class) };
        Iterable<Column> columns = Arrays.asList(expected);

        Table table = new Table(columns);

        Iterable<Column> actual = table.getColumns();
        assertThat(actual, hasItems(expected));
    }

    /**
     * Tests whether {@link Table#getColumn(String)} returns the right
     * {@link Column} when given the name of the column.
     */
    @Test
    public void getColumn_givenNameOfExistingColumn_returnsColumn() {
        Column column1 = mock(Column.class);
        Column column2 = mock(Column.class);
        Column column3 = mock(Column.class);
        Column column4 = mock(Column.class);
        when(column1.getName()).thenReturn("col1");
        when(column2.getName()).thenReturn("col2");
        when(column3.getName()).thenReturn("col3");
        when(column4.getName()).thenReturn("col4");

        Iterable<Column> columns = Arrays.asList(column1, column2, column3,
                column4);
        Table table = new Table(columns);

        Column expected = column3;
        Column actual = table.getColumn("col3");

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Table#getColumn(String)} returns null when given the
     * name of the column that does not exist.
     */
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
