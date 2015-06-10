package com.health.operations;

import com.health.AggregateFunctions;

/**
 * A class which preforms all aggregate functions.
 *
 * @author daan
 *
 */
public class Aggregator {

	protected Aggregator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Select and execute an aggregate function depending. Does an Average by
	 * default.
	 *
	 * @param values
	 *            an array of doubles containing the values to aggregate.
	 * @param function
	 *            the function to be called.
	 * @return result of the aggregate function as a double.
	 */
	public static double aggregate(final double[] values,
			final AggregateFunctions function) {
	  
		double result = 0;
		
		switch (function) {
		
		case Average:
			result = average(values);
			break;
		case Sum:
			result = sum(values);
			break;
		case Min:
			result = min(values);
			break;
		case Max:
			result = max(values);
			break;
		}

		return result;
	}

	private static double average(final double[] data) {
		double sum = sum(data);
		double average;

		average = (double) sum / data.length;
		return average;
	}

	private static double sum(final double[] data) {
		double sum = 0;

		for (int i = 0; i < data.length; i++) {
			sum = sum + data[i];
		}

		return sum;
	}

	private static double min(final double[] data) {
		double min = data[0];

		for (int i = 0; i < data.length; i++) {
			if (data[i] < min) {
				min = data[i];
			}
		}

		return min;

	}

	private static double max(final double[] data) {
		double max = data[0];

		for (int i = 0; i < data.length; i++) {
			if (data[i] > max) {
				max = data[i];
			}
		}

		return max;

	}
}
