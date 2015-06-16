package com.health.gui.help;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class HelpMainPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -1602505882375743564L;
    private final int padding = 10;
    private JPanel cardPanel;

    public HelpMainPanel() {
        super(new BorderLayout()); 
        
        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        this.add(cardPanel, BorderLayout.CENTER);
    }

    protected JPanel getCardPanel() {
        return cardPanel;
    }

    protected void setCardPanel(JPanel cardPanel) {
        this.cardPanel = cardPanel;
    }
    
}
