package com.health.operations;

import static org.junit.Assert.*;
import static com.health.operations.TableWithDays.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class TableWithDaysTest {
    LocalDateTime date;
    Column[] tableColumns;
    Table table, table2;

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

        tmp.setValue("date", LocalDateTime.of(2013, 12, 11, 0, 0));
        tmp.setValue("meetwaarde1", 8.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 20.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 12, 0, 0));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Hein");
        tmp.setValue("meetwaarde2", 10.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 13, 0, 0));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Dolf");
        tmp.setValue("meetwaarde2", -1.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 10, 0, 0));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 10.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2014, 11, 15, 0, 0));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 3.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Dolf");
        tmp.setValue("meetwaarde2", 10.0);

        Column[] tableColumns2 = new Column[4];
        tableColumns2[0] = new Column("datum", 0, ValueType.Date);
        tableColumns2[1] = new Column("meetwaarde3", 1, ValueType.Number);
        tableColumns2[2] = new Column("name", 2, ValueType.String);
        tableColumns2[3] = new Column("meetwaarde4", 3, ValueType.Number);

        table2 = new Table(Arrays.asList(tableColumns2));

        tmp = new Record(table2);
        tmp.setValue("datum", LocalDateTime.of(2013, 11, 15, 0, 0));
        tmp.setValue("meetwaarde3", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde4", 3.0);

        tmp = new Record(table2);
        tmp.setValue("datum", LocalDateTime.of(2013, 11, 15, 0, 0));
        tmp.setValue("meetwaarde3", 10.0);
        tmp.setValue("name", "Dolf");
        tmp.setValue("meetwaarde4", 10.0);
    }

    @Test
    public void testTableDays() {
        assertEquals(table2.getColumns().size(), 4);
        table2 = TableDays(table2);
        assertEquals(table2.getColumns().size(), 5);
        assertTrue(table2.getRecords().get(1).getStringValue("day_of_week")
                .contains("FRIDAY"));
    }

    @Test
    public void testDayOfWeek() {
        date = LocalDateTime.of(2015, 6, 17, 0, 6);
        assertTrue(dayOfWeek(date).toLowerCase().equals("wednesday"));
        assertFalse(dayOfWeek(date).toLowerCase().equals("partyday"));
        date = LocalDateTime.of(2015, 6, 16, 0, 6);
        assertTrue(dayOfWeek(date).toLowerCase().equals("tuesday"));
        assertFalse(dayOfWeek(date).toLowerCase().equals("partyday"));
    }
}
