package com.health.script.runtime;

/**
 * Represents the unmodifiable 'left hand value' (l-value) of an expression.
 */
public final class NonModifiableLValue extends LValue {
    /**
     * Creates a new l-value of the given type.
     *
     * @param type
     *            the type of the l-value.
     */
    public NonModifiableLValue(final ScriptType type) {
        super(type);
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
    public NonModifiableLValue(final ScriptType type, final Value value) throws ScriptRuntimeException {
        super(type, value);
    }

    /**
     * A non-modifiable l-value cannot be modified, hence a
     * {@link ScriptRuntimeException} is always thrown when this method is
     * called.
     */
    @Override
    public void set(final Value value) throws ScriptRuntimeException {
        throw new ScriptRuntimeException("Cannot assign to a nonmodifiable lvalue.");
    }
}
