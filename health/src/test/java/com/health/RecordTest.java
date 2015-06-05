package com.health;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
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
    private static final String value1 = "abc";
    private static final Double value2 = 1.5;
    private Column column1;
    private Column column2;
    private Record record;
    private Table table;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        // Create mock columns
        column1 = mock(Column.class);
        when(column1.getIndex()).thenReturn(0);
        when(column1.getName()).thenReturn("column1");
        when(column1.getType()).thenReturn(ValueType.String);
        when(column1.getIsFrequencyColumn()).thenReturn(false);

        column2 = mock(Column.class);
        when(column2.getIndex()).thenReturn(1);
        when(column2.getName()).thenReturn("column2");
        when(column2.getType()).thenReturn(ValueType.Number);
        when(column2.getIsFrequencyColumn()).thenReturn(true);

        // Create mock table
        table = mock(Table.class);
        when(table.getColumn(anyString())).thenReturn(null);
        when(table.getColumn(column1.getName())).thenReturn(
                column1);
        when(table.getColumn(column2.getName())).thenReturn(
                column2);
        when(table.getColumns()).thenReturn(Arrays.asList(column1, column2));

        // Create mock record
        record = new Record(table);
        record.setValue(column1.getName(), value1);
        record.setValue(column2.getName(), value2);
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
        Record record = new Record(table);

        Table expected = table;
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
        Record record = new Record(table);

        assertThat(record.getValues(), iterableWithSize(2));
    }

    /**
     * Tests whether {@link Record#Record(Table)} calls
     * {@link Table#addRecord(Record)} to add itself to the given table.
     */
    @Test
    public void constructor_givenTable_callsAddRecord() {
        Record record = new Record(table);

        verify(table).addRecord(record);
    }

    /**
     * Tests whether {@link Record#getValue(String)} returns null when given a
     * null reference.
     */
    @Test
    public void getValue_givenNameNull_returnsNull() {
        assertNull(record.getValue(null));
    }

    /**
     * Tests whether {@link Record#getValue(String)} returns null when given a
     * name of a nonexistent column.
     */
    @Test
    public void getValue_givenNameOfNonexistentColumn_returnsNull() {
        assertNull(record.getValue("null"));
    }

    /**
     * Tests whether {@link Record#getValue(String)} returns the value of the
     * right column given the name of the column.
     */
    @Test
    public void getValue_givenNameOfColumn_returnsValueOfColumn() {
        Object expected = value1;
        Object actual = record.getValue(column1.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getNumberValue(String)} returns the correct
     * {@link Double} when given the name of a column that contains Doubles.
     */
    @Test
    public void getNumberValue_givenNameOfColumnWithNumber_returnsNumber() {
        Double expected = (Double) value2;
        Double actual = record.getNumberValue(column2.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getNumberValue(String)} throws an
     * {@link IllegalStateException} when given the name of a column that
     * contains {@link String}s.
     */
    @Test(expected = IllegalStateException.class)
    public void getNumberValue_givenNameOfColumnWithString_throwsIllegalStateException() {
        record.getNumberValue(column1.getName());
    }

    /**
     * Tests whether {@link Record#getStringValue(String)} returns the correct
     * {@link String} when given the name of a column that contains Strings.
     */
    @Test
    public void getStringValue_givenNameOfColumnWithString_returnsString() {
        String expected = (String) value1;
        String actual = record.getStringValue(column1.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getStringValue(String)} throws an
     * {@link IllegalStateException} when given the name of a column that
     * contains {@link Double}s.
     */
    @Test(expected = IllegalStateException.class)
    public void getStringValue_givenNameOfColumnWithNumber_throwsIllegalStateException() {
        record.getStringValue(column2.getName());
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} throws a
     * {@link IllegalArgumentException} when given a null reference for name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueDouble_givenNameNull_throwsIllegalArgumentException() {
        record.setValue((String) null, value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} throws an
     * {@link IllegalArgumentException} when given a name of an nonexistent
     * column.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueDouble_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        record.setValue("null", value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} throws an
     * {@link IllegalStateException} when given a name of a column that contains
     * {@link String}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueDouble_givenNameOfColumnWithString_throwsIllegalStateException() {
        record.setValue(column1.getName(), value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Double)} updates the value
     * of the correct column when given the name of a column that contains
     * {@link Double}s.
     */
    @Test
    public void setValueDouble_givenNameOfColumn_updatesValue() {
        Double value = -1.0;

        record.setValue(column2.getName(), value);

        Double expected = value;
        Double actual = record.getNumberValue(column2.getName());

        assertSame(expected, actual);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} throws a
     * {@link IllegalArgumentException} when given a null reference for name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueString_givenNameNull_throwsIllegalArgumentException() {
        record.setValue((String) null, value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} throws an
     * {@link IllegalArgumentException} when given a name of an nonexistent
     * column.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValueString_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        record.setValue("null", value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} throws an
     * {@link IllegalStateException} when given a name of a column that contains
     * {@link Double}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueString_givenNameOfColumnWithNumber_throwsIllegalStateException() {
        record.setValue(column2.getName(), value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, String)} updates the value
     * of the correct column when given the name of a column that contains
     * {@link String}s.
     */
    @Test
    public void setValueString_givenNameOfColumn_updatesValue() {
        String value = "def";

        record.setValue(column1.getName(), value);

        String expected = value;
        String actual = record.getStringValue(column1.getName());

        assertSame(expected, actual);
    }
}
