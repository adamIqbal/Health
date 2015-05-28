package com.health.script.runtime;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.health.Utils;

public final class ScriptType {
    private final String name;
    private final Map<String, ScriptField> fields;
    private final Map<String, ScriptMethod> methods;
    private final Function<Value[], Value> constructor;

    public ScriptType(
            final String name,
            final Map<String, ScriptField> fields,
            final Map<String, ScriptMethod> methods) {
        this(name, fields, methods, null);
    }

    public ScriptType(
            final String name,
            final Map<String, ScriptField> fields,
            final Map<String, ScriptMethod> methods,
            final Function<Value[], Value> constructor) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(methods);

        this.name = name;
        this.fields = fields;
        this.methods = methods;

        if (constructor == null) {
            this.constructor = (args) -> new ComplexValue(this);
        } else {
            this.constructor = constructor;
        }
    }

    public Value makeInstance(Value[] args) {
        return this.constructor.apply(args);
    }

    public String getName() {
        return this.name;
    }

    public boolean hasMember(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.hasField(symbol) || this.hasMethod(symbol);
    }

    public boolean hasField(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.fields.containsKey(symbol);
    }

    public boolean hasMethod(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.methods.containsKey(symbol);
    }

    public ScriptMember getMember(final String symbol) {
        Objects.requireNonNull(symbol);

        return Utils.firstNonNull(
                () -> getField(symbol),
                () -> getMethod(symbol));
    }

    public ScriptField getField(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.fields.get(symbol);
    }

    public Collection<ScriptField> getFields() {
        return this.fields.values();
    }

    public ScriptMethod getMethod(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.methods.get(symbol);
    }

    public Collection<ScriptMethod> getMethods() {
        return this.methods.values();
    }

    public boolean isAssignableFrom(ScriptType type) {
        return this.getName().equals("object") || this == type;
    }
}
