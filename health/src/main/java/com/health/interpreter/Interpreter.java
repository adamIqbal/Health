package com.health.interpreter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.health.script.MyScriptLexer;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;

/**
 * Represents a script interpreter.
 */
public final class Interpreter {
    private Interpreter() {
    }

    /**
     * Interprets the given script.
     *
     * @param script
     *            the script to interpret.
     * @param context
     *            the script context.
     * @throws IOException
     *             if any I/O-exceptions occur.
     */
    public static void interpret(final String script, final Context context) throws IOException {
        Interpreter.interpret(script, context, new StatementListener(context));
    }

    /**
     * Interprets the given script.
     *
     * @param script
     *            the script to interpret.
     * @param context
     *            the script context.
     * @param interpreter
     *            a statement listener for interpreting the statements in the
     *            script.
     * @throws IOException
     *             if any I/O-exceptions occur.
     */
    public static void interpret(final String script, final Context context, final StatementListener interpreter)
            throws IOException {
        Objects.requireNonNull(script);
        Objects.requireNonNull(context);

        // Converts the script to an input stream for the lexer
        InputStream is = new ByteArrayInputStream(script.getBytes("UTF-8"));
        ANTLRInputStream input = new ANTLRInputStream(is);

        // Creates a lexer and parser to parse the script
        MyScriptLexer lexer = new MyScriptLexer(input);
        MyScriptParser parser = new MyScriptParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);

        ParseTree tree = parser.parse();

        // Create a walker to walk the parse tree for interpreting
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(interpreter, tree);
    }
}
