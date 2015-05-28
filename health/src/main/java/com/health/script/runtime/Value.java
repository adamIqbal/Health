package com.health.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.health.Utils;

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

    public Value() {
        this(objectType);
    }

    public Value(ScriptType type) {
        Objects.requireNonNull(type);

        this.type = type;
        this.fields = new HashMap<String, LValue>();

        for (ScriptField field : type.getFields()) {
            this.fields.put(field.getName(), new LValue(type));
        }
    }

    public final ScriptType getType() {
        return this.type;
    }

    public final LValue getMember(final String symbol) throws ScriptRuntimeException {
        if (!this.type.hasMember(symbol)) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a member named '%s'.",
                    this.getType().getName(), symbol));
        }

        return Utils.firstNonNull(
                () -> getMethod(symbol),
                () -> getField(symbol));
    }

    public final LValue getMethod(final String symbol) throws ScriptRuntimeException {
        ScriptMethod method = this.getType().getMethod(symbol);

        if (method == null) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a method named '%s'.",
                    this.getType().getName(), symbol));
        }

        ScriptDelegate delegate = method.createDelegate(this);

        return new NonModifiableLValue(delegate.getType(), delegate);
    }

    public final LValue getField(String symbol) throws ScriptRuntimeException {
        if (!this.getType().hasField(symbol)) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a field named '%s'.",
                    this.getType().getName(), symbol));
        }

        return this.fields.get(symbol);
    }

    public final void setField(String symbol, Value value) throws ScriptRuntimeException {
        ScriptField field = this.getType().getField(symbol);

        if (field == null) {
            throw new ScriptRuntimeException(String.format("Type '%s' does not define a field named '%s'.",
                    this.getType().getName(), symbol));
        }

        if (value != null && value.getType() != field.getType()) {
            throw new ScriptRuntimeException(String.format(
                    "Field '%s' has type '%s' but tried to set value of type '%s'.",
                    field.getName(), field.getType().getName(), value.getType().getName()));
        }

        this.fields.get(symbol).set(value);
    }

    public static ScriptType getStaticType() {
        return Value.objectType;
    }
}
