package com.health.script.runtime;

/**
 * Represents a boolean value in the script.
 */
public final class BooleanValue extends WrapperValue<Boolean> {
    private static ScriptType type;

    /**
     * Creates a new value with the default value (false).
     */
    public BooleanValue() {
        super(false);
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the boolean.
     */
    public BooleanValue(final boolean value) {
        super(value);
    }
}
