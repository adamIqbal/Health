package com.health.operations;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.health.Column;
import com.health.Record;
import com.health.Table;

/**
 * A class for all chunking operations.
 *
 * @author daan
 *
 */
public final class Chunk {
    private Chunk() {
    }

    /**
     * A function to chunk a table by a given period.
     *
     * @param table
     *            the {@link Table} to be chunked.
     * @param dateColumn
     *            the column on which to chunk. The column must be a column of
     *            type Date.
     * @param operations
     *            a list of columns and their aggregate operation.
     * @param period
     *            the period between chunk, could be days, months, years.
     * @return a chunked Table.
     */
    public static Table chunkByPeriod(
            final Table table,
            final String dateColumn,
            final List<ColumnAggregateTuple> operations,
            final Period period) {
        Map<Object, List<Record>> groups = groupByPeriod(table, dateColumn, period);

        return flattenGroups(table, dateColumn, operations, groups);
    }

    /**
     * A function to chunk a table by equality on the given column.
     *
     * @param table
     *            the table to be chunked.
     * @param keyColumn
     *            the column on which to chunk.
     * @param operations
     *            a list of columns and their aggregate operation.
     * @return a chunked Table.
     */
    public static Table chunkByColumn(
            final Table table,
            final String keyColumn,
            final List<ColumnAggregateTuple> operations) {
        Map<Object, List<Record>> groups = groupByKey(table, keyColumn);

        return flattenGroups(table, keyColumn, operations, groups);
    }

    private static Map<Object, List<Record>> groupByPeriod(
            final Table table,
            final String column,
            final Period period) {
        Map<Object, List<Record>> groups = new HashMap<Object, List<Record>>();

        LocalDate beginPer = getFirstDate(table, column);
        LocalDate lastDate = getLastDate(table, column);
        LocalDate endOfPer = LocalDate.MIN;

        while (!lastDate.isBefore(endOfPer)) {
            endOfPer = beginPer.plus(period);

            List<Record> chunk = findRecordsInPeriod(table, column, beginPer, endOfPer);

            groups.put(beginPer, chunk);

            beginPer = endOfPer;
        }

        return groups;
    }

    private static Map<Object, List<Record>> groupByKey(final Table table, final String column) {
        Map<Object, List<Record>> groups = new HashMap<Object, List<Record>>();

        for (Record record : table) {
            Object key = record.getValue(column);

            insertInGroup(groups, key, record);
        }

        return groups;
    }

    private static void insertInGroup(
            final Map<Object, List<Record>> groups,
            final Object key,
            final Record record) {
        List<Record> group = groups.get(key);

        if (group == null) {
            group = new ArrayList<Record>();
            groups.put(key, group);
        }

        group.add(record);
    }

    private static Table flattenGroups(
            final Table table,
            final String keyColumn,
            final List<ColumnAggregateTuple> operations,
            final Map<Object, List<Record>> groups) {
        List<Column> columns = createChunkTableColumns(table, keyColumn, operations);
        Table chunkedTable = new Table(columns);

        for (Entry<Object, List<Record>> entry : groups.entrySet()) {
            List<Record> chunk = entry.getValue();
            Record chunkedRecord = new Record(chunkedTable);

            for (ColumnAggregateTuple operation : operations) {
                double value = aggregate(chunk, operation.getColumn(), operation.getFunction());

                chunkedRecord.setValue(operation.getColumn(), value);
            }
        }

        return chunkedTable;
    }

    private static double aggregate(
            final List<Record> chunk,
            final String column,
            final AggregateFunction function) {
        double[] values = new double[chunk.size()];

        for (int i = 0; i < chunk.size(); i++) {
            values[i] = chunk.get(i).getNumberValue(column);
        }

        return function.apply(values);
    }

    private static List<Column> createChunkTableColumns(
            final Table table,
            final String keyColumn,
            final List<ColumnAggregateTuple> operations) {
        List<Column> chunkedTableColumns = new ArrayList<Column>();

        chunkedTableColumns.add(new Column(keyColumn, 0, table.getColumn(keyColumn).getType()));

        int i = 0;
        for (ColumnAggregateTuple operation : operations) {
            String name = operation.getColumn();
            Column column = table.getColumn(name);
            String newName = operation.getFunction().getName() + "_" + name;

            chunkedTableColumns.add(new Column(newName, i, column.getType()));
        }

        return chunkedTableColumns;
    }

    private static LocalDate getFirstDate(final Table table, final String column) {
        LocalDate res = LocalDate.MAX;

        for (Record record : table) {
            LocalDate tmp = record.getDateValue(column);

            if (tmp.isBefore(res)) {
                res = tmp;
            }
        }

        return res;
    }

    private static LocalDate getLastDate(final Table table, final String column) {
        LocalDate res = LocalDate.MIN;

        for (Record record : table) {
            LocalDate tmp = record.getDateValue(column);

            if (tmp.isAfter(res)) {
                res = tmp;
            }
        }

        return res;
    }

    private static List<Record> findRecordsInPeriod(
            final Table table,
            final String column,
            final LocalDate beginOfPer,
            final LocalDate endOfPer) {
        List<Record> chunk = new ArrayList<Record>();

        for (Record record : table) {
            LocalDate date = record.getDateValue(column);

            if ((date.isAfter(beginOfPer) || date.isEqual(beginOfPer)) && date.isBefore(endOfPer)) {
                chunk.add(record);
            }
        }

        return chunk;
    }
}
