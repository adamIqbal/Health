package com.health.script.runtime;

import java.util.Objects;

public final class LValue {
    private Value value;
    private final ScriptType type;

    public LValue(final ScriptType type) {
        Objects.requireNonNull(type);

        this.type = type;
    }

    public LValue(final ScriptType type, final Value value) throws ScriptRuntimeException {
        Objects.requireNonNull(type);

        this.type = type;
        this.set(value);
    }

    public ScriptType getType() {
        return this.type;
    }

    public Value get() {
        return this.value;
    }

    public void set(final Value value) throws ScriptRuntimeException {
        if (value != null) {
            if (!this.type.isAssignableFrom(value.getType())) {
                throw new ScriptRuntimeException(String.format(
                        "Cannot convert '%s' to '%s'.", type.getName(), value.getType().getName()));
            }
        }

        this.value = value;
    }
}
