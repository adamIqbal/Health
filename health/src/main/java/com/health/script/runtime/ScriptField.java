package com.health.script.runtime;

import java.util.Objects;

/**
 * Represents a field on a script type.
 */
public final class ScriptField extends ScriptMember {
    private final ScriptType type;

    /**
     * Creates a new field with the given name and type.
     *
     * @param name
     *            the name of the field.
     * @param type
     *            the type of the field.
     */
    public ScriptField(final String name, final ScriptType type) {
        super(name);

        Objects.requireNonNull(type);

        this.type = type;
    }

    /**
     * Gets the type of this field.
     *
     * @return the type of this field.
     */
    public ScriptType getType() {
        return this.type;
    }
}
