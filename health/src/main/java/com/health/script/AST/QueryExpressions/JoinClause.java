package com.health.script.AST.QueryExpressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.Type;
import com.health.script.AST.TypeName;
import com.health.script.AST.Expressions.Expression;
import com.health.script.AST.Expressions.Identifier;

public final class JoinClause extends QueryBodyClause {
    public final Token Join;
    public final Type RangeType;
    public final Identifier RangeIdentifier;
    public final Token In;
    public final Expression Source;
    public final Token On;
    public final Expression Left;
    public final Token Equals;
    public final Expression Right;
    public final Token Into;
    public final Identifier TempName;

    private JoinClause(
            final Token join,
            final Type rangeType,
            final Identifier rangeIdentifier,
            final Token in,
            final Expression source,
            final Token on,
            final Expression left,
            final Token equals,
            final Expression right) {
        this(join, rangeType, rangeIdentifier, in, source, on, left, equals, right, null, null);
    }

    private JoinClause(
            final Token join,
            final Type rangeType,
            final Identifier rangeIdentifier,
            final Token in,
            final Expression source,
            final Token on,
            final Expression left,
            final Token equals,
            final Expression right,
            final Token into,
            final Identifier tempName) {
        this.Join = join;
        this.RangeType = rangeType;
        this.RangeIdentifier = rangeIdentifier;
        this.In = in;
        this.Source = source;
        this.On = on;
        this.Left = left;
        this.Equals = equals;
        this.Right = right;
        this.Into = into;
        this.TempName = tempName;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<JoinClause>");

        string.append(this.Join.toString());

        if (this.RangeType != null) {
            string.append(this.RangeType.toString());
        }

        string.append(this.RangeIdentifier.toString());
        string.append(this.In.toString());
        string.append(this.Source.toString());
        string.append(this.On.toString());
        string.append(this.Left.toString());
        string.append(this.Equals.toString());
        string.append(this.Right.toString());

        if (this.Into != null) {
            string.append(this.Into.toString());
            string.append(this.TempName.toString());
        }

        string.append("</JoinClause>");

        return string.toString();
    }

    public static JoinClause parse(final TokenReader reader) {
        reader.mark();

        Token join = reader.accept(TokenName.JOIN);

        if (join == null) {
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

        Token on = reader.accept(TokenName.ON);

        if (on == null) {
            reader.revert();
            return null;
        }

        Expression left = Expression.parse(reader);

        if (left == null) {
            reader.revert();
            return null;
        }

        Token equals = reader.accept(TokenName.EQUALS_EQUALS);

        if (equals == null) {
            reader.revert();
            return null;
        }

        Expression right = Expression.parse(reader);

        if (right == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        reader.mark();

        Token into = reader.accept(TokenName.INTO);

        if (into == null) {
            reader.revert();
            return new JoinClause(join, rangeType, rangeIdentifier, in, source, on, left, equals, right);
        }

        Identifier tempName = Identifier.parse(reader);

        if (tempName == null) {
            reader.revert();
            return new JoinClause(join, rangeType, rangeIdentifier, in, source, on, left, equals, right);
        }

        reader.pop();
        return new JoinClause(join, rangeType, rangeIdentifier, in, source, on, left, equals, right, into, tempName);
    }
}
