package com.health.operations;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.health.AggregateFunctions;
import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.health.output.Output;

/**
 * A class for all chunking operations.
 *
 * @author daan
 *
 */
public final class Chunk {

	public static void main(String[] args0) {
		Column[] tableColumns = new Column[4];
		tableColumns[0] = new Column("date", 0, ValueType.Number);
		tableColumns[1] = new Column("meetwaarde1", 1, ValueType.Number);
		tableColumns[2] = new Column("name", 2, ValueType.String);
		tableColumns[3] = new Column("meetwaarde2", 3, ValueType.Number);

		Table table = new Table(Arrays.asList(tableColumns));

		// fill the table
		Record tmp = new Record(table);

		tmp.setValue("date", 10.0);
		tmp.setValue("meetwaarde1", 8.0);
		tmp.setValue("name", "Piet");
		tmp.setValue("meetwaarde2", 20.0);

		tmp = new Record(table);
		tmp.setValue("date", 10.0);
		tmp.setValue("meetwaarde1", 10.0);
		tmp.setValue("name", "Hein");
		tmp.setValue("meetwaarde2", 10.0);

		tmp = new Record(table);
		tmp.setValue("date", 10.0);
		tmp.setValue("meetwaarde1", 10.0);
		tmp.setValue("name", "Dolf");
		tmp.setValue("meetwaarde2", 10.0);

		tmp = new Record(table);
		tmp.setValue("date", 10.0);
		tmp.setValue("meetwaarde1", 10.0);
		tmp.setValue("name", "Piet");
		tmp.setValue("meetwaarde2", 10.0);

		tmp = new Record(table);
		tmp.setValue("date", 10.0);
		tmp.setValue("meetwaarde1", 10.0);
		tmp.setValue("name", "Piet");
		tmp.setValue("meetwaarde2", 10.0);

		tmp = new Record(table);
		tmp.setValue("date", 10.0);
		tmp.setValue("meetwaarde1", 10.0);
		tmp.setValue("name", "Dolf");
		tmp.setValue("meetwaarde2", 10.0);

		LocalDate day1 = LocalDate.of(2012, 12, 18);
		Period per = Period.ofDays(1);
		LocalDate day2 = day1.plus(per);

		Map<String, AggregateFunctions> operations = new HashMap<String, AggregateFunctions>();

		operations.put("meetwaarde1", AggregateFunctions.Average);
		operations.put("meetwaarde2", AggregateFunctions.Sum);

		Table chunkedTable = chunkByString(table, "name", operations);

		System.out.println(Output.formatTable(chunkedTable));

	}

	/**
	 * 
	 * @param table
	 * @param column
	 * @param operations
	 * @param period
	 * @return
	 */
	public static Table chunkByDate(Table table, String column,
			Map<String, AggregateFunctions> operations, Period period) {

		Table chunkedTable = new Table(table.getColumns());

		// find first date

		// new date tmp = first date + period.

		// make record[] for all record where date < tmp && date >= first date

		// aggregate the records

		// add record to chunkedTable

		// increment dates and loop back

		return chunkedTable;
	}

	/**
	 * chunks the data on same string, in the given column.
	 * 
	 * @param table
	 * @param operations
	 * @param column
	 *            on which the data is chunked with the same string.
	 * @return
	 */
	public static Table chunkByString(Table table, String column,
			Map<String, AggregateFunctions> operations) {
		Table chunkedTable = new Table(table.getColumns());

		ArrayList<String> found = new ArrayList<String>();
		List<Record> records = table.getRecords();
		List<Column> columns = table.getColumns();

		for (int i = 0; i < records.size(); i++) {
			String tmp = records.get(i).getStringValue(column);

			// if not allready found
			if (!found.contains(tmp)) {
				found.add(tmp);
				// list to aggregate
				List<Record> chunk = new ArrayList<Record>();
				for (int j = 0; j < records.size(); j++) {

					// if same value add record to list
					if (records.get(j).getStringValue(column).equals(tmp)) {
						chunk.add(records.get(j));
					}
				}

				Record chunkedRecord = new Record(chunkedTable);
				for (int k = 0; k < columns.size(); k++) {
					if (operations.containsKey(columns.get(k).getName())) {
						double tmpValue = aggregate(chunk, columns.get(k)
								.getName(), operations.get(columns.get(k)
								.getName()));
						chunkedRecord.setValue(k, tmpValue);
					} else {
						switch (columns.get(k).getType()) {
						case String:
							chunkedRecord.setValue(k, records.get(i)
									.getStringValue(columns.get(k).getName()));
							break;
						case Number:
							System.out.println(columns.get(k).getType());
							chunkedRecord.setValue(k, records.get(i)
									.getNumberValue(columns.get(k).getName()));
							break;
						case Date:
							// make possible for Dates
							break;
						default:
							// error
						}
					}
				}

			}
		}

		return chunkedTable;
	}

	private static double aggregate(List<Record> chunk, String column,
			AggregateFunctions function) {
		double[] values = new double[chunk.size()];

		for (int i = 0; i < chunk.size(); i++) {
			values[i] = chunk.get(i).getNumberValue(column);
		}

		return Aggregator.aggregate(values, function);
	}
}
