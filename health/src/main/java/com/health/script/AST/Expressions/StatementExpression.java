package com.health.script.AST.Expressions;

import com.health.Utils;
import com.health.script.TokenReader;

public interface StatementExpression extends Expression {
    public static StatementExpression parse(
            final TokenReader reader) {
        return Utils.firstNonNull(
                () -> InvocationExpression.parse(reader),
                () -> Assignment.parse(reader));
    }
}
