package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class ConversionTest {
    Table table;

    @Before
    public void setUp() throws Exception {
        Column[] tableColumns = new Column[4];
        tableColumns[0] = new Column("meetwaarde1", 1, ValueType.Number);
        tableColumns[1] = new Column("date", 0, ValueType.Date);
        tableColumns[2] = new Column("name", 2, ValueType.String);
        tableColumns[3] = new Column("meetwaarde2", 3, ValueType.Number);

        table = new Table(Arrays.asList(tableColumns));

        // fill the table
        Record tmp = new Record(table);

        tmp.setValue("date", LocalDateTime.of(2013, 12, 12, 0, 0));
        tmp.setValue("meetwaarde1", 8.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 20.0);
    }

    @Test
    public void testConversion() {
        assertEquals(com.health.operations.Conversion.conversion(table), null);
    }

}
