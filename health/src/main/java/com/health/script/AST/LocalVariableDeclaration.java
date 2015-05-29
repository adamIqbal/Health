package com.health.script.AST;

import com.health.script.TokenReader;

public final class LocalVariableDeclaration {
    public final LocalVariableType Type;
    public final LocalVariableDeclaratorList Declarators;

    private LocalVariableDeclaration(
            final LocalVariableType type,
            final LocalVariableDeclaratorList declarators) {
        this.Type = type;
        this.Declarators = declarators;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<LocalVariableDeclaration>");

        string.append(this.Type.toString());
        string.append(this.Declarators.toString());
        string.append("</LocalVariableDeclaration>");

        return string.toString();
    }

    public static LocalVariableDeclaration parse(final TokenReader reader) {
        reader.mark();

        LocalVariableType type = LocalVariableType.parse(reader);

        if (type == null) {
            reader.revert();
            return null;
        }

        LocalVariableDeclaratorList declarators =
                LocalVariableDeclaratorList.parse(reader);

        if (declarators == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new LocalVariableDeclaration(type, declarators);
    }
}
