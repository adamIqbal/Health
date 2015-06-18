package com.health.gui;
//not working yet.
import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class VidneyPaneltest {
    private VidneyPanel panel;

    @Before
    public void setUp() throws Exception {
        panel = mock(VidneyPanel.class, CALLS_REAL_METHODS);
        JPanel jp = mock(JPanel.class);
       
        //Mockito.when(panel.setLeft(jp));
    }

    @Test
    public void testSetLeft() throws Exception {
        JPanel jp = new JPanel();
        panel.setLeft(jp);
        assertEquals(jp, panel.getLeftComponent());
    }

    @Test
    public void testSetRight() throws Exception {
        fail("not yet implemented");
    }

}
