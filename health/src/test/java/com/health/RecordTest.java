package com.health;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit test for Record.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Column.class, Table.class })
public class RecordTest {
    private String value1;
    private Double value2;
    private Column column1;
    private Column column2;
    private Record defaultRecord;
    private Table defaultTable;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        // Create mock columns
        this.column1 = mock(Column.class);
        when(this.column1.getIndex()).thenReturn(0);
        when(this.column1.getName()).thenReturn("column1");
        when(this.column1.getType()).thenReturn(ValueType.String);

        this.column2 = mock(Column.class);
        when(this.column2.getIndex()).thenReturn(1);
        when(this.column2.getName()).thenReturn("column2");
        when(this.column2.getType()).thenReturn(ValueType.Number);

        Iterable<Column> columns = Arrays.asList(
                this.column1,
                this.column2);

        // Create mock table
        this.defaultTable = mock(Table.class);
        when(this.defaultTable.getColumn(anyString())).thenReturn(null);
        when(this.defaultTable.getColumn(this.column1.getName())).thenReturn(
                this.column1);
        when(this.defaultTable.getColumn(this.column2.getName())).thenReturn(
                this.column2);
        when(this.defaultTable.getColumns()).thenReturn(columns);

        // Create mock record
        this.value1 = "abc";
        this.value2 = 1.5;

        this.defaultRecord = spy(new Record(this.defaultTable));
        this.defaultRecord.setValue(this.column1.getName(), value1);
        this.defaultRecord.setValue(this.column2.getName(), value2);
    }

    /**
     * Tests whether {@link Record#Record(Table)} throws a
     * {@link NullPointerException} when given a null reference.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenTableNull_throwsNullPointerException() {
        new Record((Table) null);
    }

    /**
     * Tests whether {@link Record#Record(Table)} sets the record's table.
     */
    @Test
    public void constructor_givenTable_setsTable() {
        Record record = new Record(this.defaultTable);

        Table expected = this.defaultTable;
        Table actual = record.getTable();
        assertSame(expected, actual);
    }

    /**
     * Tests whether {@link Record#Record(Table)} allocates an iterable for the
     * record's values that has a length matching the number of columns of the
     * given table.
     */
    @Test
    public void constructor_givenTable_setsValues() {
        Record record = new Record(this.defaultTable);

        assertThat(record.getValues(), iterableWithSize(2));
    }

    /**
     * Tests whether {@link Record#getValue(String)} throws a
     * {@link NullPointerException} when given a null reference.
     */
    @Test(expected = NullPointerException.class)
    public void getValue_givenNameNull_throwsNullPointerException() {
        this.defaultRecord.getValue(null);
    }

    /**
     * Tests whether {@link Record#getValue(String)} throws an
     * {@link IllegalArgumentException} when given a name of an nonexistent
     * column.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getValue_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        this.defaultRecord.getValue("null");
    }

    /**
     * Tests whether {@link Record#getValue(String)} returns the value of the
     * right column given the name of the column.
     */
    @Test
    public void getValue_givenNameOfColumn_returnsValueOfColumn() {
        Record record = this.defaultRecord;

        Object expected = value1;
        Object actual = record.getValue(this.column1.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getNumberValue(String)}'s implementation
     * makes a call to {@link Record#getValue(String)} to get the value of the
     * right column.
     */
    @Test
    public void getNumberValue_callsGetValue() {
        Record record = this.defaultRecord;

        record.getNumberValue(this.column2.getName());

        verify(record).getValue(this.column2.getName());
    }

    /**
     * Tests whether {@link Record#getNumberValue(String)} returns the correct
     * {@link Double} when given the name of a column that contains Doubles.
     */
    @Test
    public void getNumberValue_givenNameOfColumnWithNumber_returnsNumber() {
        Record record = this.defaultRecord;

        Double expected = (Double) this.value2;
        Double actual = record.getNumberValue(this.column2.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getNumberValue(String)} throws an
     * {@link IllegalArgumentException} when given the name of a column that
     * contains {@link String}s.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNumberValue_givenNameOfColumnWithString_throwsIllegalArgumentException() {
        this.defaultRecord.getNumberValue(this.column1.getName());
    }

    /**
     * Tests whether {@link Record#getStringValue(String)}'s implementation
     * makes a call to {@link Record#getValue(String)} to get the value of the
     * right column.
     */
    @Test
    public void getStringValue_callsGetValue() {
        Record record = this.defaultRecord;

        record.getStringValue(this.column1.getName());

        verify(record).getValue(this.column1.getName());
    }

    /**
     * Tests whether {@link Record#getStringValue(String)} returns the correct
     * {@link String} when given the name of a column that contains Strings.
     */
    @Test
    public void getStringValue_givenNameOfColumnWithString_returnsString() {
        Record record = this.defaultRecord;

        String expected = (String) this.value1;
        String actual = record.getStringValue(this.column1.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getStringsValue(String)} throws an
     * {@link IllegalArgumentException} when given the name of a column that
     * contains {@link Double}s.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getStringValue_givenNameOfColumnWithNumber_throwsIllegalArgumentException() {
        this.defaultRecord.getNumberValue(this.column2.getName());
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} throws a
     * {@link NullPointerException} when given a null reference for name.
     */
    @Test(expected = NullPointerException.class)
    public void setValueDouble_givenNameNull_throwsNullPointerException() {
        this.defaultRecord.setValue((String) null, this.value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} throws an
     * {@link IllegalArgumentException} when given a name of a column that
     * contains {@link String}s.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueDouble_givenNameOfColumnWithString_throwsIllegalArgumentException() {
        this.defaultRecord.setValue(this.column1.getName(), this.value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} throws an
     * {@link IllegalArgumentException} when given a name of an nonexistent
     * column.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueDouble_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        this.defaultRecord.setValue("null", this.value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} updates the value
     * of the correct column when given the name of a column that contains
     * {@link Double}s.
     */
    @Test
    public void setValueDouble_givenNameOfColumn_updatesValue() {
        Record record = this.defaultRecord;
        Double value = -1.0;

        record.setValue(this.column2.getName(), value);

        Double expected = value;
        Double actual = record.getNumberValue(this.column2.getName());
        assertSame(expected, actual);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} throws a
     * {@link NullPointerException} when given a null reference for name.
     */
    @Test(expected = NullPointerException.class)
    public void setValueString_givenNameNull_throwsNullPointerException() {
        this.defaultRecord.setValue((String) null, this.value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} throws an
     * {@link IllegalArgumentException} when given a name of a column that
     * contains {@link Double}s.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueString_givenNameOfColumnWithNumber_throwsIllegalArgumentException() {
        this.defaultRecord.setValue(this.column2.getName(), this.value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} throws an
     * {@link IllegalArgumentException} when given a name of an nonexistent
     * column.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueString_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        this.defaultRecord.setValue("null", this.value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} updates the value
     * of the correct column when given the name of a column that contains
     * {@link String}s.
     */
    @Test
    public void setValueString_givenNameOfColumn_updatesValue() {
        Record record = this.defaultRecord;
        String value = "def";

        record.setValue(this.column1.getName(), value);

        String expected = value;
        String actual = record.getStringValue(this.column1.getName());
        assertSame(expected, actual);
    }
}
