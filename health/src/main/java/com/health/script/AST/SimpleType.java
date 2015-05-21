package com.health.script.AST;

import com.health.script.Token;
import com.health.script.TokenReader;
import com.health.script.TokenType;

public final class SimpleType extends Type {
    public final Token Type;

    private SimpleType(final Token type) {
        this.Type = type;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<SimpleType>");

        string.append(this.Type.toString());
        string.append("</SimpleType>");

        return string.toString();
    }

    public static SimpleType parse(final TokenReader reader) {
        Token type = reader.accept(TokenType.TYPE);

        if (type != null) {
            return new SimpleType(type);
        }

        return null;
    }
}
