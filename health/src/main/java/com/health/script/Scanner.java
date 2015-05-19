package com.health.script;

import java.util.List;

/**
 * TODO.
 *
 * @author Martijn
 */
public interface Scanner {
    /**
     * TODO. scanner.
     *
     * @param reader
     *            asdf.
     * @return asfg.
     */
    List<Token> scan(final ScriptReader reader);
}
