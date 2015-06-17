package com.health.gui.help;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Abstract class for the documentation panels.
 * @author Bjorn van der Laan
 *
 */
public abstract class Documentation extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1780261208245225493L;
    private String name;

    /**
     * Constructor.
     * @param name name of the document
     */
    public Documentation(String name) {
        super(new BorderLayout());
        this.name = name;
    }

    /**
     * Gets the name of the documentation.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the documentation.
     * @param text
     */
    public void setText(String text) {
        JLabel textLabel = new JLabel(text);
        textLabel.setVerticalAlignment(JLabel.TOP);

        this.add(textLabel, BorderLayout.CENTER);
    }

    /**
     * Loads the documentation.
     * @throws IOException if the I/O operation fails
     */
    public abstract void loadDocumentation() throws IOException;
}
