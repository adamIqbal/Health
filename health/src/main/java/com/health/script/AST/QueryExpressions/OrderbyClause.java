package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class OrderbyClause extends QueryBodyClause {
    public final Token Orderby;
    public final OrderingList Orderings;

    public OrderbyClause(
            final Token orderby,
            final OrderingList orderings) {
        this.Orderby = orderby;
        this.Orderings = orderings;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<OrderbyClause>");

        string.append(this.Orderby.toString());
        string.append(this.Orderings.toString());
        string.append("</OrderbyClause>");

        return string.toString();
    }

    public static OrderbyClause parse(final TokenReader reader) {
        reader.mark();

        Token orderby = reader.accept(TokenName.ORDERBY);

        if (orderby == null) {
            reader.revert();
            return null;
        }

        OrderingList orderings = OrderingList.parse(reader);

        if (orderings == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new OrderbyClause(orderby, orderings);
    }
}
