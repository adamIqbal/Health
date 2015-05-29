package com.health.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a builder for a {@link ScriptType}.
 */
public final class ScriptTypeBuilder {
    private boolean built = false;
    private String typeName;
    private ScriptFunction<Value[], Value> contructor;
    private final Map<String, ScriptField> fields;
    private final Map<String, ScriptMethod> methods;

    /**
     * Creates a new {@link ScriptTypeBuilder}.
     */
    public ScriptTypeBuilder() {
        this.fields = new HashMap<String, ScriptField>();
        this.methods = new HashMap<String, ScriptMethod>();
    }

    /**
     * Sets the name of the type.
     *
     * @param name
     *            the name of the type.
     */
    public void setTypeName(final String name) {
        Objects.requireNonNull(name);

        this.typeName = name;
    }

    /**
     * Defines the given field for the type.
     *
     * @param field
     *            the field to define.
     */
    public void defineField(final ScriptField field) {
        Objects.requireNonNull(field);

        fields.put(field.getName(), field);
    }

    /**
     * Defines the given method for the type.
     *
     * @param method
     *            the method to define.
     */
    public void defineMethod(final ScriptMethod method) {
        Objects.requireNonNull(method);

        methods.put(method.getName(), method);
    }

    /**
     * Defines the given constructor for the type.
     *
     * @param constructor
     *            the constructor to define.
     */
    public void defineConstructor(final ScriptFunction<Value[], Value> constructor) {
        Objects.requireNonNull(constructor);

        this.contructor = constructor;
    }

    /**
     * Creates the type.
     *
     * @return the type.
     */
    public ScriptType buildType() {
        if (this.built) {
            throw new IllegalStateException("The type has already been built.");
        } else {
            this.built = true;
        }

        return new ScriptType(this.typeName, this.fields, this.methods, this.contructor);
    }
}
