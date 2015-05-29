package com.health.script.AST;

import com.health.Utils;
import com.health.script.TokenReader;

public abstract class LocalVariableType {
    public static LocalVariableType parse(
            final TokenReader reader) {
        return Utils.firstNonNull(
                () -> VarType.parse(reader),
                () -> Type.parse(reader));
    }
}
