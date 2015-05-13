package com.health;

import GUI.GUImain;

/**
 * Utility class containing the entry point for the program.
 *
 */
final class App {
    private App() {
        // Private constructor to prevent instantiation since Java lacks static
        // classes
    }

    /**
     * The entry point for the program.
     *
     * @param args
     *            array of arguments passed to the program.
     */
    public static void main(final String[] args) {
        GUImain gui = new GUImain();
    }
}
