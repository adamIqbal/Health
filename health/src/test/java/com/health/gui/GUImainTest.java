package com.health.gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GUImainTest {
    private GUImain gui;

    @Before
    public void setUp() throws Exception {
        gui = new GUImain();
    }

    @Test
    public void testGetPanel() throws Exception {
        VidneyPanel input = GUImain.getPanel("Step 1: Input");
        assertNotNull(input);
    }

}
