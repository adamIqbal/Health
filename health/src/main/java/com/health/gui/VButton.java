package com.health.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

/**
 * Represents a extension of JButton with styling applied.
 * @author Bjorn van der Laan
 *
 */
public class VButton extends JButton {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 7943200926343968143L;
    private final int width = 100;
    private final int height = 50;
    /**
     * Defines the background color of the button.
     */
    static final Color BUTTON_COLOR = new Color(200, 200, 200);

    /**
     * Constructor.
     * @param text
     *            Text on the button
     */
    public VButton(final String text) {
        super(text);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(BUTTON_COLOR);
    }
}
