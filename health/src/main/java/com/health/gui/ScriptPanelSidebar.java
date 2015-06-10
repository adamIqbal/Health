package com.health.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Sidebar displaying several hints about the script.
 * @author Bjorn van der Laan
 *
 */
public class ScriptPanelSidebar extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1458489943027565686L;

    public ScriptPanelSidebar() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(GUImain.GUI_COLOR);
        
        JPanel cards = new JPanel();
        cards.setPreferredSize(new Dimension(200, 450));
        cards.setOpaque(false);
        cards.setLayout(new CardLayout());
        cards.add(getTextChunking());
        cards.add(getTextConnecting());
        
        JButton nextButton = new JButton("Next hint!");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.next(cards);
               
            }
        });
        
        this.add(cards, BorderLayout.CENTER);
        this.add(nextButton, BorderLayout.SOUTH);
    }
    
    private JLabel getTextChunking() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body style='width:350px'>");
        builder.append("<h1>Chunking</h1>");
        builder.append("<p>You can use the chunking operation for instance to determine the maximum value of a column on a specified interval.<p>");
        builder.append("<h2>Example</h2>");
        builder.append("<p>chunk YourTable <br>by <u>date_column</u> <br>per <u>5 days</u>, <u>max</u> of <u>column</u></p>");
        builder.append("</body></html>");
        
        JLabel pane = new JLabel();
        pane.setFocusable(false);
        pane.setText(builder.toString());
        return pane;
    }
    
    private JLabel getTextConnecting() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body style='width:200px'>");
        builder.append("<h1>Connection</h1>");
        builder.append("<p>With connection you can join two tables on a specified attribute.<p>");
        builder.append("<h2>Example</h2>");
        builder.append("<p>connect <u>table1</u> <br>with <u>table2</u> <br>where <u>date_adjusted</u> = <u>date_modified</u></p>");
        builder.append("</body></html>");
        
        JLabel pane = new JLabel();
        pane.setFocusable(false);
        pane.setText(builder.toString());
        return pane;
    }
    
}
