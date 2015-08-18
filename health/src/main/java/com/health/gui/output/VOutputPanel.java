package com.health.gui.output;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JList;
import javax.swing.JPanel;

import com.health.control.InputData;
import com.health.control.InputLoaderModule;
import com.health.gui.GUImain;
import com.health.gui.UserInterface;
import com.health.gui.VButton;
import com.health.gui.VidneyPanel;
import com.health.gui.input.FileListing;
import com.health.gui.input.FileListingRow;

/**
 * Represents the panel where the script is typed.
 * @author Bjorn van der Laan and Daan Vermunt
 *
 */
public class VOutputPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5303011708825739028L;

    /**
     * Adds an performed analysis to the output panel.
     * @param data
     *            the data to be displayed
     */
    public static void addAnalysis(final Map<String, Object> data) {
        OutputPanelSidebar.add(data);
        OutputMainPanel.setData(data);
    }

    /**
     * Constructor.
     */
    public VOutputPanel() {
        super();

        OutputMainPanel mainPanel = new OutputMainPanel();
        this.setLeft(mainPanel);

        OutputPanelSidebar sidebar = new OutputPanelSidebar();
        OutputPanelSidebar.getList().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(final MouseEvent e) {
            	JList<String> list = OutputPanelSidebar.getList();
            	if(list.getModel().getSize() > 0) {
	                String selected = OutputPanelSidebar.getList()
	                        .getSelectedValue();
	                OutputMainPanel.setData(OutputPanelSidebar
	                        .getAnalysisData(selected));
            	}
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
        this.setRight(sidebar);
        
        VButton prevButton = new VButton("Return");
        ListenForPrev lprev = new ListenForPrev();
        prevButton.addActionListener(lprev);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(prevButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
    }
    
    private class ListenForPrev implements ActionListener {
    	
    	/**
         * Handles the button click.
         *
         * @param e
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
        	GUImain.selectedTab(2, 1);
        	GUImain.goToTab("Step 2: Script");
        }
    }
}
