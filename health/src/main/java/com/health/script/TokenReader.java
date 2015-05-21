package com.health.script;

import java.util.List;
import java.util.Stack;

public final class TokenReader {
    private final List<Token> tokens;
    private final Stack<Integer> marks;
    private int position;

    public TokenReader(final List<Token> tokens) {
        this.tokens = tokens;
        this.marks = new Stack<Integer>();
        this.position = 0;
    }

    public Token accept(TokenName name) {
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

    public Token accept(TokenType type) {
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

    public void mark() {
        this.marks.push(this.position);
    }

    public void pop() {
        // TODO: Exception
        if (marks.isEmpty()) {
            throw new IllegalStateException();
        }

        this.marks.pop();
    }

    public void revert() {
        // TODO: Exception
        if (marks.isEmpty()) {
            throw new IllegalStateException();
        }

        this.position = this.marks.pop();
    }
}
