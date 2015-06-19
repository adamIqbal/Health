package com.health;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.health.operations.Connect;

public final class EventSequence {
    private final List<EventList> sequences;
    private final String[] codePattern;
    private final String code;
    private final boolean connected;

    /**
     * Makes an eventSequence with given Pattern.
     *
     * @param codePattern
     *            the pattern one would like to look for.
     */
    public EventSequence(final String[] codePattern) {
        this(codePattern, null, false);
    }

    /**
     * Makes an eventSequence with given Pattern.
     *
     * @param codePattern
     *            the pattern one would like to look for.
     * @param connected
     *            true if the Sequence has to be connected to each other.
     */
    public EventSequence(final String[] codePattern, final boolean connected) {
        this(codePattern, null, connected);
    }

    /**
     * Makes an eventSequence with given Pattern.
     *
     * @param codePattern
     *            the pattern one would like to look for.
     * @param code
     *            the code for the sequence.
     * @param connected
     *            true if the Sequence has to be connected to each other.
     */
    public EventSequence(final String[] codePattern, final String code,
            final boolean connected) {
        Objects.requireNonNull(codePattern);

        this.sequences = new ArrayList<EventList>();
        this.codePattern = codePattern;
        this.connected = connected;

        if (code == null) {
            this.code = makeCodeOfCodePattern(codePattern);
        } else {
            this.code = code;
        }
    }

    /**
     * Gets the pattern of events represented by this sequence.
     *
     * @return the pattern of events represented by this sequence.
     */
    public String[] getCodePattern() {
        return codePattern;
    }

    /**
     * Gets the code associated with this event sequence.
     *
     * @return the code associated with this event sequence.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets whether or not the events in the pattern must be consecutive.
     *
     * @return true if the events in the pattern must be consecutive; otherwise
     *         false.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Add a sequence to all found sequences in the list.
     *
     * @param sequence
     *            an EvenList with record of a sequence.
     */
    public void addSequence(final EventList sequence) {
        sequences.add(sequence);
    }

    /**
     * Removes a sequence.
     *
     * @param sequence
     *            the sequence to remove.
     */
    public void removeSequence(final EventList sequence) {
        sequences.remove(sequence);
    }

    /**
     * Get all sequences in the object.
     *
     * @return a list of evenlists with sequences.
     */
    public List<EventList> getSequences() {
        return Collections.unmodifiableList(this.sequences);
    }

    /**
     * Makes a Table of all events in all eventlists in the current object.
     *
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

    private static String makeCodeOfCodePattern(final String[] codePattern) {
        String code = "";

        for (String singleCode : codePattern) {
            code += singleCode;
        }

        return code;
    }
}
