package com.health.script.runtime;

import java.util.Objects;

public final class ScriptField extends ScriptMember {
    private final ScriptType type;

    public ScriptField(final String name, final ScriptType type) {
        super(name);

        Objects.requireNonNull(type);

        this.type = type;
    }

    public ScriptType getType() {
        return this.type;
    }
}
