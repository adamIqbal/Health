package com.health.gui.help;

import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.health.gui.VidneyPanel;

/**
 * Represents a panel where users can find documentation of the application.
 * @author Bjorn van der Laan
 *
 */
public class VHelpPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -8767468584587913052L;
    private HelpPanelSidebar sideBar;
    private HelpMainPanel mainPanel;

    /**
     * Constructor.
     */
    public VHelpPanel() {
        super();

        sideBar = new HelpPanelSidebar();
        mainPanel = new HelpMainPanel();

        sideBar.getTopicList().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                String selected = sideBar.getTopicList().getSelectedValue();
                CardLayout cl = (CardLayout) mainPanel.getCardPanel().getLayout();
                cl.show(mainPanel.getCardPanel(), selected);
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }

            @Override
            public void mousePressed(final MouseEvent e) {
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
            }

        });

        // should be loaded from textfile or database
        addCard("Help1", "some help text blabla");
        addCard("Help2", "also some help text blablablablabla");

        this.setLeft(mainPanel);
        this.setRight(sideBar);
    }

    /**
     * Adds a card to the cardpanel. TODO text should be loaded from textfile in
     * future.
     * @param name
     * @param text
     */
    private void addCard(final String name, final String text) {
        sideBar.addElement(name);

        JLabel textLabel = new JLabel(text);
        textLabel.setVerticalAlignment(JLabel.TOP);
        mainPanel.getCardPanel().add(textLabel, name);
    }
}
