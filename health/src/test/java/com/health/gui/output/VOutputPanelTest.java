package com.health.gui.output;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class VOutputPanelTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAddAnalysis() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Component component = Mockito.mock(Component.class);
        map.put("testComponent", component);

        VOutputPanel.addAnalysis(map);

        // why is this failing?
        Component actualComponent = OutputMainPanel.getPane().getComponent(0);
        assertEquals(component, actualComponent);

        HashMap<String, Object> actualMap = (HashMap<String, Object>) OutputPanelSidebar
                .getAnalysisData("testComponent");
        assertEquals(map, actualMap);
    }

    @Test
    public void testVOutputPanelConstructor() throws Exception {
        VOutputPanel panel = new VOutputPanel();

        OutputMainPanel mainPanel = (OutputMainPanel) panel.getLeftComponent();
        OutputPanelSidebar sidebar = (OutputPanelSidebar) panel
                .getRightComponent();
        MouseListener[] listeners = OutputPanelSidebar.getList().getListeners(
                MouseListener.class);

        assertNotNull(mainPanel);
        assertNotNull(sidebar);
        Assert.assertNotEquals(0, listeners.length);
    }
}
