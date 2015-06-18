package com.health.gui;

import static org.junit.Assert.*;

import java.awt.Color;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class VButtonTest {
    private VButton button;
    
    @Before
    public void setUp() {
        button = new VButton("Test");
    }

    @Test
    public void color_Test() {
        Color expected = VButton.BUTTON_COLOR;
        Color actual = button.getBackground();

        assertEquals(expected, actual);
    }

}
