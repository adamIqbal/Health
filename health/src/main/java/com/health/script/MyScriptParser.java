package com.health.script;

import java.util.List;

import com.health.script.AST.Program;

/**
 * Implements a parser for the script.
 *
 * @author Martijn
 */
public final class MyScriptParser implements Parser {
    /**
     * {@inheritDoc}
     */
    @Override
    public Program parse(final List<Token> tokens) {
        TokenReader reader = new TokenReader(tokens);

        return Program.parse(reader);
    }
}
