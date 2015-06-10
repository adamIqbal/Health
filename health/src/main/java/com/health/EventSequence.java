package com.health;

import java.util.ArrayList;
import java.util.List;

public class EventSequence {
    private List<EventList> sequences;
    private String[] codePattern;
    private String code;
    private boolean connected;

    public EventSequence(String[] codePattern) {
        construct(codePattern, null, false);
    }

    public EventSequence(String[] codePattern, boolean connected) {
        construct(codePattern, null, connected);
    }

    public EventSequence(String[] codePattern, String code, boolean connected) {
        construct(codePattern, code, connected);
    }

    private void construct(String[] codePattern, String code, boolean connected) {
        sequences = new ArrayList<EventList>();
        this.codePattern = codePattern;
        this.connected = connected;
        if(code == null){
            makeCodeOfCodePattern();
        }else{
            this.setCode(code);
        }
    }

    public String[] getCodePattern() {
        return codePattern;
    }

    private void makeCodeOfCodePattern() {
        setCode("");
        for (String singleCode : codePattern) {
            setCode(getCode() + singleCode);
        }

    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }
    
    public void addSequence(EventList sequence){
        sequences.add(sequence);
    }
    
    public void deleteSequence(EventList sequence){
        sequences.remove(sequence);
    }

    public List<EventList> getSequences() {
        return sequences;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
}
