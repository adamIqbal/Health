package com.health.operations.functions;

import com.health.script.runtime.NumberValue;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

public final class ConstrainFunctions {

  public static boolean greater(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {

      return true;

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
  
  public static boolean greatereq(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {

      return true;

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

  public static boolean smallereq(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {

      return true;

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

  public static boolean smaller(Object columns, Value value) throws Exception {
    if (value instanceof NumberValue && columns instanceof Double) {

      return true;

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
