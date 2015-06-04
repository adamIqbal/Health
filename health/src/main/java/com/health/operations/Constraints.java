package com.health.operations;

import com.health.ConstraintsEnums;
import static com.health.operations.functions.ConstrainFunctions.*;
import com.health.Table;

public class Constraints {

  public static Table constrain(final Table table, final Object column, final ConstraintsEnums cst) {
    Table constrainedTable = table;

    switch (cst) {

    case EqualTo:
      constrainedTable = equal(table, column);
      break;
    case GreaterOrEqual:
      constrainedTable = greatereq(table, column);
      break;
    case GreaterThan:
      constrainedTable = greater(table, column);
      break;
    case SmallerOrEqual:
      constrainedTable = smallereq(table, column);
      break;
    case SmallerThan:
      constrainedTable = smaller(table, column);
      break;

    default:
      // FIXME: ERROR
      System.out.println("default case reached in constraints, FIX this error handling!");
    }

    return constrainedTable;

  }
}

// constraint by double/string/date

