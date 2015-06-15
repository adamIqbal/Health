package com.health.operations.functions;

import java.time.LocalDate;

import com.health.script.runtime.DateValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

/**
 * A utility class, which consists of functions which can be used by the script for greater than,
 * greater/eq,smaller than, smaller/eq smaller operations.
 *
 */
public final class ConstrainFunctions {
  private ConstrainFunctions() {
  }

  /**
   * Function to determine whether the value gained through table is greater than value from input.
   *
   * @param columns
   *          original value from the column
   * @param value
   *          value which is gained through input
   * @return boolean if condition is met or exception
   * @throws IllegalArgumentException
   *           thrown if there are no valid arguments
   */
  public static boolean greater(final Object columns, final Value value) {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;

      return (double) columns > d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {
      throw new IllegalArgumentException("Can not invoke greater on String.");
    }

    if (columns instanceof LocalDate && value instanceof DateValue) {

      return ((LocalDate) columns).isAfter(((DateValue) value).getValue());

    }
    return false;
    // throw new
    // IllegalArgumentException("Expected valuetype && constraintype to be valid.");
  }

  /**
   * Function to determine whether the value gained through table is greater than or equal to value
   * gained through input.
   *
   * @param columns
   *          original value from the column
   * @param value
   *          value which is gained through input
   * @return boolean if condition is met or exception
   * @throws IllegalArgumentException
   *           thrown if there are no valid arguments
   */
  public static boolean greaterEq(final Object columns, final Value value) {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;

      return (double) columns >= d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {
      throw new IllegalArgumentException("Can not invoke greatereq on String.");
    }

    if (columns instanceof LocalDate && value instanceof DateValue) {

      return greater(columns, value) || equal(columns, value);

    }
    return false;
    // throw new
    // IllegalArgumentException("Expected valuetype && constraintype to be valid.");
  }

  /**
   * Function to determine whether the value gained through table is smaller than or equal to value
   * value gained through input.
   *
   * @param columns
   *          original value from the column
   * @param value
   *          value which is gained through input
   * @return boolean if condition is met or exception
   * @throws IllegalArgumentException
   *           thrown if there are no valid arguments
   */
  public static boolean smallerEq(final Object columns, final Value value) {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;

      return (double) columns <= d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {
      throw new IllegalArgumentException("Can not invoke smallereq on String.");
    }

    if (columns instanceof LocalDate && value instanceof DateValue) {

      return smaller(columns, value) || equal(columns, value);

    }
    return false;
    // throw new
    // IllegalArgumentException("Expected valuetype && constraintype to be valid.");
  }

  /**
   * Function to determine whether the value gained through input is smaller than to value in the
   * table.
   *
   * @param columns
   *          original value from the column
   * @param value
   *          value which is gained through input
   * @return boolean if condition is met or exception
   * @throws IllegalArgumentException
   *           thrown if there are no valid arguments
   */
  public static boolean smaller(final Object columns, final Value value) {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;

      return (double) columns < d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {
      throw new IllegalArgumentException("Can not invoke smaller on String.");
    }

    if (columns instanceof LocalDate && value instanceof DateValue) {

      return ((LocalDate) columns).isBefore(((DateValue) value).getValue());

    }
    return false;
    // throw new
    // IllegalArgumentException("Expected valuetype && constraintype to be valid.");
  }

  /**
   * Function to determine whether the value gained through input is equal to value in the table.
   *
   * @param columns
   *          original value from the column
   * @param value
   *          value which is gained through input
   * @return boolean if condition is met or exception
   * @throws IllegalArgumentException
   *           thrown if there are no valid arguments
   */
  public static boolean equal(final Object columns, final Value value) {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;

      return (double) columns == d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {
      StringValue str = (StringValue) value;

      return columns.toString().equals(str.getValue());
    }

    if (columns instanceof LocalDate && value instanceof DateValue) {

      return ((LocalDate) columns).isEqual(((DateValue) value).getValue());

    }
    return false;
    // throw new
    // IllegalArgumentException("Expected valuetype && constraintype to be valid.");
  }
}
