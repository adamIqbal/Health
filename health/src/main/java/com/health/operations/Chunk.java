package com.health.operations;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

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

            if (chunk.size() > 0) {
                groups.put(beginPer, chunk);
            }

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

        for (Entry<Object, List<Record>> entry : asSortedList(groups.entrySet())) {
            List<Record> chunk = entry.getValue();
            Record chunkedRecord = new Record(chunkedTable);

            chunkedRecord.setValue(keyColumn, entry.getKey());

            for (ColumnAggregateTuple operation : operations) {
                String column = operation.getColumn();

                if (operation.hasFunction()) {
                    double value = aggregate(chunk, column, operation.getFunction());

                    chunkedRecord.setValue(operation.getAggregateColumn(), value);
                } else {
                    chunkedRecord.setValue(operation.getColumn(), chunk.get(0).getValue(column));
                }
            }
        }

        return chunkedTable;
    }

    private static Iterable<Entry<Object, List<Record>>> asSortedList(final Set<Entry<Object, List<Record>>> entrySet) {
        List<Entry<Object, List<Record>>> list = new ArrayList<Entry<Object, List<Record>>>(entrySet);

        if (list.isEmpty()) {
            return list;
        }

        Class<?> type = list.get(0).getKey().getClass();

        if (Double.class.isAssignableFrom(type)) {
            list.sort((a, b) -> ((Double) a.getKey()).compareTo((Double) b.getKey()));
        } else if (String.class.isAssignableFrom(type)) {
            list.sort((a, b) -> ((String) a.getKey()).compareTo((String) b.getKey()));
        } else if (LocalDate.class.isAssignableFrom(type)) {
            list.sort((a, b) -> ((LocalDate) a.getKey()).compareTo((LocalDate) b.getKey()));
        } else {
            throw new IllegalStateException("Found illegal type in entrySet. "
                    + "EntrySet can only contain keys of type Double, String or LocalDate.");
        }

        return list;
    }

    private static double aggregate(
            final List<Record> records,
            final String column,
            final AggregateFunction<?> function) {
        return function.apply(records, column);
    }

    private static List<Column> createChunkTableColumns(
            final Table table,
            final String keyColumn,
            final List<ColumnAggregateTuple> operations) {
        List<Column> chunkedTableColumns = new ArrayList<Column>();

        chunkedTableColumns.add(new Column(keyColumn, 0, table.getColumn(keyColumn).getType()));

        int i = 1;
        for (ColumnAggregateTuple operation : operations) {
            chunkedTableColumns.add(createColumn(table, i++, operation));
        }

        return chunkedTableColumns;
    }

    private static Column createColumn(
            final Table table,
            final int index,
            final ColumnAggregateTuple operation) {
        Column column = table.getColumn(operation.getColumn());

        if (operation.hasFunction()) {
            return new Column(operation.getAggregateColumn(), index, ValueType.Number);
        } else {
            return new Column(operation.getColumn(), index, column.getType());
        }
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
