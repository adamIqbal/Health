package com.health.script;

import java.util.Objects;

/**
 * Represents a reader that reads from a script.
 *
 * @author Martijn
 */
public final class ScriptReader {
    private final Script script;
    private int position;

    /**
     * Creates a new {@link ScriptReader} that reads from the given
     * {@link Script}.
     *
     * @param script
     *            The script from which the {@link ScriptReader} should read.
     */
    public ScriptReader(final Script script) {
        Objects.requireNonNull(script);

        this.script = script;
    }

    /**
     * Gets the {@link Script} that this {@link ScriptReader} is reading.
     *
     * @return the {@link Script} that this {@link ScriptReader} is reading.
     */
    public Script getScript() {
        return this.script;
    }

    /**
     * Gets the position within the script.
     *
     * @return the position within the script.
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Gets the length of the script.
     *
     * @return the length of the script.
     */
    public int getLength() {
        return this.script.getText().length();
    }

    /**
     * Reads a single character from the script and advances the position by one
     * character.
     *
     * @return the next character from the script, or -1 if the end of the
     *         script has been reached.
     */
    public int read() {
        if (this.position == this.getLength()) {
            return -1;
        }

        return this.getScript().getText().charAt(this.position++);
    }

    /**
     * Reads a single character from the script without advancing the position.
     *
     * @return the next character from the script, or -1 if the end of the
     *         script has been reached.
     */
    public int peek() {
        if (this.position == this.getLength()) {
            return -1;
        }

        return this.getScript().getText().charAt(this.position);
    }

    /**
     * Reads a single character at a given offset from the script without
     * advancing the position.
     *
     * @param offset
     *            the offset at which to peek.
     * @return the character at the given offset in the script, or -1 if the end
     *         of the script has been reached.
     */
    public int peek(final int offset) {
        if (this.position + offset >= this.getLength()) {
            return -1;
        }

        return this.getScript().getText().charAt(this.position + offset);
    }

    /**
     * Advances the position within the script by the given amount, skipping the
     * characters.
     *
     * @param count
     *            the number of characters to skip.
     */
    public void skip(final int count) {
        if (this.position + count > this.getLength()) {
            this.position = this.getLength();
        } else {
            this.position += count;
        }
    }
}
