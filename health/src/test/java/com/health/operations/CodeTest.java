package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
        tmp.setValue(0, LocalDate.of(1, 1, 1));
        tmp.setValue(1, 1.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(2, 2, 2));
        tmp.setValue(1, 2.0);
        tmp.setValue(2, "Jan");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(3, 2, 3));
        tmp.setValue(1, 3.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(4, 2, 2));
        tmp.setValue(1, 4.0);
        tmp.setValue(2, "piet");
        
        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(4, 2, 3));
        tmp.setValue(1, 2.0);
        tmp.setValue(2, "Jan");
    }
    
    @Test
    public void testNoCodes() {
        Map<String, Function<Record, Boolean>> codes = null;
        EventList eList = Code.makeEventList(table, codes);
        assertEquals(0, eList.getList().size());
    }
    
    @Test
    public void testForDev(){
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("A", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(3));
        Event e5 = new Event("A", table.getRecords().get(4));

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        eList.addEvent(e5);
        
        String[] codePattern = {"B", "A"};
        
        EventSequence eSeq = new EventSequence(codePattern);
        
        Code.fillEventSequenceWithinPeriod(eSeq, eList, Period.ofYears(1));
        System.out.println("-----------------");
        System.out.println(eSeq.getSequences().size());
    }

}
