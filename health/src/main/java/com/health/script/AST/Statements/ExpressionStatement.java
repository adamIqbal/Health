package com.health.script.AST.Statements;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.StatementExpression;

public class ExpressionStatement extends Statement {
    public final StatementExpression Expression;
    public final Token Semicolon;

    public ExpressionStatement(StatementExpression expression,
            Token semicolon) {
        this.Expression = expression;
        this.Semicolon = semicolon;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<ExpressionStatement>");

        string.append(this.Expression.toString());
        string.append(this.Semicolon.toString());
        string.append("</ExpressionStatement>");

        return string.toString();
    }

    public static ExpressionStatement parse(
            final TokenReader reader) {
        StatementExpression expression = StatementExpression.parse(reader);

        if (expression == null) {
            return null;
        }

        Token semicolon = reader.accept(TokenName.SEMICOLON);

        if (semicolon == null) {
            // TODO: Generate diagnostic: expected ;
            return null;
        }

        return new ExpressionStatement(expression, semicolon);
    }
}
