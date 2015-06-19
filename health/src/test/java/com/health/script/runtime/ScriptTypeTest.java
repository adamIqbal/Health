package com.health.script.runtime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Value.class, ScriptField.class, ScriptMethod.class })
public class ScriptTypeTest {
    private final static String name = "type";
    private Map<String, ScriptField> fields;
    private Map<String, ScriptMethod> methods;
    @Mock
    private ScriptFunction<Value[], Value> constructor;
    @Mock
    private ScriptField field1, field2;
    @Mock
    private ScriptMethod method1, method2;
    private ScriptType type;

    @Before
    public void setUp() {
        fields = new HashMap<String, ScriptField>();
        methods = new HashMap<String, ScriptMethod>();

        field1 = mock(ScriptField.class);
        field2 = mock(ScriptField.class);
        method1 = mock(ScriptMethod.class);
        method2 = mock(ScriptMethod.class);

        fields.put("field1", field1);
        fields.put("field2", field2);
        methods.put("method1", method1);
        methods.put("method2", method2);

        type = new ScriptType(name, fields, methods);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenNameNull_throwsNullPointerException() {
        new ScriptType(null, fields, methods);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenFieldsNull_throwsNullPointerException() {
        new ScriptType(name, null, methods);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenMethodsNull_throwsNullPointerException() {
        new ScriptType(name, fields, null);
    }

    @Test
    public void makeInstance_givenConstructorNull_usesDefaultConstructor() {
        ScriptType type = new ScriptType(
                name,
                new HashMap<String, ScriptField>(),
                new HashMap<String, ScriptMethod>(),
                null);

        assertNotNull(type.makeInstance(null));
    }

    @Test
    public void makeInstance_givenConstructor_usesConstructor() {
        Value value = mock(Value.class);
        ScriptFunction<Value[], Value> ctor = (args) -> value;
        ScriptType type = new ScriptType(name, fields, methods, ctor);

        assertSame(value, type.makeInstance(null));
    }

    @Test
    public void constructor_setsName() {
        ScriptType type = new ScriptType(name, fields, methods);

        assertEquals(name, type.getName());
    }

    @Test
    public void constructor_setsFields() {
        ScriptType type = new ScriptType(name, fields, methods);

        assertThat(type.getFields(), containsInAnyOrder(field1, field2));
    }

    @Test
    public void constructor_setsMethods() {
        ScriptType type = new ScriptType(name, fields, methods);

        assertThat(type.getMethods(), containsInAnyOrder(method1, method2));
    }

    @Test(expected = NullPointerException.class)
    public void hasMember_givenNameNull_throwsNullPointerException() {
        type.hasMember(null);
    }

    @Test
    public void hasMember_givenNameOfExisitingField_returnsTrue() {
        assertEquals(true, type.hasMember("field1"));
    }

    @Test
    public void hasMember_givenNameOfExisitingMethod_returnsTrue() {
        assertEquals(true, type.hasMember("method1"));
    }

    @Test
    public void hasMember_givenNameOfNonexistentMember_returnsFalse() {
        assertEquals(false, type.hasMember("member1"));
    }

    @Test(expected = NullPointerException.class)
    public void hasField_givenNameNull_throwsNullPointerException() {
        type.hasField(null);
    }

    @Test
    public void hasField_givenNameOfExisitingField_returnsTrue() {
        assertEquals(true, type.hasField("field1"));
    }

    @Test
    public void hasField_givenNameOfNonexistentField_returnsFalse() {
        assertEquals(false, type.hasField("field3"));
    }

    @Test(expected = NullPointerException.class)
    public void hasMethod_givenNameNull_throwsNullPointerException() {
        type.hasMethod(null);
    }

    @Test
    public void hasMethod_givenNameOfExisitingMethod_returnsTrue() {
        assertEquals(true, type.hasMethod("method1"));
    }

    @Test
    public void hasMethod_givenNameOfNonexistentMethod_returnsFalse() {
        assertEquals(false, type.hasMethod("method3"));
    }

    @Test(expected = NullPointerException.class)
    public void getMember_givenNameNull_throwsNullPointerException() {
        type.getMember(null);
    }

    @Test
    public void getMember_givenNameOfExisitingField_returnsField() {
        assertEquals(field1, type.getMember("field1"));
    }

    @Test
    public void getMember_givenNameOfExisitingMethod_returnsMethod() {
        assertEquals(method1, type.getMember("method1"));
    }

    @Test
    public void getMember_givenNameOfNonexistentMember_returnsNull() {
        assertNull(type.getMember("member1"));
    }

    @Test(expected = NullPointerException.class)
    public void getField_givenNameNull_throwsNullPointerException() {
        type.getField(null);
    }

    @Test
    public void getField_givenNameOfExisitingField_returnsField() {
        assertEquals(field1, type.getField("field1"));
    }

    @Test
    public void getField_givenNameOfNonexistentField_returnsNull() {
        assertNull(type.getField("field3"));
    }

    @Test(expected = NullPointerException.class)
    public void getMethod_givenNameNull_throwsNullPointerException() {
        type.getMethod(null);
    }

    @Test
    public void getMethod_givenNameOfExisitingMethod_returnsMethod() {
        assertEquals(method1, type.getMethod("method1"));
    }

    @Test
    public void getMethod_givenNameOfNonexistentMethod_returnsNull() {
        assertNull(type.getMethod("method3"));
    }

    @Test
    public void isAssignableFrom_givenTypeNull_returnsFalse() {
        assertEquals(false, type.isAssignableFrom(null));
    }

    @Test
    public void isAssignableFrom_givenSameType_returnsTrue() {
        assertEquals(true, type.isAssignableFrom(type));
    }

    @Test
    public void isAssignableFrom_givenDifferentType_returnsFalse() {
        ScriptType type2 = new ScriptType("type2", new HashMap<String, ScriptField>(),
                new HashMap<String, ScriptMethod>());

        assertEquals(false, type.isAssignableFrom(type2));
    }

    @Test
    public void isAssignableFrom_givenObjectType_returnsTrue() {
        ScriptType objType = new ScriptType("Object", new HashMap<String, ScriptField>(),
                new HashMap<String, ScriptMethod>());

        assertEquals(true, objType.isAssignableFrom(type));
    }
}
