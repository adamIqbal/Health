package com.health.script;

import java.util.List;

import com.health.script.AST.Program;

/**
 * Interface for a parser of a script.
 */
public interface Parser {
    /**
     * Parses the given list of tokens into a {@link Program}.
     *
     * @param tokens
     *            the list of tokens to be parsed.
     * @return the parsed {@link Program}; or null if the tokens do not
     *         represent a valid program.
     */
    Program parse(List<Token> tokens);
}
