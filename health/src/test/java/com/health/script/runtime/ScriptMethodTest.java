package com.health.script.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScriptDelegate.class, Value.class })
public class ScriptMethodTest {
    private final static String name = "method";
    @Mock
    private ScriptFunction<Value[], Value> function;

    @Test(expected = NullPointerException.class)
    public void constructor_givenNameNull_throwsNullPointerException() {
        new ScriptMethod(null, function);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenFunctionNull_throwsNullPointerException() {
        new ScriptMethod(name, null);
    }

    @Test
    public void constructor_setsName() {
        ScriptMethod method = new ScriptMethod(name, function);

        assertEquals(name, method.getName());
    }

    @Test
    public void constructor_setsType() {
        ScriptMethod method = new ScriptMethod(name, function);

        assertSame(function, method.getFunction());
    }

    @Test
    public void constructor_setsIsStatic() {
        ScriptMethod method = new ScriptMethod(name, function, true);

        assertEquals(true, method.isStatic());
    }

    @Test
    public void createDelegate_createsDelegate() throws Exception {
        ScriptMethod method = new ScriptMethod(name, function, true);
        Value value = mock(Value.class);

        assertNotNull(method.createDelegate(value));
    }
}
