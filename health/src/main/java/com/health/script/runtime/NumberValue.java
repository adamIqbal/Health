package com.health.script.runtime;

/**
 * Represents a number value in the script.
 */
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

    /**
     * Creates a new value with the default value (0.0).
     */
    public NumberValue() {
        this(0.0);
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the number.
     */
    public NumberValue(final double value) {
        super(NumberValue.type);

        this.value = value;
    }

    /**
     * Gets the value of this number.
     *
     * @return the value of this number.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Sets the value of this number.
     *
     * @param value
     *            the value of this number.
     */
    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link NumberValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link NumberValue}.
     */
    public static ScriptType getStaticType() {
        return NumberValue.type;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
