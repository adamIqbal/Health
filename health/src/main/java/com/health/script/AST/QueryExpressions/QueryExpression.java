package com.health.script.AST.QueryExpressions;

import com.health.script.TokenReader;
import com.health.script.AST.Expressions.NonAssignmentExpression;

public final class QueryExpression implements NonAssignmentExpression {
    public final FromClause FromClause;
    public final QueryBody QueryBody;

    private QueryExpression(
            final FromClause fromClause,
            final QueryBody queryBody) {
        this.FromClause = fromClause;
        this.QueryBody = queryBody;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<QueryExpression>");

        string.append(this.FromClause.toString());
        string.append(this.QueryBody.toString());
        string.append("</QueryExpression>");

        return string.toString();
    }

    public static QueryExpression parse(final TokenReader reader) {
        reader.mark();

        FromClause fromClause = com.health.script.AST.QueryExpressions.FromClause.parse(reader);

        if (fromClause == null) {
            reader.revert();
            return null;
        }

        QueryBody queryBody = com.health.script.AST.QueryExpressions.QueryBody.parse(reader);

        if (queryBody == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new QueryExpression(fromClause, queryBody);
    }
}
