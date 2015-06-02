package com.health.script.AST;

import com.health.Utils;
import com.health.script.TokenReader;

public abstract class Type extends LocalVariableType {
    public static Type parse(final TokenReader reader) {
        return Utils.firstNonNull(
                () -> SimpleType.parse(reader),
                () -> TypeName.parse(reader));
    }
}
