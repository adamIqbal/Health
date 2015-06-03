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

public class Interpreter {
    public static void interpret(final String script) throws IOException {
        Objects.requireNonNull(script);

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

        Context context = Interpreter.createScriptEnvironment();

        StatementListener interpreter = new StatementListener(context);
        walker.walk(interpreter, tree);
    }

    private static Context createScriptEnvironment() {
        Context context = new Context();

        // TODO: Declare types (and methods)

        return context;
    }
}
