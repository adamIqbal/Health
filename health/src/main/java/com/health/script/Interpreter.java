package com.health.script;

import com.health.script.AST.Program;
import com.health.script.runtime.ScriptRuntimeException;

public interface Interpreter {
    void interpret(Program program, Context context) throws ScriptRuntimeException;
}
