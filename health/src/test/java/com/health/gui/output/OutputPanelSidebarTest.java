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
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAdd() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Component component = Mockito.mock(Component.class);
        map.put("testComponent", component);

        OutputPanelSidebar.add(map);

        String name = (String) OutputPanelSidebar.getAnalyses()[0];
        HashMap<String, Object> actual = (HashMap<String, Object>) OutputPanelSidebar
                .getAnalysisData(name);

        assertEquals(map.get("testComponent"), actual.get("testComponent"));
    }

    @Test
    public void testGetList() throws Exception {
        JList<String> expectedList = new JList<String>(
                new DefaultListModel<String>());
        JList<String> actualList = OutputPanelSidebar.getList();
        assertEquals(expectedList, actualList);
    }

}
