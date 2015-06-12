package com.health.control;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class InputDataTest {
    private InputData ip;

    @Before
    public void setUp() {
        ip = new InputData("path/to/data.txt", "path/to/xml.xml", "name");
    }

    @Test
    public void getConfigPath() {
        String expected = "path/to/xml.xml";
        String actual = ip.getConfigPath();

        assertEquals(expected, actual);
    }

    @Test
    public void getFilePath() {
        String expected = "path/to/data.txt";
        String actual = ip.getFilePath();

        assertEquals(expected, actual);
    }

    @Test
    public void getName() {
        String expected = "name";
        String actual = ip.getName();

        assertEquals(expected, actual);
    }
}
