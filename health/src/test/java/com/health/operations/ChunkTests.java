package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

import static com.health.operations.Chunk.*;

public class ChunkTests {

    private Table table;

    @Before
    public void setup() {
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("date", 0, ValueType.Date));
        columns.add(new Column("waarde", 1, ValueType.Number));
        columns.add(new Column("name", 2, ValueType.String));

        table = new Table(columns);

        Record tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
        tmp.setValue("waarde", 8.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 22, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Peter");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 26, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "dolfje");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 27, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 29, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Jan");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "dolfje");

        tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
        tmp.setValue("waarde", 8.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 12, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Hein");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 13, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Dolf");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 10, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 11, 15, 0, 0));
        tmp.setValue("waarde", 25.0);
        tmp.setValue("name", "Piet");

        tmp = new Record(table);
        tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
        tmp.setValue("waarde", 10.0);
        tmp.setValue("name", "Dolf");

    }

    @Test
    public void testChunkByPeriods() {
        TemporalAmount period = Period.parse("P6D");

        ColumnAggregateTuple cat = new ColumnAggregateTuple("meetwaarde2",
                AggregateFunctions.average());
        List<ColumnAggregateTuple> aggregates = new ArrayList<ColumnAggregateTuple>();
        aggregates.add(cat);

        assertFalse(table.getRecords().get(0).getValue("date")
                .equals(LocalDateTime.of(2013, 11, 15, 0, 0)));
        assertNotEquals(table.getRecords().size(), 5);
        assertEquals(table.getRecords().size(), 11);

        table = chunkByPeriod(table, "date", aggregates, period);

        assertTrue(table.getRecords().get(0).getValue("date")
                .equals(LocalDateTime.of(2013, 11, 15, 0, 0)));
        assertEquals(table.getRecords().size(), 5);
        assertFalse(table.getRecords().get(0).getValue("date")
                .equals(LocalDateTime.of(2013, 11, 14, 0, 0)));
    }

    @Test
    public void testChunkByPeriodsEmpty() {
        TemporalAmount period = Period.parse("P6D");

        List<ColumnAggregateTuple> aggregates = new ArrayList<ColumnAggregateTuple>();

        assertFalse(table.getRecords().get(0).getValue("date")
                .equals(LocalDateTime.of(2013, 11, 15, 0, 0)));
        assertNotEquals(table.getRecords().size(), 5);
        assertEquals(table.getRecords().size(), 11);

        table = chunkByPeriod(table, "date", aggregates, period);

        assertTrue(table.getRecords().get(0).getValue("date")
                .equals(LocalDateTime.of(2013, 11, 15, 0, 0)));
        assertEquals(table.getRecords().size(), 5);
        assertFalse(table.getRecords().get(0).getValue("date")
                .equals(LocalDateTime.of(2013, 11, 14, 0, 0)));
    }

    @Test
    public void testChunkByColumn() {

        ColumnAggregateTuple cat = new ColumnAggregateTuple("waarde",
                AggregateFunctions.max());
        List<ColumnAggregateTuple> aggregates = new ArrayList<ColumnAggregateTuple>();
        aggregates.add(cat);

        assertFalse(table.getRecords().get(0).getValue("waarde").equals(10.0));
        assertTrue(table.getRecords().get(0).getValue("waarde").equals(8.0));
        assertNotEquals(table.getRecords().size(), 5);
        assertEquals(table.getRecords().size(), 11);

        table = chunkByColumn(table, "waarde", aggregates);

        assertTrue(table.getRecords().get(2).getNumberValue("waarde") == 25.0);
        assertTrue(table.getRecords().get(1).getNumberValue("waarde") == 10.0);
        assertTrue(table.getRecords().get(0).getNumberValue("waarde") == 8.0);
    }
}
