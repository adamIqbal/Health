package com.health.gui.input;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.health.gui.VButton;

/**
 * Class that makes the file selection panel.
 * @author daan
 *
 */
public class FileSelectionPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -271558376732604213L;
    private VButton addButton;
    private VButton nextButton;

    /**
     * Constructor which set the panel layout and adds. components
     */
    public FileSelectionPanel() {
        super();
        
        this.setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel(
                "Drag your files into the window to start!");
        this.add(instructionLabel, BorderLayout.NORTH);

        JScrollPane scrollForFileListing = new JScrollPane(new FileListing());
        this.add(scrollForFileListing, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        addButton = new VButton("Add file");
        buttonPanel.add(addButton);
        
        nextButton = new VButton("Next");
        buttonPanel.add(nextButton);
        
        this.add(buttonPanel, BorderLayout.SOUTH);

    }
    
    /**
     * Can be called by the input controller.
     * @return the add button
     */
    public VButton getAddButton(){
    	return addButton;
    }
    
    /**
     * Can be called by the input controller.
     * @return the next button
     */
    public VButton getNextButton(){
    	return nextButton;
    }
    
}
