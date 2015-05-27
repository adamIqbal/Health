package com.health.script.runtime;

public final class StringValue extends Value {
    private static ScriptType type;
    private String value;

    static {
        ScriptTypeBuilder string = new ScriptTypeBuilder();
        string.setTypeName("string");

        StringValue.type = string.buildType();
    }

    public StringValue() {
        this(null);
    }

    public StringValue(final String value) {
        super(StringValue.type);

        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ScriptType getStaticType() {
        return StringValue.type;
    }

    public String toString() {
        return value;
    }
}
