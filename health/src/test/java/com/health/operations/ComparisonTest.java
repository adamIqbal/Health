package com.health.operations;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class ComparisonTest {
    Table table;
    
    @Before
    public void setup(){
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
        tmp.setValue("date", LocalDate.parse("2014-11-16"));
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
    public void testTripleEvent() {
        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("C", table.getRecords().get(2));
        Event e4 = new Event("A", table.getRecords().get(3));
        Event e5 = new Event("B", table.getRecords().get(4));
        Event e6 = new Event("C", table.getRecords().get(5));
        

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        eList.addEvent(e5);
        eList.addEvent(e6);
        
        String[] codePattern = {"A","B", "C"};
        
        EventSequence eSeq = new EventSequence(codePattern);
        Code.fillEventSequence(eSeq, eList);
        
        Table res = Comparison.timeBetweenEvents(eSeq);
        List<Record> recList = res.getRecords();
        double expected = 27.0;
        double actual = recList.get(1).getNumberValue("A->C");
        assertTrue(expected == actual);
        
        
    }

}
