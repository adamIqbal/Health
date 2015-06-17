package com.health.gui.help;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Represents a panel in the documentation.
 * This is a default DocumentationPanel.
 * @author Bjorn van der Laan
 *
 */
public class DocumentationPanel extends Documentation {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -3056888230815687530L;

    /**
     * Constructor.
     * @param name name of the panel
     */
    public DocumentationPanel(String name) {
        super(name);

        try {
            this.loadDocumentation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the documentation text from a textfile.
     * Filename should be equal to the documents name.
     * @throws IOException if the I/O operation fails
     */
    @Override
    public final void loadDocumentation() throws IOException {
        File file = FileUtils.getFile("data/docs/" + this.getName() + ".txt");

        if (!file.exists()) {
            file = new File("data/docs/error404.txt");
        }

        String text = FileUtils.readFileToString(file);
        this.setText(text);
    }

}
