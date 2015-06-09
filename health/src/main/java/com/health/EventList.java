package com.health;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventList {
    List<Event> eventList;
    
    public EventList(){
        eventList = new ArrayList<Event>();
    }
    
    public void addEvent(Event e){
        eventList.add(e);
    }
    
    public Event getEvent(int index){
        return eventList.get(index);
    }
    
    public List<Event> getList(){
        return eventList;
    }
    
    public void concatList(EventList that){
        eventList.addAll(that.getList());
    }
    
    public void orderListByDate() {
        List<Event> tmp = new ArrayList<Event>();
        
        int[] found = new int[eventList.size()];

        // fil found with zeros
        for (int i = 0; i < found.length; i++) {
            found[i] = 0;
        }

        while (tmp.size() != eventList.size()) {
            LocalDate minDate = LocalDate.MAX;
            int foundIndex = -1;
            for (int j = 0; j < eventList.size(); j++) {
                if (found[j] == 0) {
                    Record tmpRec = eventList.get(j).getRecord();
                    Column dateCol = tmpRec.getTable().getDateColumn();
                    String dateColName = dateCol.getName();
                    if (dateColName != null && tmpRec.getDateValue(dateColName).isBefore(minDate)) {
                        minDate = tmpRec.getDateValue(dateColName);
                        foundIndex = j;
                    }
                }
            }

            found[foundIndex] = 1;
            tmp.add(eventList.get(foundIndex));
        }
        eventList = tmp;
    }
}
