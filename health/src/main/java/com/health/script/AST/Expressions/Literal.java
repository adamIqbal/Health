package com.health.script.AST.Expressions;

import com.health.Utils;
import com.health.script.TokenReader;

public interface Literal extends PrimaryExpression {
    public static Literal parse(final TokenReader reader) {
        return Utils.firstNonNull(
                () -> NullLiteral.parse(reader),
                () -> BooleanLiteral.parse(reader),
                () -> NumberLiteral.parse(reader),
                () -> StringLiteral.parse(reader));
    }
}
