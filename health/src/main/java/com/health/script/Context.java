package com.health.script;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.ScriptType;
import com.health.script.runtime.Value;

public final class Context {
    private Map<String, ScriptType> types;
    private Map<String, LValue> variables;

    public Context() {
        this.types = new HashMap<String, ScriptType>();
        this.variables = new HashMap<String, LValue>();
    }

    public void declareLocal(final String symbol, final ScriptType type) throws ScriptRuntimeException {
        declareLocal(symbol, type, type.makeInstance());
    }

    public void declareLocal(final String symbol, final ScriptType type, final Value value)
            throws ScriptRuntimeException {
        Objects.requireNonNull(symbol);

        if (this.variables.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "A local variable named '%s' is already defined in this scope.", symbol));
        }

        this.variables.put(symbol, new LValue(type, value));
    }

    public void declareType(final ScriptType type) throws ScriptRuntimeException {
        declareType(type.getName(), type);
    }

    public void declareType(final String symbol, final ScriptType type) throws ScriptRuntimeException {
        Objects.requireNonNull(type);

        if (this.types.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The current context already contains a definition for '%s'.", symbol));
        }

        this.types.put(symbol, type);
    }

    public boolean isDefined(final String symbol) {
        return isLocalDefined(symbol) || isTypeDefined(symbol);
    }

    public boolean isLocalDefined(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.variables.containsKey(symbol);
    }

    public boolean isTypeDefined(final String symbol) {
        Objects.requireNonNull(symbol);

        return this.types.containsKey(symbol);
    }

    public LValue lookup(final String symbol) throws ScriptRuntimeException {
        Objects.requireNonNull(symbol);

        if (!this.variables.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The name '%s' does not exist in the current context.", symbol));
        }

        return this.variables.get(symbol);
    }

    public ScriptType lookupType(final String symbol) throws ScriptRuntimeException {
        Objects.requireNonNull(symbol);

        if (!this.types.containsKey(symbol)) {
            throw new ScriptRuntimeException(String.format(
                    "The type '%s' does not exist in the current context.", symbol));
        }

        return this.types.get(symbol);
    }

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
