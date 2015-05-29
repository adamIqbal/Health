package com.health.script;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Script.
 */
public class ScriptTest {
    private static final String text = "One two three\r\nFour five.";
    private Script script;

    @Before
    public void setUpTest() {
        script = new Script(text);
    }

    /**
     * Tests whether {@link Script#Script(String)} throws a
     * {@link NullPointerException} when given a null reference for script.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenTextNull_throwsNullPointerException() {
        new Script((String) null);
    }

    /**
     * Tests whether {@link Script#Script(String)} sets the script's text when
     * given valid arguments.
     */
    @Test
    public void constructor_setsText() {
        String expected = text;
        String actual = script.getText();
        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumn_givenIndexLessThanZero_throwsIndexOutOfBoundsException() {
        script.getColumn(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumn_givenEqualToTextLength_throwsIndexOutOfBoundsException() {
        script.getColumn(text.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumn_givenGreaterThanTextLength_throwsIndexOutOfBoundsException() {
        script.getColumn(text.length() + 1);
    }

    @Test
    public void getColumn_givenIndexOnLineOne_returnsCorrectValue() {
        int expected = 1;
        int actual = script.getColumn(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getColumn_givenIndexOnLineGreaterThanOne_returnsCorrectValue() {
        int expected = 4;
        int actual = script.getColumn(18);
        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLine_givenIndexLessThanZero_throwsIndexOutOfBoundsException() {
        script.getLine(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLine_givenEqualToTextLength_throwsIndexOutOfBoundsException() {
        script.getLine(text.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLine_givenGreaterThanTextLength_throwsIndexOutOfBoundsException() {
        script.getLine(text.length() + 1);
    }

    @Test
    public void getLine_givenIndexOnLineOne_returnsCorrectValue() {
        int expected = 1;
        int actual = script.getLine(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getLine_givenIndexOnLineGreaterThanOne_returnsCorrectValue() {
        int expected = 2;
        int actual = script.getLine(18);
        assertEquals(expected, actual);
    }

    @Test
    public void toString_returnsText() {
        String expected = text;
        String actual = script.toString();
        assertEquals(expected, actual);
    }
}
