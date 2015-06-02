package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;

public final class GroupClause extends SelectOrGroupClause {
    public final Token Group;
    public final Expression Source;
    public final Token By;
    public final Expression Expression;

    private GroupClause(
            final Token group,
            final Expression source,
            final Token by,
            final Expression expression) {
        this.Group = group;
        this.Source = source;
        this.By = by;
        this.Expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<GroupClause>");

        string.append(this.Group.toString());
        string.append(this.Source.toString());
        string.append(this.By.toString());
        string.append(this.Expression.toString());
        string.append("</GroupClause>");

        return string.toString();
    }

    public static GroupClause parse(final TokenReader reader) {
        reader.mark();

        Token group = reader.accept(TokenName.GROUP);

        if (group == null) {
            reader.revert();
            return null;
        }

        Expression source = com.health.script.AST.Expressions.Expression.parse(reader);

        if (source == null) {
            reader.revert();
            return null;
        }

        Token by = reader.accept(TokenName.BY);

        if (by == null) {
            reader.revert();
            return null;
        }

        Expression expression = com.health.script.AST.Expressions.Expression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new GroupClause(group, source, by, expression);
    }
}
