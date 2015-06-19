package com.health.script.runtime;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScriptType.class, ScriptField.class, ScriptMethod.class, Value.class })
public class NonModifiableLValueTest {
    private ScriptType type;
    private Value value;
    private NonModifiableLValue lval;

    @Before
    public void setUp() {
        type = mock(ScriptType.class);
        value = mock(Value.class);

        when(type.isAssignableFrom(any())).thenReturn(false);
        when(type.isAssignableFrom(type)).thenReturn(true);

        when(value.getType()).thenReturn(type);

        lval = new NonModifiableLValue(type, value);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenTypeNull_throwsNullPointerException() {
        new NonModifiableLValue(null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void constructor_givenValueWithIncompatibleType_throwsScriptRuntimeException() {
        Value value = mock(Value.class);
        when(value.getType()).thenReturn(mock(ScriptType.class));

        new NonModifiableLValue(type, value);
    }

    public void constructor_givenValueNull_setsValue() {
        NonModifiableLValue lval = new NonModifiableLValue(type, null);

        assertNull(lval.get());
    }

    @Test
    public void constructor_setsType() {
        NonModifiableLValue lval = new NonModifiableLValue(type);

        assertSame(type, lval.getType());
    }

    @Test
    public void constructor_setsValue() {
        NonModifiableLValue lval = new NonModifiableLValue(type, value);

        assertSame(value, lval.get());
    }

    @Test(expected = ScriptRuntimeException.class)
    public void set_givenValueNull_throwsScriptRuntimeException() {
        lval.set(null);

        assertNull(lval.get());
    }

    @Test(expected = ScriptRuntimeException.class)
    public void set_givenValueWithIncompatibleType_throwsScriptRuntimeException() {
        Value value = mock(Value.class);
        when(value.getType()).thenReturn(mock(ScriptType.class));

        lval.set(value);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void set_givenValueWithCompatibleType_throwsScriptRuntimeException() {
        Value value = mock(Value.class);
        when(value.getType()).thenReturn(type);

        lval.set(value);
    }
}
