package com.health.script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a script.
 *
 * @author Martijn
 */
public final class Script {
    private final String text;
    private final List<Integer> lineStarts;

    /**
     * Creates a new script with the given text.
     *
     * @param text
     *            the text.
     */
    public Script(final String text) {
        Objects.requireNonNull(text);

        this.text = text;
        this.lineStarts = Script.findLineStarts(text);
    }

    /**
     * Gets the text of this script.
     *
     * @return the text of this script.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets the column for a given index in the text.
     *
     * @param index
     *            the index in the text for which to retrieve the column.
     * @return the column for the given index in the text.
     */
    public int getColumn(final int index) {
        if (index < 0 || index >= this.text.length()) {
            throw new IndexOutOfBoundsException();
        }

        int line = this.getLine(index);

        assert line > 0;
        assert line <= this.lineStarts.size();

        int lineStart = this.lineStarts.get(line - 1);

        assert index - lineStart + 1 > 0;
        return index - lineStart + 1;
    }

    /**
     * Gets the line for a given index in the text.
     *
     * @param index
     *            the index in the text for which to retrieve the line.
     * @return the line for the given index in the text.
     */
    public int getLine(final int index) {
        if (index < 0 || index >= this.text.length()) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = this.lineStarts.size() - 1; i >= 0; i--) {
            if (index >= this.lineStarts.get(i)) {
                return i + 1;
            }
        }

        // This should never happen
        assert false;
        return 1;
    }

    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Reads a script from a given file.
     *
     * @param path
     *            the path of the script file.
     * @return a script with the contents of the given file.
     * @throws IOException
     *             if an I/O error occurs reading from the file.
     */
    public static Script fromFile(final String path) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(path)));

        return new Script(text);
    }

    /**
     * Finds the indices in the text where a new line begins, for the getColumn
     * and getLine functions.
     *
     * @param text
     *            the text for which to find the indices of the line starts.
     * @return a list containing the indices in the text where a new line
     *         begins.
     */
    private static List<Integer> findLineStarts(final String text) {
        assert text != null;

        int length = text.length();
        List<Integer> lineStarts = new ArrayList<Integer>();

        lineStarts.add(0);

        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == '\n') {
                lineStarts.add(i + 1);
            }
        }

        return lineStarts;
    }
}
