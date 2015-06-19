package com.health.script.runtime;

import java.util.Objects;

/**
 * Represents the 'left hand value' (l-value) of an expression, i.e. a value
 * that can be assigned to.
 */
public class LValue {
    private Value value;
    private final ScriptType type;

    /**
     * Creates a new l-value of the given type.
     *
     * @param type
     *            the type of the l-value.
     */
    public LValue(final ScriptType type) {
        this(type, null);
    }

    /**
     * Creates a new l-value of the given type and value.
     *
     * @param type
     *            the type of the l-value.
     * @param value
     *            the value of the l-value.
     * @throws ScriptRuntimeException
     *             if the given type is not assignable from the given value.
     */
    public LValue(final ScriptType type, final Value value) throws ScriptRuntimeException {
        Objects.requireNonNull(type);

        this.type = type;
        this.value = value;

        checkType(type, value);
    }

    /**
     * Gets the type of this l-value.
     *
     * @return the type of this l-value.
     */
    public final ScriptType getType() {
        return this.type;
    }

    /**
     * Gets the value of this l-value.
     *
     * @return the value of this l-value.
     */
    public final Value get() {
        return this.value;
    }

    /**
     * Sets the value of this l-value.
     *
     * @param value
     *            the value of this l-value.
     * @throws ScriptRuntimeException
     *             if this l-value's type is not assignable from the given
     *             value.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void set(final Value value) throws ScriptRuntimeException {
        checkType(type, value);

        this.value = value;
    }

    /**
     * Checks whether the given type is assignable from the given value.
     *
     * @param type
     *            the type.
     * @param value
     *            the value.
     * @throws ScriptRuntimeException
     *             if the given type is not assignable from the given value.
     */
    private static void checkType(final ScriptType type, final Value value) throws ScriptRuntimeException {
        if (value != null && !type.isAssignableFrom(value.getType())) {
            throw new ScriptRuntimeException(String.format(
                    "Cannot convert '%s' to '%s'.", value.getType().getName(), type.getName()));
        }
    }
}
