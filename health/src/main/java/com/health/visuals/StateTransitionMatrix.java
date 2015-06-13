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
public final class StateTransitionMatrix extends JFrame {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private StateTransitionMatrix() {
		// Does nothing
	}

	/**
	 * Main method for testing class.
	 * @param args
	 * 				args
	 */
	public static void main(final String[] args) {
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

        final int two = 2;
		final double two2 = 2.0;
		final int three = 3;
		final double three2 = 3.0;
		final int four = 4;
		final double four2 = 4.0;

        Record tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(1, 1, 1));
        tmp.setValue(1, 1.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(2, 2, 2));

        tmp.setValue(1, 2.0);
        tmp.setValue(2, "Jan");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(three, two, three));
        tmp.setValue(1, three2);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(four, two, two));
        tmp.setValue(1, four2);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(four, two, three));
        tmp.setValue(1, two2);
        tmp.setValue(2, "Jan");

        EventList eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("A", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(three));
        Event e5 = new Event("A", table.getRecords().get(four));
        Event e6 = new Event("C", table.getRecords().get(four));

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

	/**
	 * Create State Transition Matrix.
	 * @param eList
	 * 				list with possible events
	 * @param seqList
	 * 				list with sequences
	 */
	public static void createStateTrans(final EventList eList, final List<EventSequence> seqList) {
		// Create frame
		final int thousand = 1000;

		JFrame vidney = new JFrame();
		vidney.setVisible(true);

		vidney.setTitle("State Transition");
		vidney.setSize(thousand, thousand);
		vidney.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[][] matrix = setUp(eList);
		String[][] matrixUse = fillMatrix(matrix, seqList);
		String[][] outputM = new String[matrixUse.length - 1][matrixUse.length];

		for (int i = 1; i < matrixUse[0].length; i++) {
			for (int j = 0; j < matrixUse[1].length; j++) {
				outputM[i - 1][j] = matrixUse[i][j];
			}
		}

		JTable table = new JTable(outputM, matrixUse[0]);

		Container c = vidney.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	/**
	 * Set up matrix.
	 * @param eList
	 * 			event list
	 * @return
	 * 			matrix setup
	 */
	private static String[][] setUp(final EventList eList){

		ArrayList<String> eArr = new ArrayList<String>();

		for (Event e: eList.getList()) {
			if (!eArr.contains(e.getCode())) {
				eArr.add(e.getCode());
			}
		}

		String[][] matrix = new String[eArr.size() + 1][eArr.size() + 1];

		matrix[0][0] = "Event types";

		for (int k = 1; k < eArr.size() + 1; k++) {
			matrix[0][k] = eArr.get(k - 1);
			matrix[k][0] = eArr.get(k - 1);
		}
		
		return matrix;
	}
	
	/**
	 * Fill matrix.
	 * @param m
	 * 			matrix
	 * @param seqList
	 * 			list of sequences
	 * @return
	 * 			matrix
	 */
	private static String[][] fillMatrix(final String[][] m, final List<EventSequence> seqList) {
		String[][] matrix = m;
		for (EventSequence eSeq: seqList) {
			String[] codePat = eSeq.getCodePattern();

			for (int c = 1; c < codePat.length; c++) {
				String from = codePat[c - 1];
				String to = codePat[c];

				for (int a = 1; a < matrix[0].length; a++) {
					boolean found1 = false;
					if (matrix[a][0].equals(from)) {
						for (int b = 1; b < matrix[1].length; b++) {
							if (matrix[0][b].equals(to)) {
								if (matrix[a][b] == null) {
									matrix[a][b] = "1";
								} else {
									int val = Integer.parseInt(matrix[a][b]);
									val = val + 1;
									matrix[a][b] = Integer.toString(val);
								}
								found1 = true;
								break;
							}
						}
					}
					if (found1) {
						break;
					}
				}
			}
		}
		return matrix;
	}
}