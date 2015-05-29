package com.health.script.runtime;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.health.Utils;

/**
 * Represents the type of a script object.
 */
public final class ScriptType {
    private final String name;
    private final Map<String, ScriptField> fields;
    private final Map<String, ScriptMethod> methods;
    private final ScriptFunction<Value[], Value> constructor;

    /**
     * Creates a new type with the given name, fields and methods.
     *
     * @param name
     *            the name of the type.
     * @param fields
     *            the fields of the type.
     * @param methods
     *            the methods of the type.
     */
    public ScriptType(
            final String name,
            final Map<String, ScriptField> fields,
            final Map<String, ScriptMethod> methods) {
        this(name, fields, methods, null);
    }

    /**
     * Creates a new type with the given name, fields, methods and constructor.
     *
     * @param name
     *            the name of the type.
     * @param fields
     *            the fields of the type.
     * @param methods
     *            the methods of the type.
     * @param constructor
     *            the constructor of the type.
     */
    public ScriptType(
            final String name,
            final Map<String, ScriptField> fields,
            final Map<String, ScriptMethod> methods,
            final ScriptFunction<Value[], Value> constructor) {
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

    /**
     * Creates a new instance of this type by calling the constructor with the
     * given arguments.
     *
     * @param args
     *            the arguments for the constructor.
     * @return a new instance of this type.
     */
    public Value makeInstance(final Value[] args) {
        return this.constructor.apply(args);
    }

    /**
     * Gets the name of this type.
     *
     * @return the name of this type.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns whether this type has a member with the given name.
     *
     * @param symbol
     *            the name to check for.
     * @return true if this type has a member with the given name; otherwise
     *         false.
     */
    public boolean hasMember(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.hasField(symbol) || this.hasMethod(symbol);
    }

    /**
     * Returns whether this type has a field with the given name.
     *
     * @param symbol
     *            the name to check for.
     * @return true if this type has a field with the given name; otherwise
     *         false.
     */
    public boolean hasField(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.fields.containsKey(symbol);
    }

    /**
     * Returns whether this type has a method with the given name.
     *
     * @param symbol
     *            the name to check for.
     * @return true if this type has a method with the given name; otherwise
     *         false.
     */
    public boolean hasMethod(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.methods.containsKey(symbol);
    }

    /**
     * Gets the member with the given name.
     *
     * @param symbol
     *            the name of the member to get.
     * @return the member with the given name, or null if a member with the
     *         given name does not exist.
     */
    public ScriptMember getMember(final String symbol) {
        Objects.requireNonNull(symbol);

        return Utils.firstNonNull(
                () -> getField(symbol),
                () -> getMethod(symbol));
    }

    /**
     * Gets the field with the given name.
     *
     * @param symbol
     *            the name of the field to get.
     * @return the field with the given name, or null if a field with the given
     *         name does not exist.
     */
    public ScriptField getField(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.fields.get(symbol);
    }

    /**
     * Gets the fields of this type.
     *
     * @return the fields of this type.
     */
    public Collection<ScriptField> getFields() {
        return this.fields.values();
    }

    /**
     * Gets the method with the given name.
     *
     * @param symbol
     *            the name of the method to get.
     * @return the method with the given name, or null if a method with the
     *         given name does not exist.
     */
    public ScriptMethod getMethod(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.methods.get(symbol);
    }

    /**
     * Gets the methods of this type.
     *
     * @return the methods of this type.
     */
    public Collection<ScriptMethod> getMethods() {
        return this.methods.values();
    }

    /**
     * Determines whether a instance of the given type can be assigned to the
     * current type.
     *
     * @param type
     *            the type to compare with the current type.
     * @return true if a instance of the given type can be assigned to the
     *         current type; otherwise false.
     */
    public boolean isAssignableFrom(final ScriptType type) {
        return this.getName().equals("object") || this == type;
    }
}
