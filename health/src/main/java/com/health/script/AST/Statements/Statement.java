package com.health.script.AST.Statements;

import com.health.Utils;
import com.health.script.TokenReader;

public interface Statement {
    public static Statement parse(final TokenReader reader) {
        return Utils.firstNonNull(
                () -> DeclarationStatement.parse(reader),
                () -> ExpressionStatement.parse(reader));
    }
}
