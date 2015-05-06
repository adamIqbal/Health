package com.health;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
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
        Table table = mock(Table.class);
        when(table.getColumn(anyString())).thenReturn(null);
        when(table.getColumn(this.column1.getName())).thenReturn(
                this.column1);
        when(table.getColumn(this.column2.getName())).thenReturn(
                this.column2);
        when(table.getColumns()).thenReturn(columns);

        // Create mock record
        this.value1 = "abc";
        this.value2 = 1.5;

        this.defaultRecord = spy(new Record(table));
        this.defaultRecord.setValue(this.column1.getName(), value1);
        this.defaultRecord.setValue(this.column2.getName(), value2);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenTableNull_throwsNullPointerException() {
        new Record((Table) null);
    }
    
    @Test
    public void constructor_givenTable_setsTable() {
        Record record = new Record(this.defaultTable);
        
        Table expected = this.defaultTable;
        Table actual = record.getTable();
        assertSame(expected, actual);
    }

    @Test
    public void constructor_givenTable_setsValues() {
        Record record = new Record(this.defaultTable);
        
        assertThat(record.getValues(), hasItems(this.column1, this.column2));
    }
    
    @Test(expected = NullPointerException.class)
    public void getValue_givenNameNull_throwsNullPointerException() {
        this.defaultRecord.getValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValue_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        this.defaultRecord.getValue("null");
    }

    @Test
    public void getValue_givenNameOfColumn_returnsValueOfColumn() {
        Record record = this.defaultRecord;

        Object expected = value1;
        Object actual = record.getValue(this.column1.getName());

        assertEquals(expected, actual);
    }

    @Test
    public void getNumberValue_callsGetValue() {
        Record record = this.defaultRecord;

        record.getNumberValue(this.column2.getName());

        verify(record).getValue(this.column2.getName());
    }

    @Test
    public void getNumberValue_givenNameOfColumnWithNumber_returnsNumber() {
        Record record = this.defaultRecord;

        Double expected = (Double) this.value2;
        Double actual = record.getNumberValue(this.column2.getName());

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNumberValue_givenNameOfColumnWithString_throwsIllegalArgumentException() {
        this.defaultRecord.getNumberValue(this.column1.getName());
    }

    @Test
    public void getStringValue_callsGetValue() {
        Record record = this.defaultRecord;

        record.getStringValue(this.column1.getName());

        verify(record).getValue(this.column1.getName());
    }

    @Test
    public void getStringValue_givenNameOfColumnWithString_returnsString() {
        Record record = this.defaultRecord;

        String expected = (String) this.value1;
        String actual = record.getStringValue(this.column1.getName());

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStringValue_givenNameOfColumnWithNumber_throwsIllegalArgumentException() {
        this.defaultRecord.getNumberValue(this.column2.getName());
    }

    @Test(expected = NullPointerException.class)
    public void setValueNumber_givenNameNull_throwsNullPointerException() {
        this.defaultRecord.setValue((String) null, this.value2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueNumber_givenNameOfColumnWithString_throwsIllegalArgumentException() {
        this.defaultRecord.setValue(this.column1.getName(), this.value2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueNumber_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        this.defaultRecord.setValue("null", this.value2);
    }

    @Test
    public void setValueNumber_givenNameOfColumn_updatesValue() {
        Record record = this.defaultRecord;
        Double value = -1.0;

        record.setValue(this.column2.getName(), value);

        Double expected = value;
        Double actual = record.getNumberValue(this.column2.getName());
        assertSame(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void setValueString_givenNameNull_throwsNullPointerException() {
        this.defaultRecord.setValue((String) null, this.value1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueString_givenNameOfColumnWithNumber_throwsIllegalArgumentException() {
        this.defaultRecord.setValue(this.column2.getName(), this.value1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueString_givenNameOfNonexistentColumn_throwsIllegalArgumentException() {
        this.defaultRecord.setValue("null", this.value1);
    }

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
