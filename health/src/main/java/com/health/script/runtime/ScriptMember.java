package com.health.script.runtime;

import java.util.Objects;

/**
 * Represents a member on a script type.
 */
public abstract class ScriptMember {
    private final String name;

    /**
     * Creates a member with the given name.
     *
     * @param name
     *            the name of the member.
     */
    protected ScriptMember(final String name) {
        Objects.requireNonNull(name);

        this.name = name;
    }

    /**
     * Gets the name of this member.
     *
     * @return the name of this member.
     */
    public final String getName() {
        return this.name;
    }
}
