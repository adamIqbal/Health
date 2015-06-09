package com.health;

import java.util.ArrayList;
import java.util.List;

public class AllEvents {
    List<Event> eventList;
    
    public AllEvents(){
        eventList = new ArrayList<Event>();
    }
    
    public void addEvent(Event e){
        eventList.add(e);
    }
    
    public Event getEvent(int index){
        return eventList.get(index);
    }
    
    public List<Event> getEventList(){
        return eventList;
    }
}
