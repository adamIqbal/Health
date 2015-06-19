package com.health.gui.xmlwizard;

import static org.junit.Assert.*;

import org.junit.Test;

import com.health.gui.input.xmlwizard.XmlWizard;

public class XmlWizardTest {

    @Test
    public void testConstructor() {
        XmlWizard xmlWizard = new XmlWizard();
        
        assertEquals(2, xmlWizard.getComponentCount());
    }

}
