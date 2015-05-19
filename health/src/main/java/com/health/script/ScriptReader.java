package com.health.script;

import java.util.Objects;

public final class ScriptReader {
    Script script;
    int position;

    public ScriptReader(final Script script) {
        Objects.requireNonNull(script);

        this.script = script;
    }

    public Script getScript() {
        return this.script;
    }

    public int getPosition() {
        return this.position;
    }

    public int getLength() {
        return this.script.getText().length();
    }

    public int read() {
        if (this.position == this.getLength())
        {
            return -1;
        }

        return this.getScript().getText().charAt(this.position++);
    }

    public int peek() {
        if (this.position == this.getLength())
        {
            return -1;
        }

        return this.getScript().getText().charAt(this.position);
    }

    public int peek(int offset) {
        if (this.position + offset >= this.getLength())
        {
            return -1;
        }

        return this.getScript().getText().charAt(this.position + offset);
    }

    public void skip(int count) {
        if (this.position + count > this.getLength()) {
            this.position = this.getLength();
        }
        else {
            this.position += count;
        }
    }
}
