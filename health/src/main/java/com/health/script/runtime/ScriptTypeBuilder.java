package com.health.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class ScriptTypeBuilder {
    private boolean built = false;
    private String typeName;
    private Function<Value[], Value> contructor;
    private final Map<String, ScriptField> fields;
    private final Map<String, ScriptMethod> methods;

    public ScriptTypeBuilder() {
        this.fields = new HashMap<String, ScriptField>();
        this.methods = new HashMap<String, ScriptMethod>();
    }

    public void setTypeName(final String name) {
        Objects.requireNonNull(name);

        this.typeName = name;
    }

    public void defineField(final ScriptField field) {
        Objects.requireNonNull(field);

        fields.put(field.getName(), field);
    }

    public void defineMethod(final ScriptMethod method) {
        Objects.requireNonNull(method);

        methods.put(method.getName(), method);
    }

    public void defineConstructor(final Function<Value[], Value> constructor) {
        Objects.requireNonNull(constructor);

        this.contructor = constructor;
    }

    public ScriptType buildType() {
        if (this.built) {
            throw new IllegalStateException("The type has already been built.");
        } else {
            this.built = true;
        }

        return new ScriptType(this.typeName, this.fields, this.methods, this.contructor);
    }
}
