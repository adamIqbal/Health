package com.health.gui.output;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class OutputPanelSidebarTest {
    private OutputPanelSidebar panel;

    @Before
    public void setUp() throws Exception {
        panel = new OutputPanelSidebar();
    }

    @Test
    public void testAdd() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Component component = Mockito.mock(Component.class);
        map.put("testComponent", component);
        
        OutputPanelSidebar.add(map);
        
        assertNotNull(OutputPanelSidebar.getAnalysisData("testComponent"));
        assertEquals(component, OutputPanelSidebar.getAnalysisData("testComponent"));
    }

    @Test
    public void testGetAnalysisData() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testGetList() throws Exception {
        JList<String> list = new JList<String>(new DefaultListModel<String>());
        JList<String> initialList = OutputPanelSidebar.getList();
        assertEquals(list, initialList);       
    }

}
