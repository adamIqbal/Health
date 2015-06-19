/**
 * 
 */
package com.health.gui.output;

import static org.junit.Assert.assertEquals;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Bjorn van der Laan
 *
 */
public class OutputMainPanelTest {
    private OutputMainPanel panel;
    @Before
    public void setUp() {
        panel = new OutputMainPanel();
    }

    @Test
    public void testSetJTableData() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JTable component = new JTable();
        map.put("testJTable", component);
        
        OutputMainPanel.setData(map);
        int expected = OutputMainPanel.getPane().getComponentCount();
        assertEquals(expected, 1);
        
        JScrollPane scrollPane = (JScrollPane) OutputMainPanel.getPane().getComponent(0);
        JViewport port = (JViewport) scrollPane.getComponent(0);
        JTable actual = (JTable) port.getView();
        assertEquals(component, actual);
    }
    
    @Test
    public void testSetComponentData() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Component component = Mockito.mock(Component.class);
        map.put("testComponent", component);
        
        OutputMainPanel.setData(map);
        int expected = OutputMainPanel.getPane().getComponentCount();
        assertEquals(expected, 1);
        
        Component actual = OutputMainPanel.getPane().getComponent(0);
        assertEquals(component, actual);
    }
}
