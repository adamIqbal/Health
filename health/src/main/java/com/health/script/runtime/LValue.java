package com.health.script.runtime;

import java.util.Objects;

public class LValue {
    private Value value;
    private final ScriptType type;

    public LValue(final ScriptType type) {
        Objects.requireNonNull(type);

        this.type = type;
    }

    public LValue(final ScriptType type, final Value value) throws ScriptRuntimeException {
        Objects.requireNonNull(type);

        this.type = type;
        this.value = value;

        checkType(type, value);
    }

    public final ScriptType getType() {
        return this.type;
    }

    public final Value get() {
        return this.value;
    }

    public void set(final Value value) throws ScriptRuntimeException {
        checkType(type, value);

        this.value = value;
    }

    private static void checkType(final ScriptType type, final Value value) throws ScriptRuntimeException {
        if (value != null) {
            if (!type.isAssignableFrom(value.getType())) {
                throw new ScriptRuntimeException(String.format(
                        "Cannot convert '%s' to '%s'.", value.getType().getName(), type.getName()));
            }
        }
    }
}
