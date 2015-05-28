package com.health.script.runtime;

/**
 * Indicates an exception when interpreting a script.
 */
public class ScriptRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8317543700753590433L;

    /**
     * Constructs a new {@link ScriptRuntimeException} with the given message.
     *
     * @param message
     *            the detail message.
     */
    public ScriptRuntimeException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ScriptRuntimeException} with the given message
     * and cause.
     *
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public ScriptRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
