package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class MemberAccess implements PrimaryExpression {
    public final PrimaryExpression Expression;
    public final Token Period;
    public final Identifier Identifier;

    private MemberAccess(
            final PrimaryExpression expression,
            final Token period,
            final Identifier identifier) {
        this.Expression = expression;
        this.Period = period;
        this.Identifier = identifier;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<MemberAccess>");

        string.append(this.Expression.toString());
        string.append(this.Period.toString());
        string.append(this.Identifier.toString());
        string.append("</MemberAccess>");

        return string.toString();
    }

    public static MemberAccess parse(final TokenReader reader) {
        reader.mark();
        PrimaryExpression expression = PrimaryExpression.parse(reader);

        if (expression instanceof MemberAccess) {
            reader.pop();
            return (MemberAccess) expression;
        }

        reader.revert();
        return null;
    }

    public static MemberAccess parse(PrimaryExpression expression, final TokenReader reader) {
        MemberAccess memberAccess = null;

        if (expression == null) {
            return null;
        }

        while (true) {
            reader.mark();

            Token period = reader.accept(TokenName.PERIOD);

            if (period == null) {
                reader.revert();
                break;
            }

            Identifier identifier = com.health.script.AST.Expressions.Identifier
                    .parse(reader);

            if (identifier == null) {
                reader.revert();
                break;
            }

            reader.pop();
            memberAccess = new MemberAccess(expression, period, identifier);

            expression = memberAccess;
        }

        if (memberAccess == null) {
            return null;
        }

        return memberAccess;
    }
}
