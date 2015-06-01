package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class OrderingList {
    public final OrderingList Previous;
    public final Token Comma;
    public final Ordering Ordering;

    private OrderingList(final Ordering ordering) {
        this(null, null, ordering);
    }

    private OrderingList(
            final OrderingList previous,
            final Token comma,
            final Ordering ordering) {
        this.Previous = previous;
        this.Comma = comma;
        this.Ordering = ordering;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "<OrderingList>");

        if (this.Previous != null) {
            string.append(this.Previous.toString());
            string.append(this.Comma.toString());
        }

        string.append(this.Ordering.toString());
        string.append("</OrderingList>");

        return string.toString();
    }

    public static OrderingList parse(
            final TokenReader reader) {
        Ordering first = com.health.script.AST.QueryExpressions.Ordering.parse(reader);

        if (first == null) {
            return null;
        }

        OrderingList list = new OrderingList(first);

        while (true) {
            reader.mark();

            Token comma = reader.accept(TokenName.COMMA);

            if (comma == null) {
                reader.revert();
                break;
            }

            Ordering next = com.health.script.AST.QueryExpressions.Ordering.parse(reader);

            if (next == null) {
                reader.revert();
                break;
            }

            reader.pop();
            list = new OrderingList(list, comma, next);
        }

        return list;
    }
}
