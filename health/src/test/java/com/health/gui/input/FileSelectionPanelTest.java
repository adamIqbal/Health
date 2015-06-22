package com.health.gui.input;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import org.junit.Test;

public class FileSelectionPanelTest {

    @Test
    public void testConstructor() {
        FileSelectionPanel panel = new FileSelectionPanel();
        
        Component[] coms = panel.getComponents();
        assertEquals(3, coms.length);
        BorderLayout layout = new BorderLayout();
        assertEquals(layout.getClass(), panel.getLayout().getClass());
    }

}
