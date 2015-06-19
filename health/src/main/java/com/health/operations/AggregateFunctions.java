package com.health.operations;

import java.util.List;

import com.health.Record;

/**
 * A class which provides aggregate functions.
 *
 * @author daan
 */
public final class AggregateFunctions {
  private AggregateFunctions() {
  }

  private static Object[] getObjectValues(final List<Record> records, final String column) {
    Object[] values = new Object[records.size()];

    for (int i = 0; i < records.size(); i++) {
      values[i] = records.get(i).getValue(column);
    }

    return values;
  }

  private static double[] getDoubleValues(final List<Record> records, final String column) {
    double[] values = new double[records.size()];

    for (int i = 0; i < records.size(); i++) {
      values[i] = records.get(i).getNumberValue(column);
    }

    return values;
  }

  /**
   * Returns an aggregate function that counts the number of elements.
   *
   * @return an aggregate function.
   */
  public static AggregateFunction count() {
    return new AggregateFunction<Object>("count", (records, column) -> {
      return (double) getObjectValues(records, column).length;
    });
  }

  /**
   * Returns an aggregate function that calculates the average of the given elements.
   *
   * @return an aggregate function.
   */
  public static AggregateFunction average() {
    return new AggregateFunction<Double>("average", (records, column) -> {
      double sum = sum().apply(records, column);
      double average = (double) sum / count().apply(records, column);

      return average;
    });
  }

  /**
   * Returns an aggregate function that calculates the sum of the given elements.
   *
   * @return an aggregate function.
   */
  public static AggregateFunction sum() {
    return new AggregateFunction<Double>("sum", (records, column) -> {
      double sum = 0;
      double[] data = getDoubleValues(records, column);

      for (int i = 0; i < data.length; i++) {
        sum = sum + data[i];
      }

      return sum;
    });
  }

  /**
   * Returns an aggregate function that calculates the minimum of the given elements.
   *
   * @return an aggregate function.
   */
  public static AggregateFunction min() {
    return new AggregateFunction<Double>("min", (records, column) -> {
      double min = Double.POSITIVE_INFINITY;
      double[] data = getDoubleValues(records, column);

      for (int i = 0; i < data.length; i++) {
        min = Math.min(min, data[i]);
      }

      return min;
    });
  }

  /**
   * Returns an aggregate function that calculates the maximum of the given elements.
   *
   * @return an aggregate function.
   */
  public static AggregateFunction max() {
    return new AggregateFunction<Double>("max", (records, column) -> {
      double max = Double.NEGATIVE_INFINITY;
      double[] data = getDoubleValues(records, column);

      for (int i = 0; i < data.length; i++) {
        max = Math.max(max, data[i]);
      }

      return max;
    });
  }
}
