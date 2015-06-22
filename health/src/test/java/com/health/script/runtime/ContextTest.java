package com.health.script.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScriptType.class, ScriptMethod.class, ScriptDelegate.class })
public class ContextTest {
    private final static String name = "local";
    private ScriptType type;
    private Value value;
    private Context context;
    @Mock
    private ScriptFunction<Value[], Value> function;

    @Before
    public void setUp() {
        context = new Context();
        value = mock(Value.class);
        type = mock(ScriptType.class);

        when(type.getName()).thenReturn(name);
        when(type.isAssignableFrom(type)).thenReturn(true);
        when(type.makeInstance(any())).thenReturn(value);

        when(value.getType()).thenReturn(type);
    }

    @Test
    public void constructor_initializesVariables() {
        assertNotNull(context.getVariables());
    }

    @Test
    public void constructor_initializesTypes() {
        assertNotNull(context.getTypes());
    }

    @Test(expected = NullPointerException.class)
    public void declareLocal_givenNameNull_throwsNullPointerException() {
        context.declareLocal(null, type);
    }

    @Test(expected = NullPointerException.class)
    public void declareLocal_givenTypeNull_throwsNullPointerException() {
        context.declareLocal(name, null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void declareLocal_givenNameOfExistingLocal_throwsScriptRuntimeException() {
        context.declareLocal(name, type);
        context.declareLocal(name, type);
    }

    @Test
    public void declareLocal_declaresLocalVariable() {
        context.declareLocal(name, type);

        assertNotNull(context.getVariables().get(name));
    }

    @Test(expected = ScriptRuntimeException.class)
    public void removeLocal_givenNameOfNonexistentLocal_throwsScriptRuntimeException() {
        context.removeLocal(name);
    }

    @Test
    public void removeLocal_removesLocalVariable() {
        context.declareLocal(name, type);
        context.removeLocal(name);

        assertNull(context.getVariables().get(name));
    }

    @Test
    public void declareStaticMethod_declaresDelegateVariable() throws Exception {
        ScriptDelegate delegate = mock(ScriptDelegate.class);
        when(delegate.getType()).thenReturn(type);
        whenNew(ScriptDelegate.class).withAnyArguments().thenReturn(delegate);

        context.declareStaticMethod(name, function);

        assertSame(delegate, context.getVariables().get(name).get());
    }

    @Test(expected = NullPointerException.class)
    public void declareType_givenTypeNull_throwsNullPointerException() {
        context.declareType(null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void declareType_givenTypeWithExistingName_throwsScriptRuntimeException() {
        ScriptType newType = mock(ScriptType.class);
        when(newType.getName()).thenReturn(name);

        context.declareType(type);
        context.declareType(newType);
    }

    @Test
    public void declareType_declaresType() {
        context.declareType(type);

        assertNotNull(context.getTypes().get(name));
    }

    @Test(expected = NullPointerException.class)
    public void declareStaticType_givenTypeNull_throwsNullPointerException() {
        context.declareStaticType(null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void declareStaticType_givenTypeWithExistingName_throwsScriptRuntimeException() {
        ScriptType newType = mock(ScriptType.class);
        when(newType.getName()).thenReturn(name);

        context.declareStaticType(type);
        context.declareStaticType(newType);
    }

    @Test
    public void declareStaticType_declaresLocalVariable() {
        context.declareStaticType(type);

        assertNotNull(context.getVariables().get(name));
    }

    @Test
    public void declareStaticType_usesTypeConstructor() {
        context.declareStaticType(type);

        assertSame(value, context.getVariables().get(name).get());
    }

    @Test(expected = NullPointerException.class)
    public void isLocalDefined_givenNameNull_throwsNullPointerException() {
        context.isLocalDefined(null);
    }

    @Test
    public void isLocalDefined_givenNameOfDeclaredLocal_returnsTrue() {
        context.declareLocal(name, type);

        assertEquals(true, context.isLocalDefined(name));
    }

    @Test
    public void isLocalDefined_givenNameOfUndeclaredLocal_returnsFalse() {
        assertEquals(false, context.isLocalDefined(name));
    }

    @Test(expected = NullPointerException.class)
    public void isTypeDefined_givenNameNull_throwsNullPointerException() {
        context.isTypeDefined(null);
    }

    @Test
    public void isTypeDefined_givenNameOfDeclaredType_returnsTrue() {
        context.declareType(type);

        assertEquals(true, context.isTypeDefined(name));
    }

    @Test
    public void isTypeDefined_givenNameOfUndeclaredType_returnsFalse() {
        assertEquals(false, context.isTypeDefined(name));
    }

    @Test(expected = NullPointerException.class)
    public void isDefined_givenNameNull_throwsNullPointerException() {
        context.isDefined(null);
    }

    @Test
    public void isDefined_givenNameOfDeclaredLocal_returnsTrue() {
        context.declareLocal(name, type);

        assertEquals(true, context.isDefined(name));
    }

    @Test
    public void isDefined_givenNameOfDeclaredType_returnsTrue() {
        context.declareType(type);

        assertEquals(true, context.isDefined(name));
    }

    @Test
    public void isDefined_givenNameOfUndeclaredSymbol_returnsFalse() {
        assertEquals(false, context.isDefined(name));
    }

    @Test(expected = NullPointerException.class)
    public void lookup_givenNameNull_throwsNullPointerException() {
        context.lookup(null);
    }

    @Test
    public void lookup_givenNameOfDeclaredLocal_returnsLocal() {
        context.declareLocal(name, type, value);

        assertSame(value, context.lookup(name).get());
    }

    @Test(expected = ScriptRuntimeException.class)
    public void lookup_givenNameOfUndeclaredLocal_throwsScriptRuntimeException() {
        assertEquals(false, context.lookup(name));
    }

    @Test(expected = NullPointerException.class)
    public void lookupType_givenNameNull_throwsNullPointerException() {
        context.lookupType(null);
    }

    @Test
    public void lookupType_givenNameOfDeclaredType_returnsType() {
        context.declareType(type);

        assertSame(type, context.lookupType(name));
    }

    @Test(expected = ScriptRuntimeException.class)
    public void lookupType_givenNameOfUndeclaredType_throwsScriptRuntimeException() {
        context.lookupType(name);
    }
}
