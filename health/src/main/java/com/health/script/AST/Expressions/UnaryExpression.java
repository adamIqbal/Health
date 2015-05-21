package com.health.script.AST.Expressions;

import com.health.script.TokenReader;

public abstract class UnaryExpression extends BinaryExpression {
    public static UnaryExpression parse(
            final TokenReader reader) {
        return PrimaryExpression.parse(reader);
    }
}
