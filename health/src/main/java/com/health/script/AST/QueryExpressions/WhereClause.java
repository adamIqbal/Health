package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;

public final class WhereClause extends QueryBodyClause {
    public final Token Where;
    public final Expression Expression;

    private WhereClause(
            final Token where,
            final Expression expression) {
        this.Where = where;
        this.Expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<WhereClause>");

        string.append(this.Where.toString());
        string.append(this.Expression.toString());
        string.append("</WhereClause>");

        return string.toString();
    }

    public static WhereClause parse(final TokenReader reader) {
        reader.mark();

        Token where = reader.accept(TokenName.WHERE);

        if (where == null) {
            reader.revert();
            return null;
        }
        Expression expression = com.health.script.AST.Expressions.Expression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new WhereClause(where, expression);
    }
}
