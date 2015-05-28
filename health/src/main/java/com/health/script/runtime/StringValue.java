package com.health.script.runtime;

/**
 * Represents a string value in the script.
 */
public final class StringValue extends Value {
    private static ScriptType type;
    private String value;

    static {
        ScriptTypeBuilder string = new ScriptTypeBuilder();
        string.setTypeName("string");
        string.defineConstructor((args) -> new StringValue());
        string.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(((StringValue) args[0]).value);
                }));

        StringValue.type = string.buildType();
    }

    /**
     * Creates a new value with the default value (null).
     */
    public StringValue() {
        this(null);
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the string.
     */
    public StringValue(final String value) {
        super(StringValue.type);

        this.value = value;
    }

    /**
     * Gets the value of this string.
     *
     * @return the value of this string.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of this string.
     *
     * @param value
     *            the value of this string.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link StringValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link StringValue}.
     */
    public static ScriptType getStaticType() {
        return StringValue.type;
    }

    @Override
    public String toString() {
        return value;
    }
}
