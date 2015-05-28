package com.health.script.runtime;

import java.util.Objects;
import java.util.function.Function;

public final class ScriptMethod extends ScriptMember {
    private final Function<Value[], Value> function;
    private final boolean isStatic;

    public ScriptMethod(final String name, final Function<Value[], Value> function) {
        this(name, function, false);
    }

    public ScriptMethod(final String name, final Function<Value[], Value> function, boolean isStatic) {
        super(name);

        Objects.requireNonNull(function);

        this.function = function;
        this.isStatic = isStatic;
    }

    public ScriptDelegate createDelegate(final Value value) {
        return new ScriptDelegate(this, value);
    }

    public Function<Value[], Value> getFunction() {
        return this.function;
    }

    public boolean isStatic() {
        return this.isStatic;
    }
}
