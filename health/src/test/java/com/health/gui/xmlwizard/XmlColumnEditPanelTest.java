package com.health.gui.xmlwizard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.health.ValueType;
import com.health.gui.input.xmlwizard.XmlColumnEditPanel;

public class XmlColumnEditPanelTest {

    private XmlColumnEditPanel xmlColEditPanel;
    
    @Before
    public void setup(){
        xmlColEditPanel = new XmlColumnEditPanel();
    }
    
    @Test
    public void testConstructor() {
        assertEquals(2, xmlColEditPanel.getComponentCount());
    }
    
    @Test
    public void testAddColumn(){
        xmlColEditPanel.addColumn();
        assertEquals(1, xmlColEditPanel.getColumns().size());
    }


}
