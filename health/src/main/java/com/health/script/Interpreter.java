package com.health.script;

import com.health.script.AST.Program;
import com.health.script.runtime.ScriptRuntimeException;

/**
 * Interface for a interpreter of a script.
 */
public interface Interpreter {
    /**
     * Interprets the given script.
     *
     * @param program
     *            the program to interpret.
     * @param context
     *            the runtime environment for this execution.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur..
     */
    void interpret(final Program program, final Context context) throws ScriptRuntimeException;
}
