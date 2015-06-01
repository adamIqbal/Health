package com.health.input;

import com.health.AggregateFunctions;

/**
 * A class which preforms all agregate functions.
 * 
 * @author daan
 *
 */
public class Aggregator {

	/**
	 * Select and execute an aggregate function depending. Does an Average by
	 * default.
	 * 
	 * @param values
	 * @param function
	 * @return
	 */
	public static double aggregate(double[] values, AggregateFunctions function) {
		double result;
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
		default:
			result = average(values);
		}

		return result;
	}

	private static double average(double[] data) {
		double sum = sum(data);
		double average;

		average = (double) sum / data.length;
		return average;
	}

	private static double sum(double[] data) {
		double sum = 0;

		for (int i = 0; i < data.length; i++) {
			sum = sum + data[i];
		}

		return sum;
	}

	private static double min(double[] data) {
		double min = data[0];

		for (int i = 0; i < data.length; i++) {
			if (data[i] < min) {
				min = data[i];
			}
		}

		return min;

	}

	private static double max(double[] data) {
		double max = data[0];

		for (int i = 0; i < data.length; i++) {
			if (data[i] > max) {
				max = data[i];
			}
		}

		return max;

	}
}
