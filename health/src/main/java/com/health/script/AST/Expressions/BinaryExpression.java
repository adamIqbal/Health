package com.health.script.AST.Expressions;

import com.health.script.TokenReader;

public abstract class BinaryExpression extends NonAssignmentExpression {
    public static BinaryExpression parse(
            final TokenReader reader) {
        return UnaryExpression.parse(reader);
    }
}
