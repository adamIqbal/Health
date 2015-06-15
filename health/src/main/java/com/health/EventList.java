package com.health;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventList {
    List<Event> eventList;

    public EventList() {
        eventList = new ArrayList<Event>();
    }

    public void addEvent(Event e) {
        eventList.add(e);
    }

    public Event getEvent(int index) {
        return eventList.get(index);
    }

    public List<Event> getList() {
        return eventList;
    }

    public void concatList(EventList that) {
        eventList.addAll(that.getList());
    }

    /**
     * Orders an this list by date.
     */
    public void orderListByDate() {
        List<Event> sortedEvents = new ArrayList<Event>(eventList);

        if (sortedEvents.size() <= 0) {
            return;
        }

        Table table = sortedEvents.get(0).getRecord().getTable();
        Column dateColumn = table.getDateColumn();
        String dateColumnName = dateColumn.getName();

        if (dateColumnName == null) {
            return;
        }

        sortedEvents.sort((a, b) -> {
            LocalDate dateA = a.getRecord().getDateValue(dateColumnName);
            LocalDate dateB = b.getRecord().getDateValue(dateColumnName);

            if (dateA == null && dateB == null) {
                return 0;
            } else if (dateA == null && dateB != null) {
                return -1;
            } else if (dateA != null && dateB == null) {
                return 1;
            } else {
                return dateA.compareTo(dateA);
            }
        });

        eventList = sortedEvents;
    }
}
