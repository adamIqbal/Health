package com.health.input;

/**
 * Indicates an exception when reading an input file.
 */
public class InputException extends Exception {
    private static final long serialVersionUID = 5228847200926635240L;

    /**
     * Constructs a new {@link InputException} with the given message.
     *
     * @param message
     *            the detail message.
     */
    public InputException(final String message) {
        super(message);
    }
}
