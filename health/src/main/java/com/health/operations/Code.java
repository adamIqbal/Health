package com.health.operations;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.health.Event;
import com.health.EventList;
import com.health.Record;
import com.health.Table;

public class Code {
    public static EventList code(Table table,
            Map<String, Function<Record, Boolean>> codes) {
        EventList list = new EventList();

        for (Record record : table.getRecords()) {
            for (Entry<String, Function<Record, Boolean>> entry : codes.entrySet()) {
                if (entry.getValue().apply(record)) {
                    Event tmp = new Event(entry.getKey(), record);
                    list.addEvent(tmp);
                }
            }
        }
        return list;
    }
}
