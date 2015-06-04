package com.health.operations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * A static class to preform the Connect action.
 *
 * @author daan
 *
 */
public class Connect {

	/**
	 * 
	 */
	protected Connect(){
		
	}
	/**
	 * A function which connects two Table objects and return one table object
	 * ordered by date.
	 *
	 * @param table1
	 *            the first table to connect.
	 * @param table2
	 *            the table to connect with.
	 * @param connections
	 *            a list of Collumn Connections.
	 * @return A Table object.
	 */
	public static Table connect(final Table table1, final Table table2,
			List<ColumnConnection> connections) {

		List<Column> connectedTableCols = makeNewTableColumns(
				table1.getColumns(), table2.getColumns(), connections);
		// make new Table
		Table connectedTable = new Table(connectedTableCols);

		addRecords(connectedTable, table1, connections);
		addRecords(connectedTable, table2, connections);

		Iterator<Column> it = connectedTableCols.iterator();
		while (it.hasNext()) {
			Column next = it.next();
			if (next.getType() == ValueType.Date) {
				connectedTable = sortTable(connectedTable, next.getName());
				break;
			}
		}
		return connectedTable;
	}

	private static List<Column> makeNewTableColumns(
			final List<Column> table1Cols, final List<Column> table2Cols,
			List<ColumnConnection> connections) {
		List<Column> result = new ArrayList<Column>();
		Iterator<Column> it = table1Cols.iterator();

		while (it.hasNext()) {
			Column tmp = it.next();
			int index = isInConnections(tmp, connections);
			if (index >= 0) {
				result.add(new Column(connections.get(index).getNewName(),
						result.size(), tmp.getType()));
			} else {
				result.add(new Column(tmp.getName(), result.size(), tmp
						.getType()));
			}
		}

		// loop through table2;

		it = table2Cols.iterator();
		while (it.hasNext()) {
			Column tmp = it.next();
			int index = isInConnections(tmp, connections);
			if (!result.contains(tmp) && index == -1) {
				result.add(new Column(tmp.getName(), result.size(), tmp
						.getType()));
			}

		}
		return result;
	}

	/**
	 *
	 * @param col
	 *            the column to look for.
	 * @param connections
	 *            the list of connections to look for.
	 * @return the index of the the connection in the list. If not found return
	 *         -1;
	 */
	private static int isInConnections(final Column col,
			final List<ColumnConnection> connections) {
		Iterator<ColumnConnection> it = connections.iterator();
		int index = 0;
		while (it.hasNext()) {
			ColumnConnection tmp = it.next();
			if (tmp.getColumn1().equals(col.getName())
					|| tmp.getColumn2().equals(col.getName())) {
				return index;
			}
			index++;
		}
		return -1;

	}

	private static void addRecords(final Table tableNew, final Table tableToAdd,
			final List<ColumnConnection> connections) {
		List<Record> recList = tableToAdd.getRecords();
		List<Column> colList = tableToAdd.getColumns();

		for (int i = 0; i < recList.size(); i++) {
			Record tmpRec = new Record(tableNew);
			Iterator<Column> it = colList.iterator();

			while (it.hasNext()) {
				Column tmpCol = it.next();
				String tmpColName = tmpCol.getName();
				String recColName = tmpColName;

				int indexInConnections = isInConnections(tmpCol, connections);
				if (indexInConnections >= 0) {
					tmpColName = connections.get(indexInConnections)
							.getNewName();

				}

				switch (tmpCol.getType()) {
				case String:
					tmpRec.setValue(tmpColName,
							recList.get(i).getStringValue(recColName));
					break;
				case Number:
					tmpRec.setValue(tmpColName,
							recList.get(i).getNumberValue(recColName));
					break;
				case Date:
					tmpRec.setValue(tmpColName,
							recList.get(i).getDateValue(recColName));
					break;
				default:
					// error
				}
			}
		}
	}

	private static Table sortTable(final Table table, final String colName) {
		List<Record> records = table.getRecords();
		Table sortedTable = new Table(table.getColumns());
		int[] found = new int[records.size()];

		// fil found with zeros
		for (int i = 0; i < found.length; i++) {
			found[i] = 0;
		}

		while (sortedTable.getRecords().size() != records.size()) {
			LocalDate minDate = LocalDate.MAX;
			int foundIndex = -1;
			for (int j = 0; j < records.size(); j++) {
				if (found[j] == 0) {
					if (records.get(j).getDateValue(colName).isBefore(minDate)) {
						minDate = records.get(j).getDateValue(colName);
						foundIndex = j;
					}
				}
			}

			found[foundIndex] = 1;
			records.get(foundIndex).copyTo(sortedTable);
		}

		return sortedTable;
	}
}
