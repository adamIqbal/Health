package com.health.script.AST;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class LocalVariableDeclaratorList {
    public final LocalVariableDeclaratorList Previous;
    public final Token Comma;
    public final LocalVariableDeclarator Declarator;

    private LocalVariableDeclaratorList(
            final LocalVariableDeclarator declarator) {
        this(null, null, declarator);
    }

    private LocalVariableDeclaratorList(
            final LocalVariableDeclaratorList previous,
            final Token comma,
            final LocalVariableDeclarator declarator) {
        this.Previous = previous;
        this.Comma = comma;
        this.Declarator = declarator;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "<LocalVariableDeclaratorList>");

        if (this.Previous != null) {
            string.append(this.Previous.toString());
            string.append(this.Comma.toString());
        }

        string.append(this.Declarator.toString());
        string.append("</LocalVariableDeclaratorList>");

        return string.toString();
    }

    public static LocalVariableDeclaratorList parse(
            final TokenReader reader) {
        LocalVariableDeclarator first = LocalVariableDeclarator.parse(reader);

        if (first == null) {
            return null;
        }

        LocalVariableDeclaratorList list = new LocalVariableDeclaratorList(
                first);

        while (true) {
            reader.mark();

            Token comma = reader.accept(TokenName.COMMA);

            if (comma == null) {
                reader.revert();
                break;
            }

            LocalVariableDeclarator next = LocalVariableDeclarator
                    .parse(reader);

            if (next == null) {
                reader.revert();
                break;
            }

            reader.pop();
            list = new LocalVariableDeclaratorList(list, comma, next);
        }

        return list;
    }
}
