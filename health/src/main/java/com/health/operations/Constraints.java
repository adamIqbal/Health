package com.health.operations;

import java.util.function.Function;

import com.health.Record;

import com.health.Table;

public class Constraints {

  /**
   * Creates a new table containing only the records for which the given predicate is evaluated as
   * true.
   *
   * @param predicate
   *          a predicate that determines whether or not to add a given record to the new table.
   * @return a new table containing only the records for which the given predicate is evaluated as
   *         true.
   */
  public static Table constrain(final Function<Record, Boolean> predicate, Table table) {
    Table constrained = new Table(table.getColumns());

    for (Record record : table.getRecords()) {
      if (predicate.apply(record)) {
        record.copyTo(constrained);
      }
    }

    return constrained;
  }

  // public static Table constrain(final Table table, final String column, final Object value,
  // final ConstraintsEnums cst) {
  //
  // Table constrainedTable = table;
  //
  // switch (cst) {
  //
  // case EqualTo:
  // constrainedTable = equal(table, column);
  // break;
  // case GreaterOrEqual:
  // constrainedTable = greatereq(table, column);
  // break;
  // case GreaterThan:
  // constrainedTable = greater(table, column);
  // break;
  // case SmallerOrEqual:
  // constrainedTable = smallereq(table, column);
  // break;
  // case SmallerThan:
  // constrainedTable = smaller(table, column);
  // break;
  //
  // default:
  // // FIXME: ERROR
  // System.out.println("default case reached in constraints, FIX this error handling!");
  // }
  //
  // return constrainedTable;
  //
  // }
}
