package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Type;
import com.health.script.AST.TypeName;
import com.health.script.AST.Expressions.Expression;
import com.health.script.AST.Expressions.Identifier;

public final class FromClause extends QueryBodyClause {
    public final Token From;
    public final Type RangeType;
    public final Identifier RangeIdentifier;
    public final Token In;
    public final Expression Source;

    private FromClause(
            final Token from,
            final Identifier rangeIdentifier,
            final Token in,
            final Expression source) {
        this(from, null, rangeIdentifier, in, source);
    }

    private FromClause(
            final Token from,
            final Type rangeType,
            final Identifier rangeIdentifier,
            final Token in,
            final Expression source) {
        this.From = from;
        this.RangeType = rangeType;
        this.RangeIdentifier = rangeIdentifier;
        this.In = in;
        this.Source = source;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<FromClause>");

        string.append(this.From.toString());

        if (this.RangeType != null) {
            string.append(this.RangeType.toString());
        }

        string.append(this.RangeIdentifier.toString());
        string.append(this.In.toString());
        string.append(this.Source.toString());
        string.append("</FromClause>");

        return string.toString();
    }

    public static FromClause parse(final TokenReader reader) {
        reader.mark();

        Token from = reader.accept(TokenName.FROM);

        if (from == null) {
            reader.revert();
            return null;
        }

        Type rangeType = com.health.script.AST.Type.parse(reader);

        Identifier rangeIdentifier = Identifier.parse(reader);

        if (rangeIdentifier == null) {
            if (rangeType instanceof TypeName) {
                rangeIdentifier = ((TypeName) rangeType).Name;
            }
            else {
                reader.revert();
                return null;
            }
        }

        Token in = reader.accept(TokenName.IN);

        if (in == null) {
            reader.revert();
            return null;
        }

        Expression source = Expression.parse(reader);

        if (source == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new FromClause(from, rangeType, rangeIdentifier, in, source);
    }
}
