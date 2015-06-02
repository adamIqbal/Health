package com.health.script.AST;

import com.health.Utils;
import com.health.script.TokenReader;

public abstract class MemberDeclarator {
    public static MemberDeclarator parse(
            final TokenReader reader) {
        return Utils.firstNonNull(
                () -> MemberAccessMemberDeclarator.parse(reader),
                () -> LocalVariableDeclarator.parse(reader));
    }
}
