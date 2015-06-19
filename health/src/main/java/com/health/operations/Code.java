package com.health.operations;

import java.time.LocalDateTime;

import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;
import com.health.Record;
import com.health.Table;

/**
 * A class for all coding operations.
 *
 * @author daan
 *
 */
public final class Code {

	/**
	 * an unused constructor.
	 */
	private Code() {
	}

	/**
	 * Looks for all event declared and puts them in an evenList.
	 * 
	 * @param table
	 *            the table in which to look for events.
	 * @param codes
	 *            the event declaration.
	 * @return an EventList with all found events.
	 */
	public static EventList makeEventList(final Table table,
			final Map<String, Function<Record, Boolean>> codes) {
		EventList list = new EventList();
		if (codes == null)
			return list;
		for (Record record : table.getRecords()) {
			for (Entry<String, Function<Record, Boolean>> entry : codes
					.entrySet()) {
				if (entry.getValue().apply(record))
					list.addEvent(new Event(entry.getKey(), record));

			}
		}

		list.orderListByDate();
		return list;
	}

	/**
	 * Fills up an EventSequence object with EventLists with the codePatern
	 * declared in the EventSequence Object.
	 * 
	 * @param eventSeq
	 *            the EventSequence to be Filled.
	 * @param eventList
	 *            the EventList Object in which to look for the pattern.
	 */
	public static List<EventList> fillEventSequence(
			final EventSequence eventSeq, final EventList eventList) {
		eventList.orderListByDate();
		List<Event> eList = eventList.getList();
		String[] codePattern = eventSeq.getCodePattern();

		int currentIndex = 0;

		while (currentIndex < eList.size()) {
			EventList tmpEList = new EventList();

			makeEventList: if (eventSeq.isConnected()) {

				currentIndex = findEventInSeq(codePattern[0], eventList,
						currentIndex);

				// start of pattern not found
				if (currentIndex == -1) {
					break;
				}

				tmpEList.addEvent(eList.get(currentIndex));
				int oldIndex = currentIndex;
				int nextIndex = ++currentIndex;
				for (int i = 1; i < codePattern.length; i++) {
					nextIndex = findEventInSeq(codePattern[i], eventList,
							nextIndex);
					if (nextIndex - oldIndex == 1) {
						tmpEList.addEvent(eList.get(nextIndex));
						oldIndex = nextIndex;
						nextIndex++;
					} else {
						break makeEventList;
					}
				}

				eventSeq.addSequence(tmpEList);
			} else {
				// start of pattern not found
				if (currentIndex == -1) {
					break;
				}

				for (String code : codePattern) {
					currentIndex = findEventInSeq(code, eventList, currentIndex);
					if (currentIndex == -1) {
						break makeEventList;
					}
					tmpEList.addEvent(eList.get(currentIndex));
					currentIndex++;
				}

				eventSeq.addSequence(tmpEList);
			}

		}

		return eventSeq.getSequences();
	}

	/**
	 * Fills up an EventSequence object with EventLists with the codePatern
	 * declared in the EventSequence Object.
	 * 
	 * @param eventSeq
	 *            the EventSequence to be Filled.
	 * @param eventList
	 *            the EventList Object in which to look for the pattern.
	 * @param per
	 *            the maximum amount of time between the first event of the
	 *            sequence and the last.
	 */
	public static void fillEventSequenceWithinPeriod(
			final EventSequence eventSeq, final EventList eventList,
			final Period per) {
		fillEventSequence(eventSeq, eventList);

		deleteOutSideOfPeriod(eventSeq, per);
	}

	private static int findEventInSeq(final String code,
			final EventList eventList, final int currentIndex) {

		List<Event> eList = eventList.getList();
		try {
			for (int i = currentIndex; i < eList.size(); i++) {
				if (eList.get(i).getCode().equals(code)) {
					return i;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
		return -1;
	}

	private static void deleteOutSideOfPeriod(final EventSequence eventSeq,
			final Period per) {
		List<EventList> seqList = eventSeq.getSequences();
		for (EventList eventList : seqList) {
			Event firstEvent = eventList.getList().get(0);
			Event lastEvent = eventList.getList().get(
					eventList.getList().size() - 1);

			LocalDateTime endOfPer = firstEvent.getDate().plus(per);
			if (lastEvent.getDate().isAfter(endOfPer)) {
				eventSeq.deleteSequence(eventList);
			}
		}
	}
}
