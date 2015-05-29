package com.health.script.AST;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Expression;

public final class ArgumentList {
    public final ArgumentList Previous;
    public final Token Comma;
    public final Expression Argument;

    private ArgumentList(final Expression argument) {
        this(null, null, argument);
    }

    private ArgumentList(
            final ArgumentList previous,
            final Token comma,
            final Expression argument) {
        this.Previous = previous;
        this.Comma = comma;
        this.Argument = argument;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<ArgumentList>");

        if (this.Previous != null) {
            string.append(this.Previous.toString());
            string.append(this.Comma.toString());
        }

        string.append(this.Argument.toString());
        string.append("</ArgumentList>");

        return string.toString();
    }

    public static ArgumentList parse(final TokenReader reader) {
        reader.mark();

        Expression first = Expression.parse(reader);

        if (first == null) {
            reader.revert();
            return null;
        }

        ArgumentList list = new ArgumentList(first);

        while (true) {
            reader.mark();

            Token comma = reader.accept(TokenName.COMMA);

            if (comma == null) {
                reader.revert();
                break;
            }

            Expression next = Expression.parse(reader);

            if (next == null) {
                reader.revert();
                break;
            }

            reader.pop();
            list = new ArgumentList(list, comma, next);
        }

        reader.pop();
        return list;
    }
}
