package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;

public final class SelectClause extends SelectOrGroupClause {
    public final Token Select;
    public final Expression Expression;

    private SelectClause(final Token select, final Expression expression) {
        this.Select = select;
        this.Expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<SelectClause>");

        string.append(this.Select.toString());
        string.append(this.Expression.toString());
        string.append("</SelectClause>");

        return string.toString();
    }

    public static SelectClause parse(final TokenReader reader) {
        reader.mark();

        Token select = reader.accept(TokenName.SELECT);

        if (select == null) {
            reader.revert();
            return null;
        }

        Expression expression = com.health.script.AST.Expressions.Expression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new SelectClause(select, expression);
    }
}
