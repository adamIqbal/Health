package com.health.script.AST.Expressions;

import com.health.script.Token;

public final class UnaryOperatorExpression {
    public final Token Operator;
    public final UnaryExpression Expression;

    private UnaryOperatorExpression(
            final Token operator,
            final UnaryExpression expression) {
        this.Operator = operator;
        this.Expression = expression;
    }
}
