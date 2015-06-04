package com.health.script.runtime;

import java.util.Date;

/**
 * Represents a date value in the script.
 */
public final class DateValue extends Value {
  private static ScriptType type;
  private Date value;

  static {
    ScriptTypeBuilder number = new ScriptTypeBuilder();
    number.setTypeName("number");
    number.defineConstructor((args) -> new NumberValue());
    number.defineMethod(new ScriptMethod("toString", (args) -> {
      return new StringValue(((DateValue) args[0]).getValue().toString());
    }));

    DateValue.type = number.buildType();
  }

  /**
   * Creates a new value with the time it is allocated.
   */
  public DateValue() {
    this(new Date());
  }

  /**
   * Creates a new value with the given value.
   *
   * @param value
   *          the value of the date.
   */
  public DateValue(final Date value) {
    super(DateValue.type);

    this.value = value;
  }

  /**
   * Gets the value of this date.
   *
   * @return the value of this date.
   */
  public Date getValue() {
    return this.value;
  }

  /**
   * Sets the value of this date.
   *
   * @param value
   *          the value of this date.
   */
  public void setValue(final Date value) {
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
