package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDate;
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
        tableColumns[0] = new Column("date", 0, ValueType.Date);
        tableColumns[1] = new Column("meetwaarde1", 1, ValueType.Number);
        tableColumns[2] = new Column("name", 2, ValueType.String);
        tableColumns[3] = new Column("meetwaarde2", 3, ValueType.Number);

        Table table = new Table(Arrays.asList(tableColumns));

        Record tmp = new Record(table);

        tmp.setValue("date", LocalDate.parse("2013-12-20"));
        tmp.setValue("meetwaarde1", 8.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 20.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDate.parse("2013-12-12"));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Hein");
        tmp.setValue("meetwaarde2", 10.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDate.parse("2013-12-13"));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Dolf");
        tmp.setValue("meetwaarde2", -1.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDate.parse("2013-12-10"));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 10.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDate.parse("2013-11-15"));
        tmp.setValue("meetwaarde1", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde2", 3.0);

        tmp = new Record(table);
        tmp.setValue("date", LocalDate.parse("2013-12-16"));
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
        tmp.setValue("datum", LocalDate.parse("2013-11-15"));
        tmp.setValue("meetwaarde3", 10.0);
        tmp.setValue("name", "Piet");
        tmp.setValue("meetwaarde4", 3.0);

        tmp = new Record(table2);
        tmp.setValue("datum", LocalDate.parse("2013-12-16"));
        tmp.setValue("meetwaarde3", 10.0);
        tmp.setValue("name", "Dolf");
        tmp.setValue("meetwaarde4", 10.0);

        List<ColumnConnection> connections = new ArrayList<ColumnConnection>();
        connections.add(new ColumnConnection("date", "datum", "date"));

        connectedTable = Connect.connect(table, table2, connections);

    }

    @Test
    public void testCols() {
        List<Column> tableCols = connectedTable.getColumns();
        
        assertEquals("date", tableCols.get(0).getName());
        
        assertEquals("name", tableCols.get(2).getName());
        
        assertTrue(tableCols.contains(new Column("meetwaarde1", 0, ValueType.Number)));
    
    }
    
    @Test
    public void testEmptyCells(){
        
        List<Record> tableRecs = connectedTable.getRecords();
        
        assertEquals(null, tableRecs.get(1).getNumberValue("meetwaarde1"));
        double value = tableRecs.get(1).getNumberValue("meetwaarde3");
        double expected = 10;
        assertTrue(expected == value);
    }

}
