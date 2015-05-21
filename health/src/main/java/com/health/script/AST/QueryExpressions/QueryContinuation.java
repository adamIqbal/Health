package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Identifier;

public final class QueryContinuation {
    public final Token Into;
    public final Identifier Identifier;
    public final QueryBody QueryBody;

    private QueryContinuation(
            final Token into,
            final Identifier identifier,
            final QueryBody queryBody) {
        this.Into = into;
        this.Identifier = identifier;
        this.QueryBody = queryBody;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<QueryContinuation>");

        string.append(this.Into.toString());
        string.append(this.Identifier.toString());
        string.append(this.QueryBody.toString());
        string.append("</QueryContinuation>");

        return string.toString();
    }

    public static QueryContinuation parse(final TokenReader reader) {
        reader.mark();

        Token into = reader.accept(TokenName.INTO);

        if (into == null) {
            reader.revert();
            return null;
        }

        Identifier identifier = com.health.script.AST.Expressions.Identifier.parse(reader);

        if (identifier == null) {
            reader.revert();
            return null;
        }

        QueryBody queryBody = com.health.script.AST.QueryExpressions.QueryBody.parse(reader);

        if (queryBody == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new QueryContinuation(into, identifier, queryBody);
    }
}
