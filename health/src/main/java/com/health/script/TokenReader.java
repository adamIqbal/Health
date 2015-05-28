package com.health.script;

import java.util.List;
import java.util.Stack;

/**
 * Represents a reader that reads from a list of tokens.
 *
 * @author Martijn
 */
public final class TokenReader {
    private final List<Token> tokens;
    private final Stack<Integer> marks;
    private int position;

    /**
     * Creates a new {@link TokenReader} that reads from the given list of
     * tokens.
     *
     * @param tokens
     *            The list of tokens from which the {@link TokenReader} should
     *            read.
     */
    public TokenReader(final List<Token> tokens) {
        this.tokens = tokens;
        this.marks = new Stack<Integer>();
        this.position = 0;
    }

    /**
     * Reads the next token from the list if its name matches the given name.
     *
     * @param name
     *            the name to read.
     * @return the next token from the list, or null if the next token's name
     *         does not match the given name.
     */
    public Token accept(final TokenName name) {
        if (this.position >= this.tokens.size()) {
            return null;
        }

        Token token = this.tokens.get(this.position);

        if (token.getName() == name) {
            this.position++;

            return token;
        }

        return null;
    }

    /**
     * Reads the next token from the list if its type matches the given type.
     *
     * @param type
     *            the type to read.
     * @return the next token from the list, or null if the next token's type
     *         does not match the given type.
     */
    public Token accept(final TokenType type) {
        if (this.position >= this.tokens.size()) {
            return null;
        }

        Token token = this.tokens.get(this.position);

        if (token.getType() == type) {
            this.position++;

            return token;
        }

        return null;
    }

    /**
     * Marks the position of the reader and pushes it onto a stack so that it
     * can be reverted to using {@link TokenReader#revert()} or popped off the
     * stack using {@link TokenReader#pop()}.
     */
    public void mark() {
        this.marks.push(this.position);
    }

    /**
     * Pops the position marked by the last call to {@link TokenReader#mark()}
     * off the stack.
     */
    public void pop() {
        if (marks.isEmpty()) {
            throw new IllegalStateException(
                    "Tried to pop when the stack was empty. "
                            + "Every call to mark() must have a matching call to either pop() or revert().");
        }

        this.marks.pop();
    }

    /**
     * Reverts to the position marked by the last call to
     * {@link TokenReader#mark()} and pops it off the stack.
     */
    public void revert() {
        if (marks.isEmpty()) {
            throw new IllegalStateException(
                    "Tried to revert when the stack was empty. "
                            + "Every call to mark() must have a matching call to either pop() or revert().");
        }

        this.position = this.marks.pop();
    }
}
