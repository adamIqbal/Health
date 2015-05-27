package com.health.script.runtime;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.health.Utils;

public final class ScriptType {
    private final String name;
    private final Map<String, ScriptField> fields;
    private final Map<String, ScriptMethod> methods;

    public ScriptType(
            final String name,
            final Map<String, ScriptField> fields,
            final Map<String, ScriptMethod> methods) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(methods);

        this.name = name;
        this.fields = fields;
        this.methods = methods;
    }

    public Value makeInstance() {
        switch (this.name) {
        case "object":
            return new Value();
        case "bool":
            return new BooleanValue();
        case "number":
            return new NumberValue();
        case "string":
            return new StringValue();
        default:
            return new ComplexValue(this);
        }
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
        return type.getName().equals("object") || this == type;
    }
}
