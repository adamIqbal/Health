package com.health;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

        assertThat(eList.getList(), contains(e1, e2, e3, e4));
    }

    @Test
    public void testOrderByDate() {
        EventList eList = new EventList();
        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("B", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(3));
        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);

        eList.orderListByDate();

        assertThat(eList.getList(), contains(e2, e3, e4, e1));
    }

    @Test
    public void testOrderByDateWithRecordsNotContainingDate() {
        Table table = new Table(Arrays.asList(new Column("column", 0, ValueType.Number)));
        EventList eList = new EventList();
        Event event = new Event("A", new Record(table));
        eList.addEvent(event);

        eList.orderListByDate();

        assertThat(eList.getList(), hasItem(event));
    }

    @Test
    public void testOrderByDateWithEmptyList() {
        EventList eList = new EventList();

        eList.orderListByDate();

        assertThat(eList.getList(), empty());
    }

    @Test
    public void testOrderByDateWithListContainingDatesNullNull() {
        EventList eList = new EventList();
        Event e0 = new Event("A", new Record(table));
        Event e1 = new Event("B", new Record(table));
        eList.addEvent(e0);
        eList.addEvent(e1);

        eList.orderListByDate();

        assertThat(eList.getList(), containsInAnyOrder(e0, e1));
    }

    @Test
    public void testOrderByDateWithListContainingDatesDateNull() {
        EventList eList = new EventList();
        Event e0 = new Event("A", table.getRecords().get(0));
        Event e1 = new Event("B", new Record(table));
        eList.addEvent(e0);
        eList.addEvent(e1);

        eList.orderListByDate();

        assertThat(eList.getList(), contains(e1, e0));
    }

    @Test
    public void testOrderByDateWithListContainingDatesNullDate() {
        EventList eList = new EventList();
        Event e0 = new Event("A", new Record(table));
        Event e1 = new Event("B", table.getRecords().get(0));
        eList.addEvent(e0);
        eList.addEvent(e1);

        eList.orderListByDate();

        assertThat(eList.getList(), contains(e0, e1));
    }

    @Test
    public void testToTable() {
        EventList eList = new EventList();
        Event e0 = new Event("A", table.getRecords().get(0));
        Event e1 = new Event("A", table.getRecords().get(1));
        Event e2 = new Event("B", table.getRecords().get(3));
        Event e3 = new Event("B", new Record(table));
        eList.addEvent(e0);
        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);

        Table eventTable = eList.toTable();

        assertThat(eventTable.getRecords(), iterableWithSize(4));
    }

    @Test
    public void testToTableWithEmptyEventList() {
        EventList eList = new EventList();

        assertNull(eList.toTable());
    }
}
