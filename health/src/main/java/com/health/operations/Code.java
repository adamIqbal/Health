package com.health.operations;

import java.time.LocalDate;
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

public class Code {
    public static EventList makeEventList(Table table,
            Map<String, Function<Record, Boolean>> codes) {
        EventList list = new EventList();

        if (codes == null) {
            return list;
        }
        for (Record record : table.getRecords()) {
            for (Entry<String, Function<Record, Boolean>> entry : codes
                    .entrySet()) {
                if (entry.getValue().apply(record)) {
                    Event tmp = new Event(entry.getKey(), record);
                    list.addEvent(tmp);
                }
            }
        }
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
    public static void fillEventSequence(EventSequence eventSeq,
            EventList eventList) {
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
                currentIndex++;
                

                for (int i = 1; i < codePattern.length; i++) {
                    currentIndex = findEventInSeq(codePattern[i], eventList,
                            currentIndex);
                    if (currentIndex - oldIndex == 1) {
                        tmpEList.addEvent(eList.get(currentIndex));
                        oldIndex = currentIndex;
                        currentIndex++;
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
                    if(currentIndex == -1){
                        break makeEventList;
                    }
                    tmpEList.addEvent(eList.get(currentIndex));
                    currentIndex++;
                }
                
                eventSeq.addSequence(tmpEList);
            }

        }
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
    public static void fillEventSequenceWithinPeriod(EventSequence eventSeq,
            EventList eventList, Period per) {
        fillEventSequence(eventSeq, eventList);

        deleteOutSideOfPeriod(eventSeq, per);
    }

    private static int findEventInSeq(String code, EventList eventList,
            int currentIndex) {

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

    private static void deleteOutSideOfPeriod(EventSequence eventSeq, Period per){
        List<EventList> seqList = eventSeq.getSequences();
        for(EventList eventList : seqList){
            Event firstEvent = eventList.getList().get(0);
            Event lastEvent = eventList.getList().get(eventList.getList().size() -1);
            
            LocalDate endOfPer = firstEvent.getDate().plus(per);
            if(lastEvent.getDate().isAfter(endOfPer)){
                eventSeq.deleteSequence(eventList);
            }
        }
    }
}
