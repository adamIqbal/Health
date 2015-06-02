package com.health.script.AST.Expressions;

import com.health.script.TokenReader;
import com.health.script.AST.AssignmentOperator;

public final class Assignment implements Expression, StatementExpression {
    public final UnaryExpression Left;
    public final AssignmentOperator Operator;
    public final Expression Right;

    private Assignment(final UnaryExpression unary,
            final AssignmentOperator operator,
            final Expression expression) {
        this.Left = unary;
        this.Operator = operator;
        this.Right = expression;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Assignment>");

        string.append(this.Left.toString());
        string.append(this.Operator.toString());
        string.append(this.Right.toString());
        string.append("</Assignment>");

        return string.toString();
    }

    public static Assignment parse(final TokenReader reader) {
        reader.mark();

        UnaryExpression left = UnaryExpression.parse(reader);

        if (left == null) {
            reader.revert();
            return null;
        }

        AssignmentOperator operator = AssignmentOperator.parse(reader);

        if (operator == null) {
            reader.revert();
            return null;
        }

        Expression right = Expression.parse(reader);

        if (right == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new Assignment(left, operator, right);
    }
}
