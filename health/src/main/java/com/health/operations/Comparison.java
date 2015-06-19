package com.health.operations;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.health.Column;
import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * A class to compare EventSequences.
 * @author daan
 *
 */
public class Comparison {

	/**
	 * a unused constructor.
	 */
	protected Comparison() {
	}

	/**
	 * A function to make a table with the time between events in DAYS.
	 *
	 * @param eventSeq
	 *            , an EventSeqence for which you want to calculate the time
	 *            between the events in the sequence.
	 * @return a Table with a column for every transition between events and one
	 *         for between last and first. For example: sequence {A,B,C}, will
	 *         get columns:{A-&#62;B, B-&#62;C, A-&#62;C}.
	 */
	public static Table timeBetweenEvents(final EventSequence eventSeq) {
		String[] codeSeq = eventSeq.getCodePattern();
		List<Column> colList = makeColListForTBE(codeSeq);

		Table result = new Table(colList);

		List<EventList> eventSeqList = eventSeq.getSequences();

		int cols = codeSeq.length;
		if (cols == 2) {
			cols--;
		}
		double[][] timesBetween = new double[eventSeqList.size()][cols];

		fillTimesBetween(codeSeq, eventSeqList, timesBetween);

		Record rec;

		for (int i = 0; i < timesBetween.length; i++) {
			rec = new Record(result);
			for (int j = 0; j < timesBetween[i].length; j++) {
				rec.setValue(j, timesBetween[i][j]);
			}
		}

		return result;
	}

	private static List<Column> makeColListForTBE(final String[] codeSeq) {
		List<Column> result = new ArrayList<Column>();

		for (int i = 0; i < codeSeq.length; i++) {
			if (i + 1 < codeSeq.length) {
				Column newCol = new Column(codeSeq[i] + "->" + codeSeq[i + 1],
						result.size(), ValueType.Number);
				result.add(newCol);
			} else if (i > 1) {
				Column newCol = new Column(codeSeq[0] + "->" + codeSeq[i],
						result.size(), ValueType.Number);
				result.add(newCol);
			}
		}

		return result;
	}

	private static void fillTimesBetween(final String[] codeSeq,
			final List<EventList> eventSeqList, final double[][] timesBetween) {
		for (int i = 0; i < eventSeqList.size(); i++) {
			EventList eList = eventSeqList.get(i);

			for (int j = 0; j < codeSeq.length; j++) {
				if (j + 1 < codeSeq.length) {
					Event event1 = eList.getEvent(j);
					Event event2 = eList.getEvent(j + 1);
					timesBetween[i][j] = getTimeBetweenEvents(event1, event2);
				} else if (j > 1) {
					Event event1 = eList.getEvent(0);
					Event event2 = eList.getEvent(j);
					timesBetween[i][j] = getTimeBetweenEvents(event1, event2);
				}

			}
		}
	}

	private static int getTimeBetweenEvents(final Event event1,
			final Event event2) {
		long days = event1.getDate().until(event2.getDate(), ChronoUnit.DAYS);

		return (int) days;

	}
}
