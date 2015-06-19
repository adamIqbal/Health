/*package com.health.gui.xmlwizard;


import org.junit.Before;
import org.junit.Test;

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
*/