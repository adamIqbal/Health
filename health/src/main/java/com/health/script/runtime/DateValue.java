package com.health.script.runtime;

import java.time.LocalDateTime;

/**
 * Represents a date value in the script.
 */
public final class DateValue extends WrapperValue<LocalDateTime> {
    /**
     * Creates a new value with zero.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public DateValue() {
        super(LocalDateTime.of(1970, 1, 1, 0, 0));
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the date.
     */
    public DateValue(final LocalDateTime value) {
        super(value);
    }
}
