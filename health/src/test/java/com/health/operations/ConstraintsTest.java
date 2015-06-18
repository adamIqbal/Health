package com.health.operations;

import static com.health.operations.Constraints.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class ConstraintsTest {
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

    tmp.setValue("date", LocalDate.parse("2013-12-12"));
    tmp.setValue("meetwaarde1", 8.0);
    tmp.setValue("name", "Piet");
    tmp.setValue("meetwaarde2", 20.0);

    tmp = new Record(table);
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("date", LocalDate.parse("2013-12-12"));
    tmp.setValue("name", "Hein");
    tmp.setValue("meetwaarde2", 10.0);

    tmp = new Record(table);
    tmp.setValue("date", LocalDate.parse("2013-12-13"));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Dolf");
    tmp.setValue("meetwaarde2", -1.0);

    tmp = new Record(table);
    tmp.setValue("date", LocalDate.parse("2013-12-20"));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Piet");
    tmp.setValue("meetwaarde2", 10.0);

    tmp = new Record(table);
    tmp.setValue("date", LocalDate.parse("2013-12-30"));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Piet");
    tmp.setValue("meetwaarde2", 3.0);

    tmp = new Record(table);
    tmp.setValue("date", LocalDate.parse("2013-11-16"));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Dolf");
    tmp.setValue("meetwaarde2", 10.0);

    tmp = new Record(table);
    tmp.setValue("date", LocalDate.parse("2013-11-12"));
    tmp.setValue("meetwaarde1", 10.0);
    tmp.setValue("name", "Dolf");
    tmp.setValue("meetwaarde2", 10.0);
  }

  @Test
  public void testConstrainString() {
    Table table0 = constrain(table -> table.getStringValue("name").equals("Dolf"), table);

    assertTrue(table0.getRecords().size() == 3);
    assertFalse(table0.getRecords().size() > 3);
    assertFalse(table0.getRecords().size() < 3);

    assertTrue(table0.getRecords().get(2).getNumberValue("meetwaarde2") == 10.0);
    assertFalse(table0.getRecords().contains(null));
  }

  @Test
  public void testConstrainNumber() {
    Table table0 = constrain(table -> table.getNumberValue("meetwaarde1") < 10, table);

    assertTrue(table0.getRecords().size() == 1);
    assertFalse(table0.getRecords().size() > 1);
    assertFalse(table0.getRecords().size() < 1);

    assertFalse(table0.getRecords().contains("Dolf"));
    System.out.println(table0.getRecords().get(0).getStringValue("name"));
    assertTrue(table0.getRecords().get(0).getStringValue("name").equals("Piet"));

    assertFalse(table.getRecords().contains(null));
  }

  @Test
  public void testConstrainDate() {
    Table table1 = table;
    Table table0 = constrain(
        table -> table.getDateValue("date").equals(LocalDate.parse("2013-12-31")), table);

    assertTrue(table0.getRecords().size() == 0);
    assertFalse(table0.getRecords().size() > 0);
    assertFalse(table0.getRecords().size() < 0);

    table1 = constrain(table -> table.getDateValue("date").equals(LocalDate.parse("2013-11-12")),
        table);
    assertTrue(table1.getRecords().size() == 1);
    assertFalse(table1.getRecords().contains(LocalDate.parse("2013-12-30")));
    assertFalse(table1.getRecords().contains(LocalDate.parse("2013-11-12")));
    assertFalse(table1.getRecords().contains(null));
  }
}
