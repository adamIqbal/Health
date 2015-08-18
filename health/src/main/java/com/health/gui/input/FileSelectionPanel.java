package com.health.gui.input;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.health.control.InputData;
import com.health.gui.GUImain;
import com.health.gui.ProgressDialog;
import com.health.gui.UserInterface;
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

    
    public VButton getAddButton(){
    	return addButton;
    }
    
    public VButton getNextButton(){
    	return nextButton;
    }
    
}
