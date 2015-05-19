package com.health.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Script {
    private final String text;
    private final List<Integer> lineStarts;

    public Script(final String text) {
        Objects.requireNonNull(text);

        this.text = text;
        this.lineStarts = Script.findLineStarts(text);
    }

    public String getText() {
        return this.text;
    }

    public int getColumn(int index) {
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

    public int getLine(int index) {
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

    public static Script fromFile(final String path) {
        return new Script("");
    }

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
