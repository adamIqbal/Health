package com.health.script.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScriptType.class })
public class ScriptFieldTest {
    private final static String name = "field";
    private final static ScriptType type = mock(ScriptType.class);

    @Test(expected = NullPointerException.class)
    public void constructor_givenNameNull_throwsNullPointerException() {
        new ScriptField(null, type);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenTypeNull_throwsNullPointerException() {
        new ScriptField(name, null);
    }

    @Test
    public void constructor_setsName() {
        ScriptField field = new ScriptField(name, type);

        assertEquals(name, field.getName());
    }

    @Test
    public void constructor_setsType() {
        ScriptField field = new ScriptField(name, type);

        assertSame(type, field.getType());
    }
}
