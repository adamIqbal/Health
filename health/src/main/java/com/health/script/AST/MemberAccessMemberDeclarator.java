package com.health.script.AST;

import com.health.script.TokenReader;
import com.health.script.AST.Expressions.MemberAccess;

public final class MemberAccessMemberDeclarator extends MemberDeclarator {
    public final MemberAccess MemberAccess;

    private MemberAccessMemberDeclarator(final MemberAccess memberAccess) {
        this.MemberAccess = memberAccess;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "<MemberAccessMemberDeclarator>");

        string.append(this.MemberAccess.toString());
        string.append("</MemberAccessMemberDeclarator>");

        return string.toString();
    }

    public static MemberAccessMemberDeclarator parse(
            final TokenReader reader) {
        MemberAccess memberAccess = com.health.script.AST.Expressions
                .MemberAccess.parse(reader);

        if (memberAccess != null) {
            return new MemberAccessMemberDeclarator(memberAccess);
        }

        return null;
    }
}
