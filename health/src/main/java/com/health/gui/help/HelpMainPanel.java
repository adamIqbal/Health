package com.health.gui.help;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Represents the panel where user can read the documentation.
 * @author Bjorn van der Laan
 *
 */
public class HelpMainPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -1602505882375743564L;
    private final int padding = 10;
    private JPanel cardPanel;

    /**
     * Constructor.
     */
    public HelpMainPanel() {
        super(new BorderLayout());

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        this.add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * Gets the cardPanel.
     * @return cardPanel
     */
    protected JPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * Sets the cardPanel.
     * @param cardPanel the panel to set
     */
    protected void setCardPanel(JPanel cardPanel) {
        this.cardPanel = cardPanel;
    }
}
