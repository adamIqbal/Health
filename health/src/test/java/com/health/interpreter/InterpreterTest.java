package com.health.interpreter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.health.script.runtime.Context;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Context.class, StatementListener.class })
public class InterpreterTest {
    private final static String script = "";
    private Context context;

    @Before
    public void setUp() {
        context = mock(Context.class);
    }

    @Test(expected = NullPointerException.class)
    public void interpret_givenScriptNull_throwsNullPointerException() throws IOException {
        Interpreter.interpret(null, context);
    }

    @Test(expected = NullPointerException.class)
    public void interpret_givenContextNull_throwsNullPointerException() throws IOException {
        Interpreter.interpret(script, null);
    }

    @Test
    public void interpret_test() throws IOException {
        Interpreter.interpret(script, context);
    }

    @Test
    public void interpret_invokesStatementListener() throws IOException {
        StatementListener interpreter = spy(new StatementListener(context));

        Interpreter.interpret(script, context, interpreter);

        verify(interpreter).enterParse(any());
    }
}
