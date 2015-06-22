package com.health.script.runtime;

/**
 * Represents a string value in the script.
 */
public final class StringValue extends WrapperValue<String> {
    /**
     * Creates a new value with the default value (null).
     */
    public StringValue() {
        super(null);
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the string.
     */
    public StringValue(final String value) {
        super(value);
    }
}
