package com.health;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EventTest {

    private Table table;

    @Before
    public void setup() {
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("date", 0, ValueType.Date));
        columns.add(new Column("waarde", 1, ValueType.Number));
        columns.add(new Column("name", 2, ValueType.String));

        table = new Table(columns);

        Record tmp = new Record(table);
        tmp.setValue(0, LocalDateTime.of(1, 1, 1, 0, 0));
        tmp.setValue(1, 1.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDateTime.of(2, 2, 2, 0, 0));
        tmp.setValue(1, 2.0);
        tmp.setValue(2, "Jan");

    }

    @Test
    public void testConstructor() {
        Event e = new Event("A", table.getRecords().get(0));

        assertEquals("A", e.getCode());
        assertEquals("piet", e.getRecord().getStringValue("name"));
    }

    @Test
    public void testSetters() {
        Event e = new Event("A", table.getRecords().get(0));

        e.setCode("B");
        e.setRecord(table.getRecords().get(1));
        
        assertEquals("B", e.getCode());
        assertEquals("Jan", e.getRecord().getStringValue("name"));
    }

}
