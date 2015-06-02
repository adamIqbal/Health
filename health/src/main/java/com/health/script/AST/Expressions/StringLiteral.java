package com.health.script.AST.Expressions;

import com.health.script.Context;
import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

public final class StringLiteral implements Literal {
    public final Token Literal;

    private StringLiteral(final Token literal) {
        this.Literal = literal;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<StringLiteral>");

        string.append(this.Literal.toString());
        string.append("</StringLiteral>");

        return string.toString();
    }

    public static StringLiteral parse(final TokenReader reader) {
        Token literal = reader.accept(TokenType.STRING_LITERAL);

        if (literal != null) {
            return new StringLiteral(literal);
        }

        return null;
    }

    public Value interpret(Context context) {
        StringValue string = new StringValue();

        string.setValue((String) this.Literal.getValue());

        return string;
    }
}
