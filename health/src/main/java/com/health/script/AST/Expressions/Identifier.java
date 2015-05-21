package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;

public final class Identifier extends PrimaryExpression {
    public final Token Identifier;

    private Identifier(final Token identifier) {
        this.Identifier = identifier;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Identifier>");

        string.append(this.Identifier.toString());
        string.append("</Identifier>");

        return string.toString();
    }

    public static Identifier parse(final TokenReader reader) {
        Token identifier = reader.accept(TokenType.IDENTIFIER);

        if (identifier != null) {
            return new Identifier(identifier);
        }

        return null;
    }
}
