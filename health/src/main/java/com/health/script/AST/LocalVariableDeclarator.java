package com.health.script.AST;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;
import com.health.script.AST.Expressions.Identifier;

public final class LocalVariableDeclarator extends MemberDeclarator {
    public final Identifier Identifier;
    public final Token Equals;
    public final Expression Expression;

    private LocalVariableDeclarator(final Identifier identifier) {
        this(identifier, null, null);
    }

    private LocalVariableDeclarator(
            final Identifier identifier,
            final Token equals,
            final Expression expression) {
        this.Identifier = identifier;
        this.Equals = equals;
        this.Expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "<LocalVariableDeclarator>");

        string.append(this.Identifier.toString());

        if (this.Equals != null) {
            string.append(this.Equals.toString());
            string.append(this.Expression.toString());
        }

        string.append("</LocalVariableDeclarator>");

        return string.toString();
    }

    public static LocalVariableDeclarator parse(
            final TokenReader reader) {
        Identifier identifier = com.health.script.AST.Expressions.Identifier
                .parse(reader);

        if (identifier == null) {
            return null;
        }

        reader.mark();

        Token equals = reader.accept(TokenName.EQUALS);

        if (equals == null) {
            reader.revert();
            return new LocalVariableDeclarator(identifier);
        }

        Expression expression = com.health.script.AST.Expressions.Expression
                .parse(reader);

        if (expression == null) {
            // TODO: Generate diagnostic: expected expression
            reader.revert();
            return new LocalVariableDeclarator(identifier);
        }

        reader.pop();
        return new LocalVariableDeclarator(identifier, equals, expression);
    }
}
