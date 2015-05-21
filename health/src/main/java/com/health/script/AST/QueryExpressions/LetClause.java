package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;
import com.health.script.AST.Expressions.Identifier;

public final class LetClause extends QueryBodyClause {
    public final Token Let;
    public final Identifier Identifier;
    public final Token Equals;
    public final Expression Expression;

    private LetClause(
            final Token let,
            final Identifier identifier,
            final Token equals,
            final Expression expression) {
        this.Let = let;
        this.Identifier = identifier;
        this.Equals = equals;
        this.Expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<LetClause>");

        string.append(this.Let.toString());
        string.append(this.Identifier.toString());
        string.append(this.Equals.toString());
        string.append(this.Expression.toString());
        string.append("</LetClause>");

        return string.toString();
    }

    public static LetClause parse(final TokenReader reader) {
        reader.mark();

        Token let = reader.accept(TokenName.LET);

        if (let == null) {
            reader.revert();
            return null;
        }

        Identifier identifier = com.health.script.AST.Expressions.Identifier.parse(reader);

        if (identifier == null) {
            reader.revert();
            return null;
        }

        Token equals = reader.accept(TokenName.EQUALS);

        if (equals == null) {
            reader.revert();
            return null;
        }

        Expression expression = com.health.script.AST.Expressions.Expression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new LetClause(let, identifier, equals, expression);
    }
}
