package com.health.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ScriptTypeBuilder {
    private boolean built = false;
    private String typeName;
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

    public ScriptType buildType() {
        if (this.built) {
            throw new IllegalStateException();
        }

        return new ScriptType(this.typeName, this.fields, this.methods);
    }
}
