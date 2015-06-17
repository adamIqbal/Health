package com.health.gui.input.xmlwizard.startpanels;

import javax.swing.JPanel;

import com.health.FileType;

/**
 * Abstract class for panels for different FileTypes.
 * @author Bjorn van der Laan
 *
 */
public abstract class XmlStartEditSubPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 8469133789590283229L;
    private FileType type;

    /**
     * Gets the values specified in the panel.
     * @return array of values
     */
    @SuppressWarnings("checkstyle:designforextension")
    public String[] getValues() {
        return null;
    };

    /**
     * Sets the values of the panel according to the config XML file selected.
     * @param values
     *            array of values to set
     */
    public void setValues(final String[] values) {
    }

    /**
     * Gets the type attribute.
     * @return value of type attribute
     */
    protected final FileType getType() {
        return type;
    }

    /**
     * Sets the type attribute.
     * @param type
     *            new value of type
     */
    protected final void setType(final FileType type) {
        this.type = type;
    };
}
