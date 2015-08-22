package com.health.operations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * a class to read time data in tables.
 * @author daan
 *
 */
public class ReadTime {

    /**
     * an unused constructor.
     */
    protected ReadTime() {

    }

    /**
     * Creates a new table, which adds the time in the timeCol to the date in the dateCol.
     * @param table
     *            the table to manipulate.
     * @param dateCol
     *            the columns which has the date.
     * @param timeCol
     *            the columns which has the time as a number between 0 and
     *            2400.
     * @return 	table
     */
    public static Table addTimeToDate(final Table table, final Column dateCol,
            final Column timeCol) {
        if (dateCol.getType() != ValueType.Date
                && timeCol.getType() != ValueType.Number) {
            return table;
        }

        Table copy = table.clone();

        List<Record> recordList = copy.getRecords();
        for (Record rec : recordList) {

            LocalDateTime date = rec.getDateValue(dateCol.getName());

            double time = rec.getNumberValue(timeCol.getName());

            long min = (long) (time % 100);
            date = date.plus(min, ChronoUnit.MINUTES);

            long hours = (long) ((time - min) / 100);
            date = date.plus(hours, ChronoUnit.HOURS);

            rec.setValue(copy.getDateColumn().getName(), date);

        }

        return copy;
    }
}
