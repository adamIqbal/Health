package com.health.script.AST.QueryExpressions;

import com.health.Utils;
import com.health.script.TokenReader;

public abstract class SelectOrGroupClause {
    public static SelectOrGroupClause parse(final TokenReader reader) {
        return Utils.firstNonNull(
                () -> SelectClause.parse(reader),
                () -> GroupClause.parse(reader));
    }
}
