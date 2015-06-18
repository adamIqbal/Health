package com.health;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EventListTest {

    Table table;

    @Before
    public void setup() {
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("date", 0, ValueType.Date));
        columns.add(new Column("waarde", 1, ValueType.Number));
        columns.add(new Column("name", 2, ValueType.String));

        table = new Table(columns);

        Record tmp = new Record(table);
        tmp.setValue(0, LocalDateTime.of(10, 1, 1, 0, 0));
        tmp.setValue(1, 1.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDateTime.of(2, 2, 2, 0, 0));
        tmp.setValue(1, 2.0);
        tmp.setValue(2, "Jan");

        tmp = new Record(table);
        tmp.setValue(0, LocalDateTime.of(3, 2, 2, 0, 0));
        tmp.setValue(1, 3.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDateTime.of(4, 2, 2, 0, 0));
        tmp.setValue(1, 4.0);
        tmp.setValue(2, "piet");
    }

    @Test
    public void testConstructor() {
        EventList eList = new EventList();
        assertEquals(0, eList.getList().size());
    }

    @Test
    public void testAddAndGet() {
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));

        eList.addEvent(e1);
        eList.addEvent(e2);

        assertEquals(e2, eList.getEvent(1));
    }

    @Test
    public void testConcat() {
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(1));
        Event e2 = new Event("B", table.getRecords().get(0));

        eList.addEvent(e1);
        eList.addEvent(e2);

        EventList eList2 = new EventList();

        Event e3 = new Event("A", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(3));

        eList2.addEvent(e3);
        eList2.addEvent(e4);
        
        eList.concatList(eList2);
        assertEquals(e3, eList.getList().get(2));

    }
    
    @Test
    public void testOrderByDate(){
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("B", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(3));

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        
        assertEquals(e1, eList.getEvent(0));
        
        eList.orderListByDate();
        
        assertEquals(e1, eList.getEvent(3));

    }

}
