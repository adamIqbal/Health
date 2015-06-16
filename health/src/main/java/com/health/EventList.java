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
                return dateA.compareTo(dateB);
            }
        });

        eventList = sortedEvents;
    }

    /**
     * Turns this eventList into a table.
     * 
     * @return a Table format with all element in the eventSequence
     */
    public Table toTable() {
        if (this.eventList.size() <= 0) {
            return null;
        }
        List<Column> cols = makeTableCols();
        Table res = new Table(cols);
        addAllRecords(res, cols);

        return res;
    }

    private List<Column> makeTableCols() {
        List<Column> cols = new ArrayList<Column>();

        for (Event e : this.eventList) {
            List<Column> colsE = e.getRecord().getTable().getColumns();
            for (Column c : colsE) {
                if (!cols.contains(c)) {
                    cols.add(c);
                }
            }
        }

        return cols;
    }

    private void addAllRecords(Table table, List<Column> cols) {
        for (Event e : this.eventList) {
            Record rec = e.getRecord();

            Record newRec = new Record(table);
            for (Column c : cols) {
                Object val = rec.getValue(c.getName());
                if (val != null) {
                    newRec.setValue(c.getName(), val);
                }
            }
        }
    }
}
