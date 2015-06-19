package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.health.output.Output;

public class CodeTest {
    Table table;

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
    public void testNoCodes() {
        Map<String, Function<Record, Boolean>> codes = null;
        EventList eList = Code.makeEventList(table, codes);
        assertEquals(0, eList.getList().size());
        assertFalse(eList.getList().size() > 0);
        assertFalse(eList.getList().size() != 0);
    }

    @Test
    public void testCodesSize() {

        Map<String, Function<Record, Boolean>> map = new HashMap<>();
        map.put("A", table -> table.getStringValue("name").contains("Dolf"));
        map.put("B", table -> table.getStringValue("name").contains("Piet"));

        EventList eList = Code.makeEventList(table, map);
        assertNotEquals(0, eList.getList().size());
        assertTrue(eList.getList().size() == 5);
        assertFalse(eList.getList().size() != 5);

    }

    @Test
    public void testCodesEventList() {

        Map<String, Function<Record, Boolean>> map = new HashMap<>();
        map.put("A", table -> table.getStringValue("name").contains("Dolf"));
        map.put("B", table -> table.getStringValue("name").contains("Piet"));

        EventList eList = Code.makeEventList(table, map);
        assertNotEquals(0, eList.getList().size());
        assertTrue(eList.getList().size() == 5);
        assertFalse(eList.getList().size() != 5);

        assertTrue(eList.getEvent(0).getCode().equals("B"));
        assertTrue(eList.getEvent(2).getCode().equals("A"));

    }

    @Test
    public void testCodesEventListsEmpty() {

        Map<String, Function<Record, Boolean>> map = new HashMap<>();
        Map<String, Function<Record, Boolean>> maps = new HashMap<>();
        map.put("A", table -> table.getStringValue("name").contains("Dolf"));
        map.put("B", table -> table.getStringValue("name").contains("Piet"));

        EventList eList = Code.makeEventList(table, maps);
        assertEquals(0, eList.getList().size());

    }

    @Test
    public void testFillEventSequence() {
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("A", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(3));
        Event e5 = new Event("C", table.getRecords().get(4));

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        eList.addEvent(e5);

        String[] codePattern = { "A", "B" };

        EventSequence eSeq = new EventSequence(codePattern, true);

        assertTrue(Code.fillEventSequence(eSeq, eList).size() == 2);
    }


    @Test
    public void testForDeve() {
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("A", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(3));
        Event e5 = new Event("C", table.getRecords().get(4));

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        eList.addEvent(e5);

        String[] codePattern = { "A", "B" };

        EventSequence eSeq = new EventSequence(codePattern, true);

        Code.fillEventSequence(eSeq, eList);

    }

}
