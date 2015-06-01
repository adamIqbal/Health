package com.health.script.AST.QueryExpressions;

import com.health.Utils;
import com.health.script.TokenReader;

public abstract class QueryBodyClause {
    public static QueryBodyClause parse(final TokenReader reader) {
        return Utils.firstNonNull(
                () -> FromClause.parse(reader),
                () -> LetClause.parse(reader),
                () -> WhereClause.parse(reader),
                () -> JoinClause.parse(reader),
                () -> OrderbyClause.parse(reader));
    }
}
