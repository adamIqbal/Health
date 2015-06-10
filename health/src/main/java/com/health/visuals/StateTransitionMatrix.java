package com.health.visuals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.health.Column;
import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * Creates a State Transition Matrix.
 * @author Lizzy Scholten
 *
 */
public final class StateTransitionMatrix {
	
	private StateTransitionMatrix() {
		// Does nothing
	}
	
	public static void main(String[] args) {
		/*
		
		Input:
		List EventSequences, pattern, subset to check within entire event sequence set
		
		*/
		
		//Create dummy input values
		Table table;
    
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
        
        String[] codePat1 = {"B", "A", "A"};
        String[] codePat2 = {"A", "B",};
        String[] codePat3 = {"A", "B", "A"};
        
        EventSequence eSeq1 = new EventSequence(codePat1);
        EventSequence eSeq2 = new EventSequence(codePat2);
        EventSequence eSeq3 = new EventSequence(codePat3);
        
        List<EventSequence> listOfSeq = new ArrayList<EventSequence>();
        
        listOfSeq.add(eSeq1);
        listOfSeq.add(eSeq2);
        listOfSeq.add(eSeq3);
        
		createStateTrans(eList, listOfSeq);
	}
	
	
	public static void createStateTrans(EventList eList, List<EventSequence> seqList){
		
		String[] events = new String[eList.getList().size()];
		int count = 0;
		boolean found = false;
		
		for(Event e: eList.getList()){
			for(int i = 0; i < events.length; i++){
				if(e.getCode().equals(events[i])){
					found = true;
				}
			}
			if(!found){
				events[count] = e.getCode();
				count = count + 1;
			}
			found = false;
		}
		
		for(int t = 0; t < events.length; t++){
			System.out.println(events[t]);
		}
		
		
		
		String[][] matrix = new String[events.length+1][events.length+1];
		int count2 = 0;
		
		for(int k = 1; k < events.length+1; k++){
			matrix[0][k] = events[count2];
			matrix[k][0] = events[count2];
			count2 = count2 + 1;
		}
		
		System.out.println(Arrays.deepToString(matrix));
		
	}
	
}