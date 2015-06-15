package com.health.operations;

import java.time.LocalDate;
import java.util.ArrayList;
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
public final class Connect {
    /**
	 *
	 */
    private Connect() {

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
     *            a list of Column Connections.
     * @return A Table object.
     */
    public static Table connect(final Table table1, final Table table2,
            final List<ColumnConnection> connections) {

        List<Column> connectedTableCols = makeNewTableColumns(
                table1.getColumns(), table2.getColumns(), connections);
        // make new Table
        Table connectedTable = new Table(connectedTableCols);

        addRecords(connectedTable, table1, connections);
        addRecords(connectedTable, table2, connections);

        for (Column column : connectedTableCols) {
            if (column.getType() == ValueType.Date && isInConnections(column, connections) >= 0) {
                connectedTable = sortTable(connectedTable, column.getName());
                break;
            }
        }

        return connectedTable;
    }

    private static List<Column> makeNewTableColumns(
            final List<Column> table1Cols, final List<Column> table2Cols,
            final List<ColumnConnection> connections) {
        List<Column> result = new ArrayList<Column>();

        // loop through table1
        for (Column column : table1Cols) {
            int index = isInConnections(column, connections);

            if (index >= 0) {
                result.add(new Column(connections.get(index).getNewName(),
                        result.size(), column.getType()));
            } else {
                result.add(new Column(column.getName(), result.size(), column
                        .getType()));
            }
        }

        // loop through table2
        for (Column column : table2Cols) {
            int index = isInConnections(column, connections);

            if (!result.contains(column) && index == -1) {
                result.add(new Column(column.getName(), result.size(), column
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
        int index = 0;

        for (ColumnConnection connection : connections) {
            if (connection.getColumn1().equals(col.getName())
                    || connection.getColumn2().equals(col.getName())) {
                return index;
            }

            index++;
        }

        return -1;
    }

    private static void addRecords(final Table tableNew,
            final Table tableToAdd, final List<ColumnConnection> connections) {
        List<Record> recList = tableToAdd.getRecords();
        List<Column> colList = tableToAdd.getColumns();

        for (int i = 0; i < recList.size(); i++) {
            Record record = new Record(tableNew);

            for (Column column : colList) {
                String name = column.getName();
                String newName = name;

                int indexInConnections = isInConnections(column, connections);

                if (indexInConnections >= 0) {
                    newName = connections.get(indexInConnections).getNewName();

                }

                record.setValue(newName, recList.get(i).getValue(name));
            }
        }
    }

    private static Table sortTable(final Table table, final String colName) {
        List<Record> records = new ArrayList<Record>(table.getRecords());

        records.sort((a, b) -> {
            LocalDate dateA = a.getDateValue(colName);
            LocalDate dateB = b.getDateValue(colName);

            if (dateA == null && dateB == null) {
                return 0;
            } else if (dateA == null && dateB != null) {
                return -1;
            } else if (dateA != null && dateB == null) {
                return 1;
            } else {
                return dateA.compareTo(dateA);
            }
        });

        Table sortedTable = new Table(table.getColumns());

        for (Record record : records) {
            record.copyTo(sortedTable);
        }

        return sortedTable;
    }
}
