package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class ParenthesizedExpression extends PrimaryExpression {
    public final Token OpenParenthesis;
    public final Expression Expression;
    public final Token CloseParenthesis;

    private ParenthesizedExpression(
            final Token openParenthesis,
            final Expression expression,
            final Token closeParenthesis) {
        this.OpenParenthesis = openParenthesis;
        this.Expression = expression;
        this.CloseParenthesis = closeParenthesis;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<ParenthesizedExpression>");

        string.append(this.OpenParenthesis.toString());
        string.append(this.Expression.toString());
        string.append(this.CloseParenthesis.toString());
        string.append("</ParenthesizedExpression>");

        return string.toString();
    }

    public static ParenthesizedExpression parse(
            final TokenReader reader) {
        reader.mark();
        Token openParenthesis = reader.accept(TokenName.OPEN_PARENTHESIS);

        if (openParenthesis == null) {
            reader.revert();
            return null;
        }

        PrimaryExpression expression = PrimaryExpression.parse(reader);

        if (expression == null) {
            reader.revert();
            return null;
        }

        Token closeParenthesis = reader.accept(TokenName.CLOSE_PARENTHESIS);

        if (closeParenthesis == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new ParenthesizedExpression(openParenthesis, expression,
                closeParenthesis);
    }
}
