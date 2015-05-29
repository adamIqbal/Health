package com.health.script.AST.Expressions;

import com.health.script.Context;
import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.Value;

public final class NumberLiteral implements Literal {
    public final Token Literal;

    private NumberLiteral(final Token literal) {
        this.Literal = literal;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<NumberLiteral>");

        string.append(this.Literal.toString());
        string.append("</NumberLiteral>");

        return string.toString();
    }

    public static NumberLiteral parse(final TokenReader reader) {
        Token literal = reader.accept(TokenType.NUMBER_LITERAL);

        if (literal != null) {
            return new NumberLiteral(literal);
        }

        return null;
    }

    public Value interpret(Context context) {
        NumberValue number = new NumberValue();

        number.setValue((double) this.Literal.getValue());

        return number;
    }
}
