package com.health.script.AST;

import java.util.ArrayList;
import java.util.List;

import com.health.script.TokenReader;
import com.health.script.AST.Statements.Statement;

public final class Program {
    public final List<Statement> Statements;

    private Program(final List<Statement> statements) {
        this.Statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<Program>\n");

        for (Statement stmt : this.Statements) {
            string.append(stmt.toString());
            string.append('\n');
        }

        string.append("</Program>");

        return string.toString();
    }

    public static Program parse(final TokenReader reader) {
        List<Statement> statements = new ArrayList<Statement>();

        while (true) {
            Statement statement = Statement.parse(reader);

            if (statement == null) {
                break;
            }

            statements.add(statement);
        }

        return new Program(statements);
    }
}
