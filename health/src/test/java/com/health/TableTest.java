package com.health;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
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
@PrepareForTest({ Record.class })
public class TableTest {
    private Column[] columns;
    private Table table;
    private Record record;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        columns = new Column[] {
                new Column("abc", 0, ValueType.Number),
                new Column("column2", 1, ValueType.Number),
                new Column("cda", 2, ValueType.String),
                new Column("column4", 3, ValueType.Date),
        };

        table = new Table(Arrays.asList(columns));
        record = mockRecord(table);
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
        new Table(Arrays.asList(new Column("column1", 1, ValueType.Number)));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with
     * columns whose indices do not for a sequence.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWithMissingIndices_throwsIllegalArgumentException() {
        new Table(Arrays.asList(
                new Column("column1", 0, ValueType.Number),
                new Column("column2", 2, ValueType.Number)));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} throws an
     * {@link IllegalArgumentException} when given an {@link Iterable} with
     * columns whose indices contain duplicates.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenColumnsWitDuplicateIndices_throwsIllegalArgumentException() {
        new Table(Arrays.asList(
                new Column("column1", 0, ValueType.Number),
                new Column("column2", 0, ValueType.Number)));
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
     * Tests whether {@link Table#getColumn(int)} throws an
     * {@link IndexOutOfBoundsException} when given an index smaller than zero.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumnInt_givenIndexSmallerThanZero_throwsIndexOutOfBoundsException() {
        table.getColumn(-1);
    }

    /**
     * Tests whether {@link Table#getColumn(int)} throws an
     * {@link IndexOutOfBoundsException} when given an index equal to the number
     * of columns.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumnInt_givenIndexEqualToNumberOfColumns_throwsIndexOutOfBoundsException() {
        table.getColumn(this.columns.length);
    }

    /**
     * Tests whether {@link Table#getColumn(int)} throws an
     * {@link IndexOutOfBoundsException} when given an index greater than the
     * number of columns.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumnInt_givenIndexGreaterThanNumberOfColumns_throwsIndexOutOfBoundsException() {
        table.getColumn(this.columns.length + 1);
    }

    /**
     * Tests whether {@link Table#getColumn(int)} returns the right
     * {@link Column} when given the index of the column.
     */
    @Test
    public void getColumnInt_givenIndexOfExistingColumn_returnsColumn() {
        Column expected = columns[2];
        Column actual = table.getColumn(columns[2].getIndex());

        assertEquals(expected, actual);
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
        Record record = mockRecord(table2);

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
     * one {@link Record}.
     */
    @Test
    public void iterator_givenTableWithOneRecord_returnsIteratorContainingReocrd() {
        table.addRecord(record);

        assertThat(table, contains(record));
    }

    @Test
    public void size_returnsNumberOfRecords() {
        table.addRecord(mockRecord(table));
        table.addRecord(mockRecord(table));

        assertEquals(2, table.size());
    }

    @Test
    public void getDateColumn_givenTableContainsDateColumn_returnsDateColumn() {
        assertEquals(columns[3], table.getDateColumn());
    }

    @Test
    public void getDateColumn_givenTableWithoutDateColumn_returnsNull() {
        Table table = new Table(Arrays.asList(new Column("abc", 0, ValueType.Number)));

        assertNull(table.getDateColumn());
    }

    private static Record mockRecord(final Table table) {
        Record record = mock(Record.class);
        when(record.getTable()).thenReturn(table);

        return record;
    }
}
