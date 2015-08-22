package com.health.gui.output;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.health.control.OutputController;

public class VOutputPanelTest {
	private VOutputPanel outputPanel;
	private OutputController outputController;
	
    @Before
    public void setUp() throws Exception {
    	outputPanel = new VOutputPanel();
    	outputController = new OutputController(outputPanel);
    	outputController.control();
    }

    @Test
    public void testAddAnalysis() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Component component = Mockito.mock(Component.class);
        map.put("testComponent", component);

        outputController.addAnalysis(map);

        // why is this failing?
        Component actualComponent = outputPanel.getMainPanel().getPane().getComponent(0);
        assertEquals(component, actualComponent);

        HashMap<String, Object> actualMap = (HashMap<String, Object>) outputPanel.getSidebar()
                .getAnalysisData("testComponent");
        assertEquals(map, actualMap);
    }

    @Test
    public void testVOutputPanelConstructor() throws Exception {
        OutputMainPanel mainPanel = (OutputMainPanel) outputPanel.getLeftComponent();
        OutputPanelSidebar sidebar = (OutputPanelSidebar) outputPanel
                .getRightComponent();
        MouseListener[] listeners = outputPanel.getSidebar().getList().getListeners(
                MouseListener.class);

        assertNotNull(mainPanel);
        assertNotNull(sidebar);
        Assert.assertNotEquals(0, listeners.length);
    }
}
