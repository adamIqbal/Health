package com.health.script.runtime;

public final class NonModifiableLValue extends LValue {
    public NonModifiableLValue(final ScriptType type) {
        super(type);
    }

    public NonModifiableLValue(final ScriptType type, final Value value) throws ScriptRuntimeException {
        super(type, value);
    }

    @Override
    public void set(final Value value) throws ScriptRuntimeException {
        throw new ScriptRuntimeException("Cannot assign to a nonmodifiable lvalue.");
    }
}
