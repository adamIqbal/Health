package com.health.script.AST.QueryExpressions;

import com.health.Utils;
import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;

public final class Ordering {
    public final Expression Expression;
    public final Token Ordering;

    private Ordering(final Expression expression, final Token ordering) {
        this.Expression = expression;
        this.Ordering = ordering;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Ordering>");

        string.append(this.Expression.toString());
        string.append(this.Ordering.toString());
        string.append("</Ordering>");

        return string.toString();
    }

    public static Ordering parse(final TokenReader reader) {
        reader.mark();

        Expression expression = com.health.script.AST.Expressions.Expression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        Token ordering = Utils.firstNonNull(
                () -> reader.accept(TokenName.ASCENDING),
                () -> reader.accept(TokenName.DESCENDING));

        if (ordering != null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new Ordering(expression, ordering);
    }
}
