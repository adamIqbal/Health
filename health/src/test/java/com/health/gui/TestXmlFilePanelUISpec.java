package com.health.gui;
import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Button;
import org.uispec4j.Panel;
import org.uispec4j.Trigger;
import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.BasicHandler;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import com.health.App;
import com.health.gui.input.xmlwizard.XmlFilePanel;

public class TestXmlFilePanelUISpec { 
    static {
        UISpec4J.init();
    }

    private static Panel panel;
    @Before
    public void setUp() {
        //setAdapter(new MainClassAdapter(App.class, new String[0]));
    }
    
    @Test
    public void editWithoutSelectedTest() {
        Panel panel = new Panel(new XmlFilePanel());
        Button edit = panel.getButton("Edit selected");        
        
        WindowInterceptor
        .init(edit.triggerClick())
        .process(new WindowHandler("First") {
            @Override
            public Trigger process(Window window) throws Exception {
                BasicHandler.init().assertTitleContains("Warning");
                return edit.triggerClick();
            }
        })
        .run();
    }

}
