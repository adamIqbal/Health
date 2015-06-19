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
        tmp.setValue("waarde", 10.0);
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
                AggregateFunctions.sum());
        List<ColumnAggregateTuple> aggregates = new ArrayList<ColumnAggregateTuple>();
        aggregates.add(cat);

        table = chunkByPeriod(table, "date", aggregates, period);

        System.out.println(table.getRecords());

    }

    @Test
    public void testChunkByColumn() {
        fail("Not yet implemented");
    }

}
