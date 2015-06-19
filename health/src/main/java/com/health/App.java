package com.health;

import com.health.gui.GUImain;

/**
 * Utility class containing the entry point for the program.
 *
 */
final class App {
    private App() {
    }

    /**
     * The entry point for the program.
     *
     * @param args
     *            array of arguments passed to the program.
     */
    public static void main(final String[] args) {
        new GUImain("Vidney", "Metal");
    }
}
