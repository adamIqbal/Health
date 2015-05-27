package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Type;

public final class CastExpression implements UnaryExpression {
    public final Token OpenParenthesis;
    public final Type Type;
    public final Token CloseParenthesis;
    public final UnaryExpression Expression;

    private CastExpression(
            final Token openParenthesis,
            final Type type,
            final Token closeParenthesis,
            final UnaryExpression expression) {
        this.OpenParenthesis = openParenthesis;
        this.Type = type;
        this.CloseParenthesis = closeParenthesis;
        this.Expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<CastExpression>");

        string.append(this.OpenParenthesis.toString());
        string.append(this.Type.toString());
        string.append(this.CloseParenthesis.toString());
        string.append(this.Expression.toString());
        string.append("</CastExpression>");

        return string.toString();
    }

    public static CastExpression parse(
            final TokenReader reader) {
        reader.mark();

        Token openParenthesis = reader.accept(TokenName.OPEN_PARENTHESIS);

        if (openParenthesis == null) {
            reader.revert();
            return null;
        }

        Type type = com.health.script.AST.Type.parse(reader);

        if (type == null) {
            reader.revert();
            return null;
        }

        Token closeParenthesis = reader.accept(TokenName.CLOSE_PARENTHESIS);

        if (closeParenthesis == null) {
            reader.revert();
            return null;
        }

        UnaryExpression expression = UnaryExpression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new CastExpression(openParenthesis, type, closeParenthesis,
                expression);
    }
}
