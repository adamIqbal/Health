package com.health.operations;

import static com.health.operations.Constraints.*;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class ConstraintsTest {
    Table table;
    Table table3;

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

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("date", 0, ValueType.Date));
        columns.add(new Column("waarde", 1, ValueType.Number));
        columns.add(new Column("name", 2, ValueType.String));

        table3 = new Table(columns);

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
        tmp.setValue("waarde", 8.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 22, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Peter");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 26, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "dolfje");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 27, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 29, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "dolfje");

        tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
        tmp.setValue("waarde", 8.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 12, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Hein");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 13, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Dolf");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 10, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 11, 15, 0, 0));
        tmp.setValue("waarde", 25.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Dolf");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
        tmp.setValue("waarde", 8.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 22, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Peter");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 26, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "dolfje");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 27, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 29, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "dolfje");

        tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
        tmp.setValue("waarde", 8.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 12, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Hein");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 13, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Dolf");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 10, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 11, 15, 0, 0));
        tmp.setValue("waarde", 25.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table3);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Dolf");

    }

    @Test
    public void testConstrainString() {
        Table table0 = constrain(
                table -> table.getStringValue("name").equals("Dolf"), table);

        assertTrue(table0.getRecords().size() == 2);
        assertFalse(table0.getRecords().size() > 2);
        assertFalse(table0.getRecords().size() < 2);

        assertTrue(table0.getRecords().get(1).getNumberValue("meetwaarde2") == 10.0);
        assertFalse(table0.getRecords().contains(null));
    }

    @Test
    public void testConstrainNumber() {
        Table table0 = constrain(
                table -> table.getNumberValue("meetwaarde1") < 10, table);

        assertTrue(table0.getRecords().size() == 1);
        assertFalse(table0.getRecords().size() > 1);
        assertFalse(table0.getRecords().size() < 1);

        assertFalse(table0.getRecords().contains("Dolf"));
        assertTrue(table0.getRecords().get(0).getStringValue("name")
                .equals("Piet"));
        assertFalse(table.getRecords().contains(null));
    }

    @Test
    public void testConstrainDate() {
        Table table1 = table;
        Table table0 = constrain(
                table -> table.getDateValue("date").equals(
                        LocalDateTime.of(2013, 12, 31, 0, 0)), table);

        assertTrue(table0.getRecords().size() == 0);
        assertFalse(table0.getRecords().size() > 0);
        assertFalse(table0.getRecords().size() < 0);

        table1 = constrain(
                table -> table.getDateValue("date").equals(
                        LocalDateTime.of(2013, 11, 12, 0, 0)), table);
        assertTrue(table1.getRecords().size() == 0);
        assertFalse(table1.getRecords().contains(
                LocalDateTime.of(2013, 12, 30, 0, 0)));
        assertFalse(table1.getRecords().contains(
                LocalDateTime.of(2013, 11, 12, 0, 0)));
        assertFalse(table1.getRecords().contains(null));
    }

    @Test
    public void testConstrainNumberLargeTable() {
        Table table0 = constrain(
                table3 -> table3.getNumberValue("waarde") >= 25, table3);
        assertTrue(table0.getRecords().size() == 2);
        assertFalse(table0.getRecords().size() > 2);
        assertFalse(table0.getRecords().size() < 2);

        assertFalse(table0.getRecords().contains("Dolf"));
        assertTrue(table0.getRecords().get(0).getStringValue("name")
                .equals("Piet"));
        assertFalse(table.getRecords().contains(null));
    }
}
