package com.health.script.AST.Expressions;

import com.health.script.Context;
import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;
import com.health.script.runtime.NullValue;
import com.health.script.runtime.Value;

public final class NullLiteral implements Literal {
    public final Token Literal;

    private NullLiteral(final Token literal) {
        this.Literal = literal;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<NullLiteral>");

        string.append(this.Literal.toString());
        string.append("</NullLiteral>");

        return string.toString();
    }

    public static NullLiteral parse(final TokenReader reader) {
        Token literal = reader.accept(TokenType.NULL_LITERAL);

        if (literal != null) {
            return new NullLiteral(literal);
        }

        return null;
    }

    public Value interpret(Context context) {
        return new NullValue();
    }
}
