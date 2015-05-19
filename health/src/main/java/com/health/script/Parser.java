package com.health.script;

import java.util.List;

import com.health.script.AST.Program;

/**
 * TODO.
 *
 * @author Martijn
 *
 */
public interface Parser {
    /**
     * TODO.
     *
     * @param tokens
     *            asdf.
     * @return asdg.
     */
    Program parse(List<Token> tokens);
}
