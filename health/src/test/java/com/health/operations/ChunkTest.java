package com.health.operations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class ChunkTest {

  private Table table;

  @Before
  public void setup() {
    Column[] tableColumns = new Column[4];
    tableColumns[0] = new Column("meetwaarde1", 1, ValueType.Number);
    tableColumns[1] = new Column("date", 0, ValueType.Date);
    tableColumns[2] = new Column("name", 2, ValueType.String);
    tableColumns[3] = new Column("meetwaarde2", 3, ValueType.Number);

    table = new Table(Arrays.asList(tableColumns));

    // fill the table
    Record tmp = new Record(table);

    tmp.setValue("date", LocalDateTime.of(2013, 12, 20, 0, 0));
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
    tmp.setValue("date", LocalDateTime.of(2013, 11, 15, 0, 0));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Piet");
    tmp.setValue("meetwaarde2", 3.0);

    tmp = new Record(table);
    tmp.setValue("date", LocalDateTime.of(2013, 12, 16, 0, 0));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Dolf");
    tmp.setValue("meetwaarde2", 10.0);

  }

  /*
   * @Test public void testChunkOnDay() { period = Period.ofDays(1); column = "date"; table =
   * Chunk.chunkByTime(table, column, null, period); String countColumn =
   * Chunk.getCountcolumnnametemplate() + column;
   * 
   * List<Record> resultList = table.getRecords(); assertEquals(LocalDate.of(2013, 11, 12),
   * resultList.get(0) .getDateValue(column));
   * 
   * double i = resultList.get(2).getNumberValue(countColumn); double j = 2; assertTrue(j == i);
   * 
   * }
   * 
   * @Test public void testChunkOn2Days() { period = Period.ofDays(2); column = "date"; table =
   * Chunk.chunkByTime(table, column, null, period); String countColumn =
   * Chunk.getCountcolumnnametemplate() + column;
   * 
   * List<Record> resultList = table.getRecords(); assertEquals(LocalDate.of(2013, 11, 12),
   * resultList.get(0) .getDateValue(column));
   * 
   * double i = resultList.get(2).getNumberValue(countColumn); double j = 3; assertTrue(j == i); }
   * 
   * @Test public void testChunkOnWeek() { period = Period.ofWeeks(1); column = "date"; table =
   * Chunk.chunkByTime(table, column, null, period); String countColumn =
   * Chunk.getCountcolumnnametemplate() + column;
   * 
   * List<Record> resultList = table.getRecords(); assertEquals(LocalDate.of(2013, 11, 16),
   * resultList.get(0) .getDateValue(column));
   * 
   * double i = resultList.get(1).getNumberValue(countColumn); double j = 3; assertTrue(j == i); }
   * 
   * @Test public void testChunkOnMonth() { period = Period.ofMonths(1); column = "date"; table =
   * Chunk.chunkByTime(table, column, null, period); String countColumn =
   * Chunk.getCountcolumnnametemplate() + column;
   * 
   * List<Record> resultList = table.getRecords(); assertEquals(LocalDate.of(2013, 11, 16),
   * resultList.get(0) .getDateValue(column));
   * 
   * double i = resultList.get(1).getNumberValue(countColumn); double j = 5; assertTrue(j == i); }
   * 
   * @Test public void testChunkOnString() { column = "name"; table = Chunk.chunkByString(table,
   * column, null); String countColumn = Chunk.getCountcolumnnametemplate() + column;
   * 
   * List<Record> resultList = table.getRecords();
   * 
   * double i = resultList.get(0).getNumberValue(countColumn); double j = 3; assertTrue(j == i); }
   * 
   * @Test public void testChunkOnTimeWithAgg() { period = Period.ofMonths(1); column = "date";
   * 
   * aggregates = new HashMap<String, AggregateFunctions>(); aggregates.put("meetwaarde2",
   * AggregateFunctions.Min); aggregates.put("meetwaarde1", AggregateFunctions.Max);
   * 
   * table = Chunk.chunkByTime(table, column, aggregates, period);
   * 
   * List<Record> resultList = table.getRecords();
   * 
   * double value = resultList.get(1).getNumberValue("meetwaarde1"); double expected = 10;
   * assertTrue(expected == value);
   * 
   * value = resultList.get(1).getNumberValue("meetwaarde2"); expected = -1; assertTrue(expected ==
   * value);
   * 
   * }
   * 
   * @Test public void testChunkWithMin() { column = "name";
   * 
   * aggregates = new HashMap<String, AggregateFunctions>(); aggregates.put("meetwaarde2",
   * AggregateFunctions.Min);
   * 
   * table = Chunk.chunkByString(table, column, aggregates);
   * 
   * List<Record> resultList = table.getRecords();
   * 
   * double value = resultList.get(0).getNumberValue("meetwaarde2"); double expected = 3;
   * assertTrue(expected == value); }
   * 
   * @Test public void testChunkWithMax() { column = "name"; aggregates = new HashMap<String,
   * AggregateFunctions>();
   * 
   * aggregates.put("meetwaarde2", AggregateFunctions.Max); table = Chunk.chunkByString(table,
   * column, aggregates);
   * 
   * List<Record> resultList = table.getRecords();
   * 
   * double value = resultList.get(0).getNumberValue("meetwaarde2"); double expected = 20;
   * assertTrue(expected == value); }
   * 
   * @Test public void testChunkWithSum() { column = "name";
   * 
   * aggregates = new HashMap<String, AggregateFunctions>(); aggregates.put("meetwaarde2",
   * AggregateFunctions.Sum);
   * 
   * table = Chunk.chunkByString(table, column, aggregates);
   * 
   * List<Record> resultList = table.getRecords();
   * 
   * double value = resultList.get(0).getNumberValue("meetwaarde2"); double expected = 33;
   * assertTrue(expected == value); }
   * 
   * @Test public void testChunkWithAverage() { column = "name";
   * 
   * aggregates = new HashMap<String, AggregateFunctions>(); aggregates.put("meetwaarde2",
   * AggregateFunctions.Average);
   * 
   * table = Chunk.chunkByString(table, column, aggregates); List<Record> resultList =
   * table.getRecords();
   * 
   * double value = resultList.get(0).getNumberValue("meetwaarde2"); double expected = 11;
   * assertTrue(expected == value); }
   */
}
