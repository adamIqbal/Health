package com.health.script.runtime;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a date value in the script.
 */
public final class DateValue extends Value {
    private static ScriptType type;
    private LocalDateTime value;

    static {
        ScriptTypeBuilder date = new ScriptTypeBuilder();
        date.setTypeName("date");
        date.defineConstructor((args) -> new NumberValue());
        date.defineMethod(new ScriptMethod("toString", (args) -> {
            return new StringValue(((DateValue) args[0]).getValue().toString());
        }));

        DateValue.type = date.buildType();
    }

    /**
     * Creates a new value with zero.
     */
    public DateValue() {
        this(LocalDateTime.of(1970, 1, 1, 0, 0));
    }

    /**
     * Creates a new value with the given value.
     *
     * @param value
     *            the value of the date.
     */
    public DateValue(final LocalDateTime value) {
        super(DateValue.type);

        this.value = value;
    }

    /**
     * Gets the value of this date.
     *
     * @return the value of this date.
     */
    public LocalDateTime getValue() {
        return this.value;
    }

    /**
     * Sets the value of this date.
     *
     * @param value
     *            the value of this date.
     */
    public void setValue(final LocalDateTime value) {
        this.value = value;
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link DateValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link DateValue}.
     */
    public static ScriptType getStaticType() {
        return DateValue.type;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
