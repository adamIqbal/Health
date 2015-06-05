package com.health.gui.fileSelection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.health.gui.GUImain;

/**
 * Class that makes the file selection panel.
 * @author daan
 *
 */
public class FileSelectionPanel extends JPanel {
	/**
	 * Constructor which set the panel layout and adds.
	 * components
	 */
	public FileSelectionPanel() {
		this.setLayout(new BorderLayout());
		
		JScrollPane scrollForFileListing = new JScrollPane(new FileListing());
		this.add(scrollForFileListing, BorderLayout.CENTER);

		JButton addButton = new JButton("Add file");
		
		ListenForAddFile lforAddFile = new ListenForAddFile();
		addButton.addActionListener(lforAddFile);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(addButton);
		
		this.add(buttonPanel, BorderLayout.SOUTH);

	}
	/**
	 * Listener for the add file button.
	 *
	 */
	private class ListenForAddFile implements ActionListener {

		/**
		 * Handles the button click.
		 *
		 * @param e
		 */
		public void actionPerformed(final ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				FileListing.addFile(
						fileChooser.getSelectedFile());

			}

		}

	}
}
