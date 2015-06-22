package com.health;

import java.time.LocalDateTime;

/**
 * Represents an event that is labeled with a code.
 *
 * @author daan.
 *
 */
public final class Event {
    private String codeName;
    private Record rec;

    /**
     * Create a new Event.
     *
     * @param code
     *            the name of the code.
     * @param record
     *            the Record of the event.
     */
    public Event(final String code, final Record record) {
        codeName = code;
        rec = record;
    }

    /**
     * get the code name of the event.
     *
     * @return the codeName.
     */
    public String getCode() {
        return codeName;
    }

    /**
     * set the code name of the event.
     *
     * @param code
     *            the name of the code.
     */
    public void setCode(final String code) {
        this.codeName = code;
    }

    /**
     * get the record of the event.
     *
     * @return the record of the event.
     */
    public Record getRecord() {
        return rec;
    }

    /**
     * set the record of the event.
     *
     * @param record
     *            the record of the event.
     */
    public void setRecord(final Record record) {
        this.rec = record;
    }

    /**
     * get the date of an event.
     *
     * @return return the date of an event.
     */
    public LocalDateTime getDate() {
        return rec.getDateValue(rec.getTable().getDateColumn().getName());
    }
}
