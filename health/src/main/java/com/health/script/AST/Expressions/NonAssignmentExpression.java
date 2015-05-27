package com.health.script.AST.Expressions;

import com.health.Utils;
import com.health.script.TokenReader;
import com.health.script.AST.QueryExpressions.QueryExpression;

public interface NonAssignmentExpression extends Expression {
    public static NonAssignmentExpression parse(
            final TokenReader reader) {
        // TODO Auto-generated method stub
        return Utils.firstNonNull(
                () -> BinaryExpression.parse(reader),
                () -> QueryExpression.parse(reader));
    }
}
