package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;

public final class Literal extends PrimaryExpression {
    public final Token Literal;

    private Literal(final Token literal) {
        this.Literal = literal;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Literal>");

        string.append(this.Literal.toString());
        string.append("</Literal>");

        return string.toString();
    }

    public static Literal parse(final TokenReader reader) {
        Token literal = reader.accept(TokenType.LITERAL);

        if (literal != null) {
            return new Literal(literal);
        }

        return null;
    }
}
