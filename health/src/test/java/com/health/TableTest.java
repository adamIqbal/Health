package com.health;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit test for Table.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Column.class)
public class TableTest {
    private Column defaultColumn1;
    private Column defaultColumn2;
    private Column defaultColumn3;
    private Column defaultColumn4;
    private Iterable<Column> defaultColumns;
    private Table defaultTable;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        this.defaultColumn1 = mock(Column.class);
        this.defaultColumn2 = mock(Column.class);
        this.defaultColumn3 = mock(Column.class);
        this.defaultColumn4 = mock(Column.class);

        when(this.defaultColumn1.getName()).thenReturn("abc");
        when(this.defaultColumn2.getName()).thenReturn("column2");
        when(this.defaultColumn3.getName()).thenReturn("cda");
        when(this.defaultColumn4.getName()).thenReturn("column4");

        this.defaultColumns = Arrays.asList(
                this.defaultColumn1,
                this.defaultColumn2,
                this.defaultColumn3,
                this.defaultColumn4);

        this.defaultTable = new Table(this.defaultColumns);
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
     * Tests whether {@link Table#Table(Iterable)} sets the columns when given
     * an empty {@link Iterable} so that {@link Table#getColumns()} returns an
     * {@link Iterable} instance.
     */
    @Test
    public void constructor_givenColumnsEmpty_setsColumns() {
        Table table = new Table(new ArrayList<Column>());

        assertThat(table.getColumns(), iterableWithSize(0));
    }

    /**
     * Tests whether {@link Table#Table(Iterable)} sets the columns when given
     * an {@link Iterable} so that {@link Table#getColumns()} returns an
     * {@link Iterable} that contains the same elements.
     */
    @Test
    public void constructor_givenColumns_setsColumns() {
        Table table = new Table(Arrays.asList(
                this.defaultColumn1,
                this.defaultColumn2));

        assertThat(table.getColumns(),
                contains(this.defaultColumn1, this.defaultColumn2));
    }

    /**
     * Tests whether {@link Table#getColumn(String)} returns the right
     * {@link Column} when given the name of the column.
     */
    @Test
    public void getColumn_givenNameOfExistingColumn_returnsColumn() {
        Table table = this.defaultTable;

        Column expected = this.defaultColumn3;
        Column actual = table.getColumn(this.defaultColumn3.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Table#getColumn(String)} returns null when given the
     * name of the column that does not exist.
     */
    @Test
    public void getColumn_givenNameOfNonexistentColumn_returnsNull() {
        Table table = this.defaultTable;

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
        Table table1 = this.defaultTable;
        Table table2 = mock(Table.class);
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
        Table table = this.defaultTable;
        Record record = mock(Record.class);
        when(record.getTable()).thenReturn(table);

        table.addRecord(record);

        assertThat(table.getRecords(), contains(record));
    }

    /**
     * Tests whether {@link Table#iterator()} returns an iterator that
     * contains this table.
     */
    @Test
    public void iterator_returnsIteratorContainingTable() {
        Table table = this.defaultTable;
        
        assertThat(table, contains(table));
    }
}
