package com.health.script;

import java.util.Objects;

/**
 * Represents a token.
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
     * Creates a new token with the given script, start, end and name.
     *
     * @param script
     *            The script that this token belongs to.
     * @param start
     *            The start index of this token within the script.
     * @param end
     *            The end index of this token within the script.
     * @param name
     *            The name of this token.
     */
    public Token(final Script script, final int start, final int end,
            final TokenName name) {
        this(script, start, end, name, null);
    }

    /**
     * Creates a new token with the given script, start, end, name and value.
     *
     * @param script
     *            The script that this token belongs to.
     * @param start
     *            The start index of this token within the script.
     * @param end
     *            The end index of this token within the script.
     * @param name
     *            The name of this token.
     * @param value
     *            The value of the token.
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

    /**
     * Gets the script that this token belongs to.
     *
     * @return the script that this token belongs to.
     */
    public Script getScript() {
        return this.script;
    }

    /**
     * Gets the start index of this token within the script.
     *
     * @return the start index of this token within the script.
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Gets the end index of this token within the script.
     *
     * @return the end index of this token within the script.
     */
    public int getEnd() {
        return this.end;
    }

    /**
     * Gets name of this token.
     *
     * @return the name of this token.
     */
    public TokenName getName() {
        return this.name;
    }

    /**
     * Gets type of this token.
     *
     * @return the type of this token.
     */
    public TokenType getType() {
        return this.getName().getType();
    }

    /**
     * Gets value of this token.
     *
     * @return the value of this token.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Gets length of this token.
     *
     * @return the length of this token.
     */
    public int getLength() {
        return this.end - this.start;
    }

    /**
     * Gets lexeme of this token.
     *
     * @return the lexeme of this token.
     */
    public String getLexeme() {
        String lexeme = this.name.getLexeme();

        if (lexeme == null) {
            lexeme = this.script.getText().substring(this.start, this.end);
        }

        return lexeme;
    }

    /**
     * Gets column at which this token starts.
     *
     * @return the column at which this token starts.
     */
    public int getColumn() {
        return this.script.getColumn(this.start);
    }

    /**
     * Gets line at which this token starts.
     *
     * @return the line at which this token starts.
     */
    public int getLine() {
        return this.script.getLine(this.start);
    }

    /**
     * Sets the value of this token.
     *
     * @param value
     *            the value for this token.
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("<");
        string.append(this.name.toString());
        string.append('>');
        string.append(this.getLexeme());
        string.append("</");
        string.append(this.name.toString());
        string.append('>');

        return string.toString();
    }
}
