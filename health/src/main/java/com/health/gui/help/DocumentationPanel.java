package com.health.gui.help;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DocumentationPanel extends Documentation {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -3056888230815687530L;

    public DocumentationPanel(String name) {
        super(name);
        
        try {
            this.loadDocumentation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDocumentation() throws IOException {
        File file = FileUtils.getFile("data/docs/" + this.getName() + ".txt");
        
        if(!file.exists()) {
            file = new File("data/docs/error404.txt");
        }
        
        String text = FileUtils.readFileToString(file);
        this.setText(text);
    }

}
