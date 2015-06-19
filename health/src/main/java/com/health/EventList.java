package com.health;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class EventList {
    private List<Event> eventList;

    /**
     * Creates an emty eventList.
     */
    public EventList() {
        eventList = new ArrayList<Event>();
    }

    /**
     * Add an event to the EventList.
     *
     * @param e
     *            an event to add Event.
     */
    public void addEvent(final Event e) {
        eventList.add(e);
    }

    /**
     * Get an event at a given index.
     *
     * @param index
     *            of the event you want to get.
     * @return an event at index.
     */
    public Event getEvent(final int index) {
        return eventList.get(index);
    }

    /**
     * Get an arrayList with all event in the eventList.
     *
     * @return an arrayList with all event in the eventList.
     */
    public List<Event> getList() {
        return eventList;
    }

    /**
     * Concatenates one evenList that to this one.
     *
     * @param that
     *            an other EventList.
     */
    public void concatList(final EventList that) {
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

        if (dateColumn == null) {
            return;
        }

        String dateColumnName = dateColumn.getName();

        sortedEvents.sort((a, b) -> {
            LocalDateTime dateA = a.getRecord().getDateValue(dateColumnName);
            LocalDateTime dateB = b.getRecord().getDateValue(dateColumnName);

            if (dateA == null) {
                if (dateB == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (dateB == null) {
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
        cols.add(new Column("code_name", cols.size(), ValueType.String));
        Table res = new Table(cols);

        for (Event e : eventList) {
            Record rec = new Record(res);

            Record tmp = e.getRecord();
            int i = 0;
            for (Object o : tmp.getValues()) {
                rec.setValue(i, o);
                i++;
            }

            rec.setValue("code_name", e.getCode());

        }

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
}
