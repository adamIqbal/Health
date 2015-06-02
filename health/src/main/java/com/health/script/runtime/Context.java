package com.health.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Represents the runtime environment of the script.
 *
 * @author Martijn
 */
public final class Context {
    private Map<String, ScriptType> types;
    private Map<String, LValue> variables;

    /**
     * Creates a new, empty, context.
     */
    public Context() {
        this.types = new HashMap<String, ScriptType>();
        this.variables = new HashMap<String, LValue>();

        // Declare all the standard type
        this.declareType(Value.getStaticType());
        this.declareType(BooleanValue.getStaticType());
        this.declareType(NumberValue.getStaticType());
        this.declareType(StringValue.getStaticType());
        this.declareType(TableValue.getStaticType());
    }

    /**
     * Declares a new local variable of the given type.
     *
     * @param symbol
     *            the name of the local variable.
     * @param type
     *            the type of the local variable.
     * @throws ScriptRuntimeException
     *             if a local variable with the given name is already declared
     *             or if the type and value or not compatible.
     */
    public void declareLocal(final String symbol, final ScriptType type) throws ScriptRuntimeException {
        declareLocal(symbol, type, type.makeInstance(new Value[0]));
    }

    /**
     * Declares a new local variable of the given type.
     *
     * @param symbol
     *            the name of the local variable.
     * @param type
     *            the type of the local variable.
     * @param value
     *            the value of the local variable.
     * @throws ScriptRuntimeException
     *             if a local variable with the given name is already declared
     *             or if the type and value or not compatible.
     */
    public void declareLocal(final String symbol, final ScriptType type, final Value value)
            throws ScriptRuntimeException {
        Objects.requireNonNull(symbol);

        if (this.variables.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "A local variable named '%s' is already defined in this scope.", symbol));
        }

        this.variables.put(symbol, new LValue(type, value));
    }

    /**
     * Declares a given type.
     *
     * @param type
     *            the type to declare.
     * @throws ScriptRuntimeException
     *             if a type with the given name is already declared.
     */
    public void declareType(final ScriptType type) throws ScriptRuntimeException {
        Objects.requireNonNull(type);

        String symbol = type.getName();

        if (this.types.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The current context already contains a definition for '%s'.", symbol));
        }

        this.types.put(symbol, type);
    }

    /**
     * Declares a given type as a (fake) static type.
     *
     * @param type
     *            the type to declare.
     * @throws ScriptRuntimeException
     *             if a type with the given name is already declared.
     */
    public void declareStaticType(final ScriptType type) throws ScriptRuntimeException {
        Objects.requireNonNull(type);

        String symbol = type.getName();

        if (this.variables.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The current context already contains a definition for '%s'.", symbol));
        }

        this.variables.put(symbol, new NonModifiableLValue(type, type.makeInstance(new Value[0])));
    }

    /**
     * Returns whether a given symbol is defined.
     *
     * @param symbol
     *            the symbol to check for whether it is defined.
     * @return true if the given symbol is defined; otherwise false.
     */
    public boolean isDefined(final String symbol) {
        return isLocalDefined(symbol) || isTypeDefined(symbol);
    }

    /**
     * Returns whether a given local variable is defined.
     *
     * @param symbol
     *            the symbol to check for whether it is defined.
     * @return true if the given symbol is a defined local variable; otherwise
     *         false.
     */
    public boolean isLocalDefined(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.variables.containsKey(symbol);
    }

    /**
     * Returns whether a given type is defined.
     *
     * @param symbol
     *            the symbol to check for whether it is defined.
     * @return true if the given symbol is a defined type; otherwise false.
     */
    public boolean isTypeDefined(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.types.containsKey(symbol);
    }

    /**
     * Retrieves the l-value for a given symbol.
     *
     * @param symbol
     *            the symbol to retrieve the l-value for.
     * @return the l-value for a given symbol.
     * @throws ScriptRuntimeException
     *             if a local variable with the given name is not declared.
     */
    public LValue lookup(final String symbol) throws ScriptRuntimeException {
        Objects.requireNonNull(symbol);

        if (!this.variables.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The name '%s' does not exist in the current context.", symbol));
        }

        return this.variables.get(symbol);
    }

    /**
     * Retrieves the type for a given symbol.
     *
     * @param symbol
     *            the symbol to retrieve the type for.
     * @return the type for a given symbol.
     * @throws ScriptRuntimeException
     *             if a type with the given name is not declared.
     */
    public ScriptType lookupType(final String symbol) throws ScriptRuntimeException {
        Objects.requireNonNull(symbol);

        if (!this.types.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The type '%s' does not exist in the current context.", symbol));
        }

        return this.types.get(symbol);
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return a string that represents the current object.
     */
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (Entry<String, LValue> entry : this.variables.entrySet()) {
            string.append(entry.getKey());
            string.append(": ");
            if (entry.getValue() != null && entry.getValue().get() != null) {
                string.append(entry.getValue().get().toString());
            } else {
                string.append("null");
            }
            string.append("\r\n");
        }

        return string.toString();
    }
}