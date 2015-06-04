package com.health.operations.functions;

import com.health.script.runtime.NumberValue;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

/**
 * A utility class, which consists of functions which can be used by the script for ==,=>,<= operations.
 *
 */
public final class ConstrainFunctions {

  /**
   * Function to determine whether the value gained through input is greater than value in the table
   * @param columns original value from the column
   * @param value  value which is gained through input
   * @return boolean if condition is met or exception
   * @throws Exception thrown if there are no valid arguments
   */
  public static boolean greater(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;
  return (double) columns< d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {

      throw new IllegalArgumentException("Can not invoke greater on String.");
    }

    // if (columns instanceof Date && value instanceof DateValue) {
    //
    // return true;
    // }
    // FIXME: add dateValue

    throw new IllegalArgumentException("Expected valuetype && constraintype to be valid.");

  }
  
  /**
   * Function to determine whether the value gained through input is greater than or equal to value in the table
   * @param columns original value from the column
   * @param value  value which is gained through input
   * @return boolean if condition is met or exception
   * @throws Exception thrown if there are no valid arguments
   */
  public static boolean greatereq(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;
  return (double) columns <= d.getValue();

    }

    if (columns instanceof String && value instanceof StringValue) {

      throw new IllegalArgumentException("Can not invoke greatereq on String.");
    }

    // if (columns instanceof Date && value instanceof DateValue) {
    //
    // return true;
    // }
    // FIXME: add dateValue

    throw new IllegalArgumentException("Expected valuetype && constraintype to be valid.");

  }
  /**
   * Function to determine whether the value gained through input is smaller than or equal to value in the table
   * @param columns original value from the column
   * @param value  value which is gained through input
   * @return boolean if condition is met or exception
   * @throws Exception thrown if there are no valid arguments
   */
  public static boolean smallereq(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;
  return (double) columns >= d.getValue();

    }

    if (columns instanceof String && value instanceof StringValue) {

      throw new IllegalArgumentException("Can not invoke smallereq on String.");
    }

    // if (columns instanceof Date && value instanceof DateValue) {
    //
    // return true;
    // }
    // FIXME: add dateValue

    throw new IllegalArgumentException("Expected valuetype && constraintype to be valid.");
  }
  /**
   * Function to determine whether the value gained through input is smaller than to value in the table
   * @param columns original value from the column
   * @param value  value which is gained through input
   * @return boolean if condition is met or exception
   * @throws Exception thrown if there are no valid arguments
   */
  public static boolean smaller(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {
      NumberValue d = (NumberValue) value;
      return (double) columns > d.getValue();
    }

    if (columns instanceof String && value instanceof StringValue) {

      throw new IllegalArgumentException("Can not invoke smaller on String.");
    }

    // if (columns instanceof Date && value instanceof DateValue) {
    //
    // return true;
    // }
    // FIXME: add dateValue

    throw new IllegalArgumentException("Expected valuetype && constraintype to be valid.");

  }
  /**
   * Function to determine whether the value gained through input is equal to value in the table
   * @param columns original value from the column
   * @param value  value which is gained through input
   * @return boolean if condition is met or exception
   * @throws Exception thrown if there are no valid arguments
   */
  public static boolean equal(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {
          NumberValue d = (NumberValue) value;
      return (double) columns == d.getValue();

    }

    if (columns instanceof String && value instanceof StringValue) {
       StringValue str  = (StringValue) value;
        return columns.toString().equals(str.getValue());
    }

    // if (columns instanceof Date && value instanceof DateValue) {
    //
    // return true;
    // }
    // FIXME: add dateValue

    throw new IllegalArgumentException("Expected valuetype && constraintype to be valid.");

  }


}
