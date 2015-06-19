package com.health.script.runtime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScriptType.class, ScriptField.class, ScriptMethod.class })
public abstract class ValueTest {
    private ScriptType type;
    private Value value;
    private ScriptField field;
    private ScriptMethod method;
    private ScriptDelegate delegate;

    @Before
    public void setUp() {
        type = mock(ScriptType.class);
        field = mock(ScriptField.class);
        method = mock(ScriptMethod.class);
        delegate = new ScriptDelegate(method, value);

        when(field.getName()).thenReturn("field");
        when(field.getType()).thenReturn(type);

        when(method.getName()).thenReturn("method");
        when(method.createDelegate(any())).thenReturn(delegate);

        when(type.getFields()).thenReturn(Arrays.asList(field));
        when(type.hasField("field")).thenReturn(true);
        when(type.getField("field")).thenReturn(field);
        when(type.getFields()).thenReturn(Arrays.asList(field));
        when(type.hasMethod("method")).thenReturn(true);
        when(type.getMethod("method")).thenReturn(method);
        when(type.getMethods()).thenReturn(Arrays.asList(method));
        when(type.isAssignableFrom(type)).thenReturn(true);

        value = this.createValue(type);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void getField_givenNameNull_throwsScriptRuntimeException() {
        value.getField(null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void getField_givenNameOfNonexistentField_throwsScriptRuntimeException() {
        value.getField("field2");
    }

    @Test
    public void getField_givenNameOfExistingField_returnsField() {
        assertNotNull(value.getField("field"));
    }

    @Test(expected = ScriptRuntimeException.class)
    public void getMethod_givenNameNull_throwsScriptRuntimeException() {
        value.getMethod(null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void getMethod_givenNameOfNonexistentMethod_throwsScriptRuntimeException() {
        value.getMethod("method2");
    }

    @Test
    public void getMethod_givenNameOfExistingMethod_returnsDelegateForMethod() throws Exception {
        Value expected = delegate;
        Value actual = value.getMethod("method").get();

        assertSame(expected, actual);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void getMember_givenNameNull_throwsScriptRuntimeException() {
        value.getMember(null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void getMember_givenNameOfNonexistentMember_throwsScriptRuntimeException() {
        value.getMember("member");
    }

    @Test
    public void getMember_givenNameOfExistingMethod_returnsDelegateForMethod() throws Exception {
        Value expected = delegate;
        Value actual = value.getMember("method").get();

        assertSame(expected, actual);
    }

    @Test
    public void getMember_givenNameOfExistingField_returnsField() {
        assertNotNull(value.getMember("field"));
    }

    @Test(expected = ScriptRuntimeException.class)
    public void setField_givenNameNull_throwsScriptRuntimeException() {
        value.setField(null, value);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void setField_givenNameOfNonexistentField_throwsScriptRuntimeException() {
        value.setField("field2", value);
    }

    @Test
    public void setField_givenNameOfExistingField_setsField() {
        value.setField("field", value);

        assertSame(value, value.getField("field").get());
    }

    protected abstract Value createValue(final ScriptType type);
}
