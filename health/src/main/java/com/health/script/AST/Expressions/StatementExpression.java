package com.health.script.AST.Expressions;

import com.health.Utils;
import com.health.script.TokenReader;

public interface StatementExpression {
    public static StatementExpression parse(
            final TokenReader reader) {
        return Utils.firstNonNull(
                () -> InvocationExpression.parse(reader),
                () -> Assignment.parse(reader));
    }
}
