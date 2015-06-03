package com.health.operations;

import com.health.ConstraintsEnums;
import com.health.Table;

public class Constraints {

  public static Table constrain(final Table table, final Object column, final ConstraintsEnums cst) {
    Table constrainedTable = table;

    if (column instanceof String) {
      String constrained = (String) column;
      constrainEqual(table,column);

    } else {

      switch (cst) {

      case EqualTo:
        constrainedTable = average(values);
        break;
      case GreaterOrEqual:
        constrainedTable = sum(values);
        break;
      case GreaterThan:
        constrainedTable = min(values);
        break;
      case SmallerOrEqual:
        constrainedTable = max(values);
        break;
      case SmallerThan:
        constrainedTable = max(values);
        break;

      default:
        // FIXME: ERROR
      }

      return constrainedTable;

    }
  }

}

// constraint by double/string/date

