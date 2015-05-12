package com.health;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit test for Table.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Column.class, Record.class })
public class TableTest {
    private Column[] columns;
    private Table table;
    private Record record;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        // Create mock columns
        Column column1 = mock(Column.class);
        Column column2 = mock(Column.class);
        Column column3 = mock(Column.class);
        Column column4 = mock(Column.class);

        // Mock column names
        when(column1.getName()).thenReturn("abc");
        when(column2.getName()).thenReturn("column2");
        when(column3.getName()).thenReturn("cda");
        when(column4.getName()).thenReturn("column4");

        // Mock column indices
        when(column1.getIndex()).thenReturn(0);
        when(column2.getIndex()).thenReturn(1);
        when(column3.getIndex()).thenReturn(2);
        when(column4.getIndex()).thenReturn(3);

        columns = new Column[] {
                column1,
                column2,
                column3,
                column4
        };

        table = new Table(Arrays.asList(columns));

        record = mock(Record.class);
        when(record.getTable()).thenReturn(table);
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws a
     * {@link NullPointerException} when given a null reference.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenColumnsNull_throwsNullPointerException() {
        new Table((Iterable<Column>) null);
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with null
     * elements.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWithNullColumn_throwsIllegalArgumentException() {
        new Table(Arrays.asList((Column) null));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an empty {@link Iterable}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsEmpty_throwsIllegalArgumentException() {
        new Table(new ArrayList<Column>());
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with
     * columns whose indices do not start at zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWithIndicesStartingAboveZero_throwsIllegalArgumentException() {
        Column column = mock(Column.class);
        when(column.getName()).thenReturn("column1");
        when(column.getIndex()).thenReturn(1);

        new Table(Arrays.asList(column));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with
     * columns whose indices do not for a sequence.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWithMissingIndices_throwsIllegalArgumentException() {
        Column column1 = mock(Column.class);
        Column column2 = mock(Column.class);
        when(column1.getName()).thenReturn("column1");
        when(column2.getName()).thenReturn("column2");
        when(column1.getIndex()).thenReturn(0);
        when(column1.getIndex()).thenReturn(2);

        new Table(Arrays.asList(column1, column2));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with
     * columns whose indices contain duplicates.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWitDuplicateIndices_throwsIllegalArgumentException() {
        Column column1 = mock(Column.class);
        Column column2 = mock(Column.class);
        when(column1.getName()).thenReturn("column1");
        when(column2.getName()).thenReturn("column2");
        when(column1.getIndex()).thenReturn(0);
        when(column1.getIndex()).thenReturn(0);

        new Table(Arrays.asList(column1, column2));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} sets the columns when given
     * an {@link Iterable} so that {@link Table#getColumns()} returns an
     * {@link Iterable} that contains the same elements.
     */
    @Test
    public void constructor_givenValidColumns_setsColumns() {
        Table table = new Table(Arrays.asList(columns));

        assertThat(table.getColumns(),
                IsIterableContainingInAnyOrder
                        .<Column> containsInAnyOrder(columns));
    }

    /**
     * Tests whether {@link Table#getColumn(String)} returns the right
     * {@link Column} when given the name of the column.
     */
    @Test
    public void getColumn_givenNameOfExistingColumn_returnsColumn() {
        Column expected = columns[2];
        Column actual = table.getColumn(columns[2].getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Table#getColumn(String)} returns null when given the
     * name of the column that does not exist.
     */
    @Test
    public void getColumn_givenNameOfNonexistentColumn_returnsNull() {
        Column actual = table.getColumn("null");

        assertNull(actual);
    }

    /**
     * Tests whether {@link Table#addRecord(Record)} throws an
     * {@link IllegalArgumentException} when given a {@link Record} that belongs
     * to a different table.
     */
    @Test(expected = IllegalArgumentException.class)
    public void addRecord_givenRecordBeloningToDifferentTable_throwsIllegalArgumentException() {
        Table table1 = table;
        Table table2 = new Table(Arrays.asList(columns));
        Record record = mock(Record.class);
        when(record.getTable()).thenReturn(table2);

        table1.addRecord(record);
    }

    /**
     * Tests whether {@link Table#addRecord(Record)} adds the given record when
     * given a record that belongs to this table.
     */
    @Test
    public void addRecord_givenRecordBeloningToThisTable_addsRecord() {
        table.addRecord(record);

        assertThat(table.getRecords(), contains(record));
    }

    /**
     * Tests whether {@link Table#addRecord(Record)} throws a
     * {@link NullPointerException} when given a null reference.
     */
    @Test(expected = NullPointerException.class)
    public void addRecord_givenRecordNull_throwsNullPointerException() {
        table.addRecord((Record) null);
    }

    /**
     * Tests whether {@link Table#removeRecord(Record)} removes the given record
     * when given a record that was already added.
     */
    @Test
    public void removeRecord_givenRecordExists_removesRecord() {
        table.addRecord(record);

        table.removeRecord(record);

        assertThat(table.getRecords(), not(hasItem(record)));
    }

    /**
     * Tests whether {@link Table#removeRecord(Record)} does not remove any
     * records when given a record that was not added.
     */
    @Test
    public void removeRecord_givenRecordDoesNotExist_doesNotRemoveRecords() {
        table.addRecord(record);

        table.removeRecord(mock(Record.class));

        assertThat(table.getRecords(), hasItem(record));
    }

    /**
     * Tests whether {@link Table#removeRecord(Record)} does not remove any
     * records when given a null reference.
     */
    @Test
    public void removeRecord_givenRecordNull_doesNotRemoveRecords() {
        table.addRecord(record);

        table.removeRecord((Record) null);

        assertThat(table.getRecords(), hasItem(record));
    }

    /**
     * Tests whether {@link Table#iterator()} returns an iterator that contains
     * at least one {@link Chunk}.
     */
    @Test
    public void iterator_returnsIteratorContainingTable() {
        assertThat(table, iterableWithSize(greaterThan(0)));
    }
}
