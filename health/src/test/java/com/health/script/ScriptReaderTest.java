package com.health.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit test for ScriptReader.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Script.class })
public class ScriptReaderTest {
    private String text;
    private Script script;
    private ScriptReader reader;

    @Before
    public void setUpTest() {
        text = "12\n3";

        script = mock(Script.class);
        when(script.getText()).thenReturn(text);

        reader = new ScriptReader(script);
    }

    /**
     * Tests whether {@link ScriptReader#ScriptReader(Script)} throws a
     * {@link NullPointerException} when given a null reference for script.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenScriptNull_throwsNullPointerException() {
        new ScriptReader((Script) null);
    }

    /**
     * Tests whether {@link ScriptReader#ScriptReader(Script)} sets the
     * readers's script when given valid arguments.
     */
    @Test
    public void constructor_setsText() {
        Script expected = script;
        Script actual = reader.getScript();
        assertSame(expected, actual);
    }

    @Test
    public void getLength_returnsCorrectValue() {
        int expected = text.length();
        int actual = reader.getLength();
        assertEquals(expected, actual);
    }

    @Test
    public void read_givenPositionLessThanLength_positioreturnsCorrectValue() {
        int expected = '1';
        int actual = reader.read();
        assertEquals(expected, actual);
    }

    @Test
    public void read_givenPositionEqualToLength_returnsMinusOne() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        int expected = -1;
        int actual = reader.read();
        assertEquals(expected, actual);
    }

    @Test
    public void read_givenPositionLessThanLength_incrementsPosition() {
        reader.read();

        int expected = 1;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void read_givenPositionEqualToLength_doesNotIncrementPosition() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        reader.read();

        int expected = 4;
        int actual = reader.getPosition();
        assertEquals(expected, actual);

    }

    @Test
    public void peek_givenPositionLessThanLength_positioreturnsCorrectValue() {
        int expected = '1';
        int actual = reader.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void peek_givenPositionEqualToLength_returnsMinusOne() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        int expected = -1;
        int actual = reader.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void peek_givenPositionLessThanLength_doesNotIncrementPosition() {
        reader.peek();

        int expected = 0;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void peek_givenPositionEqualToLength_doesNotIncrementPosition() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        reader.peek();

        int expected = 4;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void peekOffset_givenPositionLessThanLengthAndOffsetLessThanLength_returnsCorrectValue() {
        int expected = '1';
        int actual = reader.peek(0);
        assertEquals(expected, actual);
    }

    @Test
    public void peekOffset_givenPositionLessThanLengthAndOffsetEqualToLength_returnsMinusOne() {
        int expected = -1;
        int actual = reader.peek(4);
        assertEquals(expected, actual);
    }

    @Test
    public void peekOffset_givenPositionLessThanLengthAndOffsetGreaterThanLength_returnsMinusOne() {
        int expected = -1;
        int actual = reader.peek(5);
        assertEquals(expected, actual);
    }

    @Test
    public void peekOffset_givenPositionEqualToLengthAndOffsetEqualToLength_returnsMinusOne() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        int expected = -1;
        int actual = reader.peek(0);
        assertEquals(expected, actual);
    }

    @Test
    public void peekOffset_givenPositionEqualToLengthAndOffsetGreaterThanLength_returnsMinusOne() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        int expected = -1;
        int actual = reader.peek(1);
        assertEquals(expected, actual);
    }

    @Test
    public void skip_givenPositionLessThanLengthAndCountLessThanLength_incrementsPositionByCount() {
        reader.skip(3);

        int expected = 3;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void skip_givenPositionLessThanLengthAndCountEqualsToLength_incrementsPositionToLength() {
        reader.skip(4);

        int expected = 4;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void skip_givenPositionLessThanLengthAndCountGreaterThanLength_incrementsPositionToLength() {
        reader.skip(5);

        int expected = 4;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void skip_givenPositionEqualToLengthAndCountEqualsToLength_doesNotIncrementPosition() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        reader.skip(0);

        int expected = 4;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }

    @Test
    public void skip_givenPositionEqualToLengthAndCountGreaterThanLength_doesNotIncrementPosition() {
        reader.read();
        reader.read();
        reader.read();
        reader.read();

        reader.skip(1);

        int expected = 4;
        int actual = reader.getPosition();
        assertEquals(expected, actual);
    }
}