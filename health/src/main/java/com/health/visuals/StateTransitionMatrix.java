package com.health.visuals;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
public final class StateTransitionMatrix extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StateTransitionMatrix() {
		// Does nothing
	}
	
	public static void main(String[] args){
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
        Event e6 = new Event("C", table.getRecords().get(4));

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        eList.addEvent(e5);
        eList.addEvent(e6);
        
        String[] codePat1 = {"B", "A", "A", "B"};
        String[] codePat2 = {"A", "A", "B"};
        String[] codePat3 = {"A", "B", "A", "C"};
        String[] codePat4 = {"C", "B", "C"};
        
        EventSequence eSeq1 = new EventSequence(codePat1);
        EventSequence eSeq2 = new EventSequence(codePat2);
        EventSequence eSeq3 = new EventSequence(codePat3);
        EventSequence eSeq4 = new EventSequence(codePat4);
        
        List<EventSequence> listOfSeq = new ArrayList<EventSequence>();
        
        listOfSeq.add(eSeq1);
        listOfSeq.add(eSeq2);
        listOfSeq.add(eSeq3);
        listOfSeq.add(eSeq4);
        
		StateTransitionMatrix.createStateTrans(eList, listOfSeq);
		
	}
	
	
	public static void createStateTrans(EventList eList, List<EventSequence> seqList){
		
		JFrame vidney = new JFrame();
		vidney.setVisible(true);
		
		vidney.setTitle("State Transition");
		vidney.setSize(1000
				,1000);
		vidney.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		ArrayList<String> eArr = new ArrayList<String>();
		
		for(Event e: eList.getList()){
			if(eArr.contains(e.getCode())){
			
			}
			else {
				eArr.add(e.getCode());
			}
		}
		
		for(String s: eArr){
			System.out.println(s);
		}	
		
		String[][] matrix = new String[eArr.size()+1][eArr.size()+1];
		
		matrix[0][0] = "Event types";
		
		for(int k = 1; k < eArr.size()+1; k++){
			matrix[0][k] = eArr.get(k-1);
			matrix[k][0] = eArr.get(k-1);
		}
		
		System.out.println(Arrays.deepToString(matrix));
			
		//Example:
		//B A A
		//A B
		//A B A
		for(EventSequence eSeq: seqList){
			String[] codePat = eSeq.getCodePattern();
			
			for(int c = 1; c < codePat.length; c++){
				String from = codePat[c-1];
				String to = codePat[c];
				
				for(int a = 1; a < matrix[0].length; a++){
					boolean found1 = false;
					
					System.out.println("matrix a0 : "+ matrix[a][0]);
					System.out.println("from : "+ from);
					
					if(matrix[a][0].equals(from)){
						for(int b = 1; b < matrix[1].length; b++){
							System.out.println("matrix 0b : "+ matrix[0][b]);
							System.out.println("to : "+ to);
							if(matrix[0][b].equals(to)){
								if (matrix[a][b] == null) {
									matrix[a][b] = "1";
								}
								else {
									int val = Integer.parseInt(matrix[a][b]);
									val = val + 1;
									matrix[a][b] = Integer.toString(val);
								}
								found1 = true;
								break;
							}		
						}
					}
					
					if(found1){
						break;
					}
				}
			}
			
		}	
		
		printMatrix(matrix);
		
		
		String[][] outputM = new String[matrix.length-1][matrix.length];
		
		for(int i = 1; i < matrix[0].length; i++){
			for(int j = 0; j < matrix[1].length; j++){
				outputM[i-1][j] = matrix[i][j];
			}
		}
		
		System.out.println(Arrays.deepToString(matrix));
		
		JTable table = new JTable(outputM, matrix[0]);
	
		Container c = vidney.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JScrollPane(table), BorderLayout.CENTER);
		
	}	
	
	public static void printMatrix(String[][] matrix) {
        
    }
	
}