package com.health.operations;

import java.util.List;

import com.health.Column;
import com.health.Record;
import com.health.Table;

public class TableWithDays {

  public static Table TableDays(final Table table) {
    Table origTable = table;
    List<Record> recList = origTable.getRecords();
    List<Column> colList = origTable.getColumns();

    Table result = null;

    for (int i = 0; i < recList.size(); i++) {

      Record record = new Record(result);

      colList.add(new Column("day_of_week", colList.size(), null));

      for (Column column : colList) {
        String name = column.getName();
        String newName = name;

        record.setValue(name, recList.get(i).getValue(newName));
      }
    }

    return null;

  }

}
