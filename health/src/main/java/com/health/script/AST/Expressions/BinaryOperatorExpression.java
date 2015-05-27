package com.health.script.AST.Expressions;

import com.health.script.Token;

public final class BinaryOperatorExpression implements BinaryExpression {
    public final BinaryExpression LeftExpression;
    public final Token Operator;
    public final UnaryExpression RightExpression;

    private BinaryOperatorExpression(
            final BinaryExpression leftExpression,
            final Token operator,
            final UnaryExpression rightExpression) {
        this.LeftExpression = leftExpression;
        this.Operator = operator;
        this.RightExpression = rightExpression;
    }
}
