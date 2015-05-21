package com.health.script;

import java.util.List;

import com.health.script.AST.Program;

public class MyScriptParser implements Parser {
    @Override
    public Program parse(final List<Token> tokens) {
        TokenReader reader = new TokenReader(tokens);

        return Program.parse(reader);
    }
}
