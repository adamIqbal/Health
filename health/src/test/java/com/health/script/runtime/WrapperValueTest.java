package com.health.script.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScriptType.class, ScriptField.class, ScriptMethod.class })
public class WrapperValueTest extends ValueTest {
    private ScriptType type;
    private Object innerValue;
    private WrapperValue<Object> value;

    @Override
    public void setUp() {
        super.setUp();

        type = mock(ScriptType.class);

        innerValue = new Object();
        value = new WrapperValue<Object>(type, innerValue);
    }

    @Test(expected = NullPointerException.class)
    public void constructorValue_givenValueNull_setsValue() {
        new WrapperValue<Object>(null);
    }

    @Test
    public void constructorValue_createsType() {
        WrapperValue<Object> value = new WrapperValue<Object>(new Object());

        assertNotNull(value.getType());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenTypeNull_throwsNullPointerException() {
        new WrapperValue<Object>(null, new Object());
    }

    @Test
    public void constructor_givenValueNull_setsValue() {
        WrapperValue<Object> value = new WrapperValue<Object>(type, null);

        assertNull(value.getValue());
    }

    @Test
    public void setValue_setsValue() {
        Object newValue = new Object();
        value.setValue(newValue);

        assertSame(newValue, value.getValue());
    }

    @Test
    public void getWrapperType_returnsTypeWithCorrectName() {
        ScriptType type = WrapperValue.getWrapperType(Object.class);

        assertEquals("Object", type.getName());
    }

    @Test
    public void getWrapperType_givenPreviouslyWrappedType_returnsSameType() {
        ScriptType type1 = WrapperValue.getWrapperType(Object.class);
        ScriptType type2 = WrapperValue.getWrapperType(Object.class);

        assertSame(type1, type2);
    }

    @Override
    protected Value createValue(final ScriptType type) {
        return new WrapperValue<Object>(type, innerValue);
    }
}
