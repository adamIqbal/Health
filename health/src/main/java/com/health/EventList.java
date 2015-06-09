package com.health;

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
}
