package com.health;

public class Event {
    private String codeName;
    private Record rec;
    
    public Event(String code, Record record){
        codeName = code;
        rec = record;
    }

    public String getCode() {
        return codeName;
    }

    public void setCode(String code) {
        this.codeName = code;
    }

    public Record getRecord() {
        return rec;
    }

    public void setRecord(Record record) {
        this.rec = record;
    }
    
    
}
