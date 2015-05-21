package com.health.script.AST;

import com.health.Utils;
import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class AssignmentOperator {
    public final Token Operator;

    private AssignmentOperator(final Token operator) {
        this.Operator = operator;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Identifier>");

        string.append(this.Operator.toString());
        string.append("</Identifier>");

        return string.toString();
    }

    public static AssignmentOperator parse(final TokenReader reader) {
        Token operator = Utils.firstNonNull(
                () -> reader.accept(TokenName.EQUALS));

        if (operator != null) {
            return new AssignmentOperator(operator);
        }

        return null;
    }
}
