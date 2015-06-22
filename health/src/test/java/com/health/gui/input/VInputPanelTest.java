package com.health.gui.input;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Component;

import org.junit.Test;

public class VInputPanelTest {

    @Test
    public void testConstructor() {
        VInputPanel panel = new VInputPanel();
        
        Component[] coms = panel.getComponents();
        assertEquals(3, coms.length);
    }

}
