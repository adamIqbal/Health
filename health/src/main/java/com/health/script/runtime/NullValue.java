package com.health.script.runtime;

/**
 * Represents a null value in the script.
 */
public final class NullValue extends Value {
    /**
     * Creates a new null value.
     */
    public NullValue() {
        super(Value.getStaticType());
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link NullValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link NullValue}.
     */
    public static ScriptType getStaticType() {
        return Value.getStaticType();
    }

    @Override
    public String toString() {
        return "<null>";
    }
}
