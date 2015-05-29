package com.health.script.AST.Statements;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.LocalVariableDeclaration;

public class DeclarationStatement implements Statement {
    public final LocalVariableDeclaration Declaration;
    public final Token Semicolon;

    public DeclarationStatement(LocalVariableDeclaration declaration,
            Token semicolon) {
        this.Declaration = declaration;
        this.Semicolon = semicolon;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<DeclarationStatement>");

        string.append(this.Declaration.toString());
        string.append(this.Semicolon.toString());
        string.append("</DeclarationStatement>");

        return string.toString();
    }

    public static DeclarationStatement parse(
            final TokenReader reader) {
        reader.mark();

        LocalVariableDeclaration declaration =
                LocalVariableDeclaration.parse(reader);

        if (declaration == null) {
            reader.revert();

            return null;
        }

        Token semicolon = reader.accept(TokenName.SEMICOLON);

        if (semicolon == null) {
            reader.revert();

            return null;
        }

        reader.pop();

        return new DeclarationStatement(declaration, semicolon);
    }
}
