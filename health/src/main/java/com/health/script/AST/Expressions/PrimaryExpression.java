package com.health.script.AST.Expressions;

import com.health.Utils;
import com.health.script.TokenReader;

public interface PrimaryExpression extends UnaryExpression {
    public static PrimaryExpression parse(final TokenReader reader) {
        PrimaryExpression expr = Utils.firstNonNull(
                () -> Literal.parse(reader),
                () -> ParenthesizedExpression.parse(reader),
                () -> AnonymousObjectCreationExpression.parse(reader),
                () -> Identifier.parse(reader));

        if (expr != null) {
            while (true) {
                PrimaryExpression outer = MemberAccess.parse(expr, reader);

                if (outer != null) {
                    expr = outer;
                    continue;
                }

                outer = InvocationExpression.parse(expr, reader);

                if (outer != null) {
                    expr = outer;
                    continue;
                }

                break;
            }
        }

        return expr;
    }
}
