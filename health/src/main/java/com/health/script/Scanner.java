package com.health.script;

import java.util.List;

/**
 * Interface for a scanner of a script.
 */
public interface Scanner {
    /**
     * Scans the given script reader and returns the scanned list of tokens.
     *
     * @param reader
     *            the script reader to scan.
     * @return the scanned list of tokens.
     */
    List<Token> scan(final ScriptReader reader);
}
