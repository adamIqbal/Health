package com.health.script.AST;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class MemberDeclaratorList {
    public final MemberDeclaratorList Previous;
    public final Token Comma;
    public final MemberDeclarator Declarator;

    private MemberDeclaratorList(final MemberDeclarator declarator) {
        this(null, null, declarator);
    }

    private MemberDeclaratorList(
            final MemberDeclaratorList previous,
            final Token comma,
            final MemberDeclarator declarator) {
        this.Previous = previous;
        this.Comma = comma;
        this.Declarator = declarator;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "<MemberDeclaratorList>");

        if (this.Previous != null) {
            string.append(this.Previous.toString());
            string.append(this.Comma.toString());
        }

        string.append(this.Declarator.toString());
        string.append("</MemberDeclaratorList>");

        return string.toString();
    }

    public static MemberDeclaratorList parse(
            final TokenReader reader) {
        MemberDeclarator first = MemberDeclarator.parse(reader);

        if (first == null) {
            return null;
        }

        MemberDeclaratorList list = new MemberDeclaratorList(
                first);

        while (true) {
            reader.mark();

            Token comma = reader.accept(TokenName.COMMA);

            if (comma == null) {
                reader.revert();
                break;
            }

            MemberDeclarator next = MemberDeclarator.parse(reader);

            if (next == null) {
                reader.revert();
                break;
            }

            reader.pop();
            list = new MemberDeclaratorList(list, comma, next);
        }

        return list;
    }
}
