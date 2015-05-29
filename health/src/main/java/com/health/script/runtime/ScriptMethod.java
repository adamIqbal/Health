package com.health.script.runtime;

import java.util.Objects;

/**
 * Represents a method on a script type.
 */
public final class ScriptMethod extends ScriptMember {
    private final ScriptFunction<Value[], Value> function;
    private final boolean isStatic;

    /**
     * Creates a new method with the given and function.
     *
     * @param name
     *            the name of the method.
     * @param function
     *            the function of the method.
     */
    public ScriptMethod(final String name, final ScriptFunction<Value[], Value> function) {
        this(name, function, false);
    }

    /**
     * Creates a new method with the given and function.
     *
     * @param name
     *            the name of the method.
     * @param function
     *            the function of the method.
     * @param isStatic
     *            whether or not the method is a static method.
     */
    public ScriptMethod(final String name, final ScriptFunction<Value[], Value> function, final boolean isStatic) {
        super(name);

        Objects.requireNonNull(function);

        this.function = function;
        this.isStatic = isStatic;
    }

    /**
     * Creates a delegate that can be used to invoke this method.
     *
     * @param value
     *            the instance that the method this method will be called on.
     * @return a delegate that can be used to invoke this method.
     */
    public ScriptDelegate createDelegate(final Value value) {
        return new ScriptDelegate(this, value);
    }

    /**
     * Gets the function of this method.
     *
     * @return the function of this method.
     */
    public ScriptFunction<Value[], Value> getFunction() {
        return this.function;
    }

    /**
     * Gets whether or not this method is a static method.
     *
     * @return true if this method is a static method; otherwise false.
     */
    public boolean isStatic() {
        return this.isStatic;
    }
}
