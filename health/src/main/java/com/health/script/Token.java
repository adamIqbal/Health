package com.health.script;

import java.util.Objects;

/**
 * TODO.
 *
 * @author Martijn
 */
public final class Token {
    private Script script;
    private int start;
    private int end;
    private TokenName name;
    private Object value;

    /**
     * TODO.
     *
     * @param script
     *            TODO.
     * @param start
     *            TODO.
     * @param end
     *            TODO.
     * @param name
     *            TODO.
     */
    public Token(final Script script, final int start, final int end,
            final TokenName name) {
        this(script, start, end, name, null);
    }

    /**
     * TODO.
     *
     * @param script
     *            TODO.
     * @param start
     *            TODO.
     * @param end
     *            TODO.
     * @param name
     *            TODO.
     * @param value
     *            TODO.
     */
    public Token(final Script script, final int start, final int end,
            final TokenName name, final Object value) {
        Objects.requireNonNull(script, "Argument script cannot be null.");
        Objects.requireNonNull(name, "Argument name cannot be null.");

        if (start < 0) {
            throw new IllegalArgumentException(
                    "Argument start must be greater than or equal to zero.");
        }

        if (start >= script.getText().length()) {
            throw new IllegalArgumentException(
                    "Argument start must be smaller than than "
                            + "script.getText().getLength().");
        }

        if (end < start) {
            throw new IllegalArgumentException(
                    "Argument end must be greater than or equal to start.");
        }

        if (end > script.getText().length()) {
            throw new IllegalArgumentException(
                    "Argument end must be smaller than or equal to "
                            + "script.getText().getLength().");
        }

        this.script = script;
        this.start = start;
        this.end = end;
        this.name = name;
        this.value = value;
    }

    public Script getScript() {
        return this.script;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public TokenName getName() {
        return this.name;
    }

    public TokenType getType() {
        return this.getName().getType();
    }

    public Object getValue() {
        return this.value;
    }

    public int getLength() {
        return this.end - this.start;
    }

    public String getLexeme() {
        String lexeme = this.name.getLexeme();

        if (lexeme == null) {
            lexeme = this.script.getText().substring(this.start, this.end);
        }

        return lexeme;
    }

    public int getColumn() {
        return this.script.getColumn(this.start);
    }

    public int getLine() {
        return this.script.getLine(this.start);
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
