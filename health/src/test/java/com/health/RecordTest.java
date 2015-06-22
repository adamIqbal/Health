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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
    private static final LocalDateTime value3 = LocalDateTime.of(1970, 1, 1, 0, 0);
    private Column column1;
    private Column column2;
    private Column column3;
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
        when(column1.isFrequencyColumn()).thenReturn(false);

        column2 = mock(Column.class);
        when(column2.getIndex()).thenReturn(1);
        when(column2.getName()).thenReturn("column2");
        when(column2.getType()).thenReturn(ValueType.Number);
        when(column2.isFrequencyColumn()).thenReturn(false);

        column3 = mock(Column.class);
        when(column3.getIndex()).thenReturn(2);
        when(column3.getName()).thenReturn("column3");
        when(column3.getType()).thenReturn(ValueType.Date);
        when(column3.isFrequencyColumn()).thenReturn(false);

        // Create mock table
        table = mock(Table.class);
        when(table.getColumn(anyString())).thenReturn(null);
        when(table.getColumn(column1.getIndex())).thenReturn(column1);
        when(table.getColumn(column2.getIndex())).thenReturn(column2);
        when(table.getColumn(column3.getIndex())).thenReturn(column3);
        when(table.getColumn(column1.getName())).thenReturn(column1);
        when(table.getColumn(column2.getName())).thenReturn(column2);
        when(table.getColumn(column3.getName())).thenReturn(column3);
        when(table.getColumns()).thenReturn(Arrays.asList(column1, column2, column3));
        when(table.getDateColumn()).thenReturn(column3);

        // Create mock record
        record = new Record(table);
        record.setValue(column1.getName(), value1);
        record.setValue(column2.getName(), value2);
        record.setValue(column3.getName(), value3);
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

        assertThat(record.getValues(), iterableWithSize(3));
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

    @Test
    public void getValue_givenIndexOfNonexistentColumn_returnsNull() {
        assertNull(record.getValue(-1));
    }

    @Test
    public void getValue_givenIndexOfColumn_returnsValueOfColumn() {
        Object expected = value1;
        Object actual = record.getValue(column1.getIndex());

        assertEquals(expected, actual);
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

    @Test
    public void getNumberValue_givenNameOfColumnWithNumberNull_returnsZero() {
        record.setValue(column2.getName(), (Double) null);

        Double expected = (Double) 0.0;
        Double actual = record.getNumberValue(column2.getName());

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
     * Tests whether {@link Record#getNumberValue(String)} throws an
     * {@link IllegalStateException} when given the name of a column that
     * contains {@link LocalDate}s.
     */
    @Test(expected = IllegalStateException.class)
    public void getNumberValue_givenNameOfColumnWithDate_throwsIllegalStateException() {
        record.getNumberValue(column3.getName());
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
     * Tests whether {@link Record#getStringValue(String)} throws an
     * {@link IllegalStateException} when given the name of a column that
     * contains {@link LocalDate}s.
     */
    @Test(expected = IllegalStateException.class)
    public void getStringValue_givenNameOfColumnWithDate_throwsIllegalStateException() {
        record.getStringValue(column3.getName());
    }

    /**
     * Tests whether {@link Record#getDateValue(String)} returns the correct
     * {@link LocalDate} when given the name of a column that contains Strings.
     */
    @Test
    public void getDateValue_givenNameOfColumnWithDate_returnsDate() {
        LocalDateTime expected = (LocalDateTime) value3;
        LocalDateTime actual = record.getDateValue(column3.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#getDateValue(String)} throws an
     * {@link IllegalStateException} when given the name of a column that
     * contains {@link Double}s.
     */
    @Test(expected = IllegalStateException.class)
    public void getDateValue_givenNameOfColumnWithNumber_throwsIllegalStateException() {
        record.getDateValue(column2.getName());
    }

    /**
     * Tests whether {@link Record#getDateValue(String)} throws an
     * {@link IllegalStateException} when given the name of a column that
     * contains {@link String}s.
     */
    @Test(expected = IllegalStateException.class)
    public void getDateValue_givenNameOfColumnWithString_throwsIllegalStateException() {
        record.getDateValue(column1.getName());
    }

    /**
     * Tests whether {@link Record#setValue(int, Object)} throws a
     * {@link IllegalArgumentException} when given an index of a column that
     * does not exist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValue_givenIndexOfNonExistingColumn_throwsIllegalArgumentException() {
        record.setValue(-1, null);
    }

    /**
     * Tests whether {@link Record#setValue(int, Object)} updates the value of
     * the correct column when given a {@link Double} value and the name of a
     * column that contains {@link Double}s.
     */
    @Test
    public void setValueDouble_givenIndexOfColumnWithNumber_updatesValue() {
        Double value = -1.0;

        record.setValue(column2.getIndex(), value);

        Double expected = value;
        Double actual = record.getNumberValue(column2.getName());

        assertSame(expected, actual);
    }

    @Test
    public void setValueFloat_updatesValue() {
        float value = -1.0f;

        record.setValue(column2.getIndex(), value);

        Double expected = (double) value;
        Double actual = record.getNumberValue(column2.getName());

        assertEquals(expected, actual);
    }

    @Test
    public void setValueShort_updatesValue() {
        short value = 255;

        record.setValue(column2.getIndex(), value);

        Double expected = (double) value;
        Double actual = record.getNumberValue(column2.getName());

        assertEquals(expected, actual);
    }

    @Test
    public void setValueInteger_updatesValue() {
        int value = 255;

        record.setValue(column2.getIndex(), value);

        Double expected = (double) value;
        Double actual = record.getNumberValue(column2.getName());

        assertEquals(expected, actual);
    }

    @Test
    public void setValueLong_updatesValue() {
        long value = 255;

        record.setValue(column2.getIndex(), value);

        Double expected = (double) value;
        Double actual = record.getNumberValue(column2.getName());

        assertEquals(expected, actual);
    }

    @Test
    public void setValueCharacter_updatesValue() {
        char value = 255;

        record.setValue(column2.getIndex(), value);

        Double expected = (double) value;
        Double actual = record.getNumberValue(column2.getName());

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws a
     * {@link IllegalArgumentException} when given a null reference for name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValue_givenNameNull_throwsIllegalArgumentException() {
        record.setValue((String) null, value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalArgumentException} when given a name of an nonexistent
     * column.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValue_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        record.setValue("null", value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalArgumentException} when given a value that is not one of
     * {@link Double}, {@link String} or {@link LocalDate}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setValue_givenValueOfInvalidType_throwsIllegalArgumentException() {
        record.setValue(column1.getName(), new Object());
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalStateException} when given a {@link Double} value and the
     * name of a column that contains {@link String}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueDouble_givenNameOfColumnWithString_throwsIllegalStateException() {
        record.setValue(column1.getName(), value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalStateException} when given a {@link Double} value and the
     * name of a column that contains {@link LocalDate}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueDouble_givenNameOfColumnWithDate_throwsIllegalStateException() {
        record.setValue(column3.getName(), value2);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} updates the value
     * of the correct column when given a {@link Double} value and the name of a
     * column that contains {@link Double}s.
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
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalStateException} when given a {@link String} value and the
     * name of a column that contains {@link Double}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueString_givenNameOfColumnWithNumber_throwsIllegalStateException() {
        record.setValue(column2.getName(), value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalStateException} when given a {@link String} value and the
     * name of a column that contains {@link LocalDate}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueString_givenNameOfColumnWithDate_throwsIllegalStateException() {
        record.setValue(column3.getName(), value1);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} updates the value
     * of the correct column when given a {@link String} value and the name of a
     * column that contains {@link String}s.
     */
    @Test
    public void setValueString_givenNameOfColumn_updatesValue() {
        String value = "def";

        record.setValue(column1.getName(), value);

        String expected = value;
        String actual = record.getStringValue(column1.getName());

        assertSame(expected, actual);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalStateException} when given a {@link LocalDate} value and
     * the name of a column that contains {@link Double}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueDate_givenNameOfColumnWithNumber_throwsIllegalStateException() {
        record.setValue(column2.getName(), value3);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} throws an
     * {@link IllegalStateException} when given a {@link LocalDate} value and
     * the name of a column that contains {@link String}s.
     */
    @Test(expected = IllegalStateException.class)
    public void setValueDate_givenNameOfColumnWithString_throwsIllegalStateException() {
        record.setValue(column1.getName(), value3);
    }

    /**
     * Tests whether {@link Record#setValue(String, Object)} updates the value
     * of the correct column when given {@link LocalDate} value and the name of
     * a column that contains {@link LocalDate}s.
     */
    @Test
    public void setValueDate_givenNameOfColumn_updatesValue() {
        LocalDateTime value = LocalDateTime.of(1971, 1, 1, 1, 1);

        record.setValue(column3.getName(), value);

        LocalDateTime expected = value;
        LocalDateTime actual = record.getDateValue(column3.getName());

        assertSame(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void copyTo_givenTableNull_throwsNullPointerException() {
        record.copyTo(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyTo_givenTableWithDifferentNumberOfColumns_throwsIllegalArgumentException() {
        Table table2 = mock(Table.class);
        when(table2.getColumns()).thenReturn(Arrays.asList(column1, column2));

        record.copyTo(table2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyTo_givenTableWithDifferentColumns_throwsIllegalArgumentException() {
        Table table2 = mock(Table.class);
        when(table2.getColumns()).thenReturn(Arrays.asList(column1, column3, column2));

        record.copyTo(table2);
    }

    @Test
    public void copyTo_givenTableWithIdenticalColumns_copiesRecord() {
        Table table2 = mock(Table.class);
        when(table2.getColumns()).thenReturn(Arrays.asList(column1, column2, column3));

        record.copyTo(table2);

        ArgumentCaptor<Record> recordCaptur = ArgumentCaptor.forClass(Record.class);
        verify(table2).addRecord(recordCaptur.capture());

        Record copy = recordCaptur.getValue();
        assertEquals(record.getValues(), copy.getValues());
    }
}
