package com.health.gui;

import static org.junit.Assert.*;

import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GUImainTest {
    private final String title = "TestTitle";
    private final String laf = "Metal";
    private GUImain gui;

    @Before
    public void setUp() throws Exception {     
        gui = new GUImain(title, laf);
    }

    @Test
    public void testGetPanel() throws Exception {
        VidneyPanel input = GUImain.getPanel("Step 1: Input");
        assertNotNull(input);
    }

    @Test
    public void testInit_Title() throws Exception {
        String expected = title;
        String actual = gui.getTitle();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSetLAF_NotFound() {
        try {
            gui.setLookAndFeel("NonExistingLAF");
        } catch (Exception e) {
            fail("Exception was thrown in testSetLAF_NotFound");
        }
    }

}
