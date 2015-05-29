package com.health.script.AST.Expressions;

import com.health.script.Context;
import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;
import com.health.script.runtime.BooleanValue;
import com.health.script.runtime.Value;

public final class BooleanLiteral implements Literal {
    public final Token Literal;

    private BooleanLiteral(final Token literal) {
        this.Literal = literal;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<BooleanLiteral>");

        string.append(this.Literal.toString());
        string.append("</BooleanLiteral>");

        return string.toString();
    }

    public static BooleanLiteral parse(final TokenReader reader) {
        Token literal = reader.accept(TokenType.BOOL_LITERAL);

        if (literal != null) {
            return new BooleanLiteral(literal);
        }

        return null;
    }

    public Value interpret(Context context) {
        BooleanValue bool = new BooleanValue();

        bool.setValue((boolean) this.Literal.getValue());

        return bool;
    }
}
