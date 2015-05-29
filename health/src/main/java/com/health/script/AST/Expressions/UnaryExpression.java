package com.health.script.AST.Expressions;

import com.health.script.TokenReader;

public interface UnaryExpression extends BinaryExpression {
    public static UnaryExpression parse(
            final TokenReader reader) {
        return PrimaryExpression.parse(reader);
    }
}
