package com.health.gui;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class VButtonTest {
    private VButton button;
    
    @Before
    public void setUp() {
        button = new VButton("Test");
    }

    @Test
    public void testVButton_Color() throws Exception {
        Color expected= VButton.BUTTON_COLOR;
        Color actual = button.getBackground();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testVButton_Text() throws Exception {
        String expected = "Test";
        String actual = button.getText();
        assertEquals(expected, actual);
    }
}
