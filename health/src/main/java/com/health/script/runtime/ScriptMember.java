package com.health.script.runtime;

import java.util.Objects;

public abstract class ScriptMember {
    private final String name;

    public ScriptMember(final String name) {
        Objects.requireNonNull(name);

        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
