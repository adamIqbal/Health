package com.health;

import java.util.ArrayList;
import java.util.List;

import com.health.operations.Connect;

public class EventSequence {
    private List<EventList> sequences;
    private String[] codePattern;
    private String code;
    private boolean connected;

    /**
     * Makes an eventSequence with given Pattern.
     * @param codePattern the pattern one would like to look for.
     */
    public EventSequence(final String[] codePattern) {
        construct(codePattern, null, false);
    }

    /**
     * Makes an eventSequence with given Pattern.
     * @param codePattern the pattern one would like to look for.
     * @param connected true if the Sequence has to be connected to each other.
     */
    public EventSequence(final String[] codePattern, final boolean connected) {
        construct(codePattern, null, connected);
    }

    /**
     * Makes an eventSequence with given Pattern.
     * @param codePattern the pattern one would like to look for.
     * @param code the code for the sequence.
     * @param connected true if the Sequence has to be connected to each other.
     */
    public EventSequence(final String[] codePattern, final String code,
            final boolean connected) {
        construct(codePattern, code, connected);
    }

    private void construct(final String[] codePattern, final String code,
            final boolean connected) {
        sequences = new ArrayList<EventList>();
        this.codePattern = codePattern;
        this.connected = connected;
        if (code == null) {
            makeCodeOfCodePattern();
        } else {
            this.setCode(code);
        }
    }

    /**
     * Get the code Pattern of the sequence.
     * @return an String array with the codes of the pattern.
     */
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
     * @return connected.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Add a sequence to all found sequences in the list.
     * @param sequence an EvenList with record of a sequence.
     */
    public void addSequence(final EventList sequence) {
        sequences.add(sequence);
    }

    /**
     * Delete a sequence.
     * @param sequence the sequence to delete.
     */
    public void deleteSequence(final EventList sequence) {
        sequences.remove(sequence);
    }

    /**
     * Get all sequences in the object.
     * @return a list of evenlists with sequences.
     */
    public List<EventList> getSequences() {
        return sequences;
    }

    /**
     * @return the code of the sequence.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Makes a Table of all events in all eventlists in the current object.
     * @return a table with all events found.
     */
    public Table toTable() {
        Table res = null;

        for (EventList eList : sequences) {
            if (res == null) {
                res = eList.toTable();
            } else {
                res = Connect.connect(res, eList.toTable(), null);
            }

        }

        return res;
    }
}
