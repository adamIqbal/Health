package com.health.script.AST.QueryExpressions;

import java.util.ArrayList;
import java.util.List;

import com.health.script.TokenReader;

public final class QueryBody {
    public final List<QueryBodyClause> BodyClauses;
    public final SelectOrGroupClause SelectOrGroupClause;
    public final QueryContinuation QueryContinuation;

    private QueryBody(
            final List<QueryBodyClause> bodyClauses,
            final SelectOrGroupClause selectOrGroupClause) {
        this(bodyClauses, selectOrGroupClause, null);
    }

    private QueryBody(
            final List<QueryBodyClause> bodyClauses,
            final SelectOrGroupClause selectOrGroupClause,
            final QueryContinuation queryContinuation) {
        this.BodyClauses = bodyClauses;
        this.SelectOrGroupClause = selectOrGroupClause;
        this.QueryContinuation = queryContinuation;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<QueryBody>");

        for (QueryBodyClause clause : this.BodyClauses) {
            string.append(clause.toString());
        }

        string.append(this.SelectOrGroupClause.toString());

        if (this.QueryContinuation != null) {
            string.append(this.QueryContinuation.toString());
        }

        string.append("</QueryBody>");

        return string.toString();
    }

    public static QueryBody parse(final TokenReader reader) {
        reader.mark();

        List<QueryBodyClause> clauses = new ArrayList<QueryBodyClause>();

        while (true) {
            QueryBodyClause clause = QueryBodyClause.parse(reader);

            if (clause == null) {
                break;
            }

            clauses.add(clause);
        }

        SelectOrGroupClause selectOrGroupClause = com.health.script.AST.QueryExpressions.SelectOrGroupClause
                .parse(reader);

        if (selectOrGroupClause == null) {
            reader.revert();
            return null;
        }

        QueryContinuation queryContinuation = com.health.script.AST.QueryExpressions.QueryContinuation.parse(reader);

        reader.pop();
        return new QueryBody(clauses, selectOrGroupClause, queryContinuation);
    }
}
