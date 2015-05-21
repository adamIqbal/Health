package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.ArgumentList;

public final class InvocationExpression extends PrimaryExpression implements
        StatementExpression {
    public final PrimaryExpression Expression;
    public final Token OpenParenthesis;
    public final ArgumentList Arguments;
    public final Token CloseParenthesis;

    private InvocationExpression(
            final PrimaryExpression expression,
            final Token openParenthesis,
            final Token closeParenthesis) {
        this(expression, openParenthesis, null, closeParenthesis);
    }

    private InvocationExpression(
            final PrimaryExpression expression,
            final Token openParenthesis,
            final ArgumentList arguments,
            final Token closeParenthesis) {
        this.Expression = expression;
        this.OpenParenthesis = openParenthesis;
        this.Arguments = arguments;
        this.CloseParenthesis = closeParenthesis;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<InvocationExpression>");

        string.append(this.Expression.toString());
        string.append(this.OpenParenthesis.toString());

        if (this.Arguments != null) {
            string.append(this.Arguments.toString());
        }

        string.append(this.CloseParenthesis.toString());
        string.append("</InvocationExpression>");

        return string.toString();
    }

    public static InvocationExpression parse(final TokenReader reader) {
        reader.mark();
        PrimaryExpression expression = PrimaryExpression.parse(reader);

        if (expression instanceof InvocationExpression) {
            reader.pop();
            return (InvocationExpression) expression;
        }

        reader.revert();
        return null;
    }

    public static InvocationExpression parse(PrimaryExpression expression, final TokenReader reader) {
        InvocationExpression invocation = null;

        if (expression == null) {
            return null;
        }

        while (true) {
            reader.mark();

            Token openParenthesis = reader.accept(TokenName.OPEN_PARENTHESIS);

            if (openParenthesis == null) {
                reader.revert();
                break;
            }

            ArgumentList arguments = ArgumentList.parse(reader);

            Token closeParenthesis = reader.accept(TokenName.CLOSE_PARENTHESIS);

            if (closeParenthesis == null) {
                reader.revert();
                break;
            }

            reader.pop();

            invocation = new InvocationExpression(expression, openParenthesis, arguments, closeParenthesis);
            expression = invocation;
        }

        if (invocation == null) {
            return null;
        }

        return invocation;
    }
}
