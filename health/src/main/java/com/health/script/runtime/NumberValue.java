package com.health.script.runtime;

public final class NumberValue extends Value {
    private static ScriptType type;
    private double value;

    static {
        ScriptTypeBuilder number = new ScriptTypeBuilder();
        number.setTypeName("number");
        number.defineConstructor((args) -> new NumberValue());
        number.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(Double.toString(((NumberValue) args[0]).getValue()));
                }));

        NumberValue.type = number.buildType();
    }

    public NumberValue() {
        this(0.0);
    }

    public NumberValue(final double value) {
        super(NumberValue.type);

        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static ScriptType getStaticType() {
        return NumberValue.type;
    }

    public String toString() {
        return Double.toString(value);
    }
}
