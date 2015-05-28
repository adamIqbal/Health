package com.health.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a value in the script.
 */
public class Value {
    private static ScriptType objectType;
    private final ScriptType type;
    private final Map<String, LValue> fields;

    static {
        ScriptTypeBuilder object = new ScriptTypeBuilder();
        object.setTypeName("object");
        object.defineConstructor((args) -> new Value());
        object.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(args[0].toString());
                }));

        Value.objectType = object.buildType();
    }

    /**
     * Creates a new value with type 'object'.
     */
    public Value() {
        this(objectType);
    }

    /**
     * Creates a new value with the given type.
     *
     * @param type
     *            the type of the value.
     */
    public Value(final ScriptType type) {
        Objects.requireNonNull(type);

        this.type = type;
        this.fields = new HashMap<String, LValue>();

        // Create an l-value for every field so that it can be assigned to
        for (ScriptField field : type.getFields()) {
            this.fields.put(field.getName(), new LValue(type));
        }
    }

    /**
     * Gets the type of this value.
     *
     * @return the type of this value.
     */
    public final ScriptType getType() {
        return this.type;
    }

    /**
     * Gets the l-value of the member with the given name.
     *
     * @param symbol
     *            the name of the member to retrieve.
     * @return the l-value of the member with the given name.
     * @throws ScriptRuntimeException
     *             if a member with the given name is not defined.
     */
    public final LValue getMember(final String symbol) throws ScriptRuntimeException {
        if (this.getType().hasField(symbol)) {
            return this.getField(symbol);
        } else if (this.getType().hasMethod(symbol)) {
            return this.getMethod(symbol);
        } else {
            // If this object's type does not contain a member with the given
            // name, throw an exception
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a member named '%s'.",
                    this.getType().getName(), symbol));
        }
    }

    /**
     * Gets the l-value of the method with the given name.
     *
     * @param symbol
     *            the name of the method to retrieve.
     * @return the l-value of the method with the given name.
     * @throws ScriptRuntimeException
     *             if a method with the given name is not defined.
     */
    public final LValue getMethod(final String symbol) throws ScriptRuntimeException {
        ScriptMethod method = this.getType().getMethod(symbol);

        // If this object's type does not contain a method with the given name,
        // throw an exception
        if (method == null) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a method named '%s'.",
                    this.getType().getName(), symbol));
        }

        // Create a delegate to the method with a closure containing this object
        ScriptDelegate delegate = method.createDelegate(this);

        // Wrap it in a non-modifiable l-value
        return new NonModifiableLValue(delegate.getType(), delegate);
    }

    /**
     * Gets the l-value of the field with the given name.
     *
     * @param symbol
     *            the name of the field to retrieve.
     * @return the l-value of the field with the given name.
     * @throws ScriptRuntimeException
     *             if a field with the given name is not defined.
     */
    public final LValue getField(final String symbol) throws ScriptRuntimeException {
        // If this object's type does not contain a field with the given name,
        // throw an exception
        if (!this.getType().hasField(symbol)) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a field named '%s'.",
                    this.getType().getName(), symbol));
        }

        return this.fields.get(symbol);
    }

    /**
     * Sets the value of the field with the given name.
     *
     * @param symbol
     *            the name of the field to retrieve.
     * @param value
     *            the value to set.
     * @throws ScriptRuntimeException
     *             if a field with the given name is not defined.
     */
    public final void setField(final String symbol, final Value value) throws ScriptRuntimeException {
        // If this object's type does not contain a field with the given name,
        // throw an exception
        if (!this.getType().hasField(symbol)) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a field named '%s'.",
                    this.getType().getName(), symbol));
        }

        this.fields.get(symbol).set(value);
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link Value}.
     *
     * @return the {@link ScriptType} corresponding to {@link Value}.
     */
    public static ScriptType getStaticType() {
        return Value.objectType;
    }
}
