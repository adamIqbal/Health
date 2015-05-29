package com.health.script.AST;

import com.health.script.TokenReader;
import com.health.script.AST.Expressions.Identifier;

public final class TypeName extends Type {
    public final Identifier Name;

    private TypeName(final Identifier name) {
        this.Name = name;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<TypeName>");

        string.append(this.Name.toString());
        string.append("</TypeName>");

        return string.toString();
    }

    public static TypeName parse(final TokenReader reader) {
        Identifier name = Identifier.parse(reader);

        if (name != null) {
            return new TypeName(name);
        }

        return null;
    }
}
