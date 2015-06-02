package com.health.script.AST;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;

public final class VarType extends LocalVariableType {
    public final Token Var;

    private VarType(final Token var) {
        this.Var = var;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Var>");

        string.append(this.Var.toString());
        string.append("</Var>");

        return string.toString();
    }

    public static VarType parse(final TokenReader reader) {
        Token var = reader.accept(TokenName.VAR);

        if (var != null) {
            return new VarType(var);
        }

        return null;
    }
}
