package com.health.script.runtime;

public final class NullValue extends Value {
    private double value;

    public NullValue() {
        super(Value.getStaticType());
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static ScriptType getStaticType() {
        return Value.getStaticType();
    }

    public String toString() {
        return "<null>";
    }
}
