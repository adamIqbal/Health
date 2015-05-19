package com.health.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import externalClasses.FileDrop;

/**
 * @author daan
 *
 */
public class FileListingRow {

	private JTextField fileField;
	private String fileString;
	private JComboBox<String> xmlFormat;
	private JButton deleteButton;
	private ListenForDeleteFile lforDelete;
	private boolean inGroup = false;
	private static String selectFormatString = "select format";

	/**
	 * @return return the standard format string.
	 */
	public static String getSelectFormatString() {
		return selectFormatString;
	}

	/**
	 * @return fileField
	 */
	public JTextField getFileField() {
		return fileField;
	}

	/**
	 * @return fileString
	 */
	public String getFileString() {
		return fileString;
	}

	/**
	 * @param fileString
	 */
	public void setFileString(String fileString) {
		this.fileString = fileString;
	}

	/**
	 * @return xmlFormat
	 */
	public JComboBox<String> getXmlFormat() {
		return xmlFormat;
	}

	/**
	 * @param xmlFormat
	 */
	public void setXmlFormat(JComboBox<String> xmlFormat) {
		this.xmlFormat = xmlFormat;
	}

	/**
	 * @return deleteButton
	 */
	public JButton getDeleteButton() {
		return deleteButton;
	}


	/**
	 * @return inGroup
	 */
	public boolean isInGroup() {
		return inGroup;
	}

	/**
	 * @param inGroup
	 */
	public void setInGroup(boolean inGroup) {
		this.inGroup = inGroup;
	}

	/**
	 * constructor for a file listing row.
	 */
	public FileListingRow() {
		fileField = new JTextField();
		fileField.setEnabled(false);
		fileField.setDisabledTextColor(Color.black);
		fileField.setOpaque(false);

		deleteButton = new JButton("X");
		lforDelete = new ListenForDeleteFile(fileString);
		deleteButton.addActionListener(lforDelete);
		// filedrop for fileField
		new FileDrop(fileField, fileField.getBorder(), new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					FileListing.addFile(files[i], xmlFormat.getSelectedItem()
							.toString());
				}
			}
		});

		this.fillComboBox();

	}

	/**
	 * set the fileString and shorten if too long for the field.
	 * @param fileString
	 * @param maxStringLenght
	 */
	public void setFileString(String fileString, int maxStringLenght) {
		this.fileString = fileString;
		String fileFieldText = fileString;

		// if string wont fit the field
		if (fileFieldText.length() > maxStringLenght) {
			// trim of till 50 chars
			fileFieldText = this.fileString.substring(fileFieldText.length()
					- maxStringLenght);

			// find last slash that fits in the field
			int lastSlash = fileFieldText.indexOf('/');

			// if there is a slash
			if (lastSlash != -1) {
				fileFieldText = fileFieldText.substring(lastSlash);
			}
			fileFieldText = "..." + fileFieldText;
		}

		fileField.setText(fileFieldText);
		lforDelete.setStringToBeDeleted(this.fileString);
		xmlFormat.addActionListener(new ListenForSetFormat());
	}

	/**
	 * fill the combobox with all possible xmlformats
	 */
	public void fillComboBox() {
		String[] formats = {selectFormatString, "textFormat", "fooFormat",
				"faaFormat" };
		xmlFormat = new JComboBox<String>(formats);
	}

	/**
	 * @author daan
	 *
	 */
	private class ListenForDeleteFile implements ActionListener {
		
		private String stringToBeDeleted;

		/**
		 * set the string to be deleted.
		 * @param fileString
		 */
		public ListenForDeleteFile(final String fileString) {
			this.stringToBeDeleted = fileString;
		}

		/**
		 * set the string with construction.
		 * @param newFileString
		 */
		public void setStringToBeDeleted(final String newFileString) {
			this.stringToBeDeleted = newFileString;
		}

		public void actionPerformed(final ActionEvent e) {
			FileListing.delete(this.stringToBeDeleted, xmlFormat
					.getSelectedItem().toString());
		}

	}

	/**
	 * @author daan
	 *
	 */
	private class ListenForSetFormat implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			JComboBox<String> changed = (JComboBox<String>) e.getSource();
			FileListing.changeFormat(changed.getSelectedItem().toString());

			FileListing.fillFileListing();
		}

	}

	/**
	 * function to check if equal formats are selected.
	 * @param that
	 * @return true if equal formats are selected
	 */
	public boolean hasEqualFormat(FileListingRow that) {
		return this.xmlFormat.getSelectedItem().toString()
				.equals(that.xmlFormat.getSelectedItem().toString());
	}

}
