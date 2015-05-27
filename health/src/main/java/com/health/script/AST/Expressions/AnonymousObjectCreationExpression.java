package com.health.script.AST.Expressions;

import com.health.script.Token;
import com.health.script.TokenName;
import com.health.script.TokenReader;
import com.health.script.AST.MemberDeclaratorList;

public final class AnonymousObjectCreationExpression implements PrimaryExpression {
    public final Token New;
    public final Token OpenBrace;
    public final MemberDeclaratorList MemberDeclarators;
    public final Token Comma;
    public final Token CloseBrace;

    private AnonymousObjectCreationExpression(
            final Token newKeyword,
            final Token openBrace,
            final Token closeBrace) {
        this(newKeyword, openBrace, null, null, closeBrace);
    }

    private AnonymousObjectCreationExpression(
            final Token newKeyword,
            final Token openBrace,
            final MemberDeclaratorList declarators,
            final Token closeBrace) {
        this(newKeyword, openBrace, declarators, null, closeBrace);
    }

    private AnonymousObjectCreationExpression(
            final Token newKeyword,
            final Token openBrace,
            final MemberDeclaratorList declarators,
            final Token comma,
            final Token closeBrace) {
        this.New = newKeyword;
        this.OpenBrace = openBrace;
        this.MemberDeclarators = declarators;
        this.Comma = comma;
        this.CloseBrace = closeBrace;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "<AnonymousObjectCreationExpression>");

        string.append(this.New.toString());
        string.append(this.OpenBrace.toString());

        if (this.MemberDeclarators != null) {
            string.append(this.MemberDeclarators.toString());

            if (this.Comma != null) {
                string.append(this.Comma.toString());
            }
        }

        string.append(this.CloseBrace.toString());
        string.append("</AnonymousObjectCreationExpression>");

        return string.toString();
    }

    public static AnonymousObjectCreationExpression parse(
            final TokenReader reader) {
        reader.mark();

        Token newKeyword = reader.accept(TokenName.NEW);

        if (newKeyword == null) {
            reader.revert();
            return null;
        }

        Token openBrace = reader.accept(TokenName.OPEN_BRACE);

        if (openBrace == null) {
            reader.revert();
            return null;
        }

        MemberDeclaratorList declarators = MemberDeclaratorList.parse(reader);
        Token comma = null;

        if (declarators != null) {
            comma = reader.accept(TokenName.COMMA);
        }

        Token closeBrace = reader.accept(TokenName.CLOSE_BRACE);

        if (closeBrace == null) {
            reader.revert();
            return null;
        }

        reader.pop();
        return new AnonymousObjectCreationExpression(newKeyword, openBrace,
                declarators, comma, closeBrace);
    }
}
