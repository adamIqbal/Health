package com.health.operations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class ReadTime {

    public static void addTimeToDate(Table table, Column dateCol, Column timeCol) {
        if (dateCol.getType() != ValueType.Date
                && timeCol.getType() != ValueType.Number) {
            return;
        }
        
        List<Record> recordList = table.getRecords();
        for (Record rec : recordList) {
       
            LocalDateTime date = rec.getDateValue(dateCol.getName());

            double time = rec.getNumberValue(timeCol.getName());

            long min = (long) (time % 100);
            date = date.plus(min, ChronoUnit.MINUTES);

            long hours = (long) ((time - min) / 100);
            date = date.plus(hours, ChronoUnit.HOURS);
            
            rec.setValue(table.getDateColumn().getName(), date);
            
        }
    }
}
