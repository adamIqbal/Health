package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDate;
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
import com.health.output.Output;

public class ConnectTest {

    private Table connectedTable;

    @Before
    public void setupTest() {

        Column[] tableColumns = new Column[4];
        tableColumns[0] = new Column("meetwaarde1", 1, ValueType.Number);
        tableColumns[1] = new Column("date", 0, ValueType.Date);
        tableColumns[2] = new Column("name", 2, ValueType.String);
        tableColumns[3] = new Column("meetwaarde2", 3, ValueType.Number);

        Table table = new Table(Arrays.asList(tableColumns));

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

        Table table2 = new Table(Arrays.asList(tableColumns2));

        tmp = new Record(table2);
        tmp.setValue("datum", LocalDateTime.of(2013, 11, 15, 0, 0));
        tmp.setValue("meetwaarde3", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde4", 3.0);

        tmp = new Record(table2);
        tmp.setValue("datum", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("meetwaarde3", 10.0);
        tmp.setValue("name", "Dolf");
        tmp.setValue("meetwaarde4", 10.0);

        List<ColumnConnection> connections = new ArrayList<ColumnConnection>();
        connections.add(new ColumnConnection("date", "datum", "date"));

        connectedTable = Connect.connect(table, table2, connections);

    }

    @Test
    public void testColsDate() {
        List<Column> tableCols = connectedTable.getColumns();

        assertEquals("date", tableCols.get(0).getName());
        assertNotEquals("date", tableCols.get(1).getName());

    }

    @Test
    public void testColsString() {
        List<Column> tableCols = connectedTable.getColumns();

        assertEquals("name", tableCols.get(2).getName());
        assertNotEquals("name", tableCols.get(1).getName());

    }

    @Test
    public void testColsNumber() {
        List<Column> tableCols = connectedTable.getColumns();

        assertTrue(tableCols.contains(new Column("meetwaarde3", 1,
                ValueType.Number)));
        assertFalse(tableCols.contains(new Column("meetwaarde6", 0,
                ValueType.Number)));

    }

    @Test
    public void testAllThree() {
        List<Column> tableCols = connectedTable.getColumns();
        assertEquals("name", tableCols.get(2).getName());
        assertNotEquals("name", tableCols.get(0).getName());

        assertTrue(tableCols.contains(new Column("meetwaarde3", 1,
                ValueType.Number)));
        assertFalse(tableCols.contains(new Column("meetwaarde0", 0,
                ValueType.Number)));

        assertEquals("date", tableCols.get(0).getName());
        assertNotEquals("date", tableCols.get(2).getName());

    }

    @Test
    public void testNoEmptyCells() {

        List<Record> tableRecs = connectedTable.getRecords();

        assertNotEquals(null, tableRecs.get(3).getNumberValue("meetwaarde1"));
        double value = tableRecs.get(1).getNumberValue("meetwaarde3");
        double expected = 0;
        assertTrue(expected == value);
    }

}
