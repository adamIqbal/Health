package com.health.gui;
//not working yet.
import static org.junit.Assert.assertEquals;

import java.awt.Color;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class VidneyPanelTest {
    private VidneyPanel panel;

    @Before
    public void setUp() throws Exception {
        panel = Mockito.spy(VidneyPanel.class);
    }

    @Test
    public void testSetLeft() throws Exception {
        JPanel jp = new JPanel();
        panel.setLeft(jp);
        assertEquals(jp, panel.getLeftComponent());
    }

    @Test
    public void testSetRight() throws Exception {
        JPanel jp = new JPanel();
        panel.setLeft(jp);
        assertEquals(jp, panel.getLeftComponent());
    }

    @Test
    public void testPreparePanel() throws Exception {
        JPanel jp = new JPanel();
        panel.setLeft(jp);
        
        Color expected = UserInterface.GUI_COLOR;
        Color actual = panel.getLeftComponent().getBackground();
        assertEquals(expected, actual);
    }
}
