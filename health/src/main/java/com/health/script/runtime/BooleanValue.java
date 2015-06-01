package com.health.script.runtime;

/**
 * Represents a boolean value in the script.
 */
public final class BooleanValue extends Value {
    private static ScriptType type;
    private boolean value;

    static {
        ScriptTypeBuilder bool = new ScriptTypeBuilder();
        bool.setTypeName("bool");
        bool.defineConstructor((args) -> new BooleanValue());
        bool.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(Boolean.toString(((BooleanValue) args[0]).getValue()));
                }));

        BooleanValue.type = bool.buildType();
    }

    /**
     * Creates a new value with the default value (false).
     */
    public BooleanValue() {
        this(false);
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the boolean.
     */
    public BooleanValue(final boolean value) {
        super(BooleanValue.type);

        this.value = value;
    }

    /**
     * Gets the value of this boolean.
     *
     * @return the value of this boolean.
     */
    public boolean getValue() {
        return this.value;
    }

    /**
     * Sets the value of this boolean.
     *
     * @param value
     *            the value of this boolean.
     */
    public void setValue(final boolean value) {
        this.value = value;
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link BooleanValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link BooleanValue}.
     */
    public static ScriptType getStaticType() {
        return BooleanValue.type;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
