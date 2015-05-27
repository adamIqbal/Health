package com.health.script.runtime;

import java.util.Objects;
import java.util.function.Function;

public final class ScriptMethod extends ScriptMember {
    private final Function<Value[], Value> function;

    public ScriptMethod(final String name, final Function<Value[], Value> function) {
        super(name);

        Objects.requireNonNull(function);

        this.function = function;
    }

    public ScriptDelegate createDelegate() {
        return new ScriptDelegate(this);
    }

    public Function<Value[], Value> getFunction() {
        return this.function;
    }
}
