package com.health.script.runtime;

/**
 * Represents a number value in the script.
 */
public final class NumberValue extends WrapperValue<Double> {
    /**
     * Creates a new value with the default value (0.0).
     */
    public NumberValue() {
        super(0.0);
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the number.
     */
    public NumberValue(final double value) {
        super(value);
    }
}
