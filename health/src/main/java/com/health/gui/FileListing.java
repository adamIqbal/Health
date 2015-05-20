package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import externalClasses.FileDrop;

/**
 * The class that fill and is the panel with a list of files used for input.
 *
 * @author Daan
 */
public class FileListing extends JPanel {

	private static GridBagConstraints fileListingCons;
	private static JPanel listing = new JPanel();
	private static ArrayList<FileListingRow> fileListingRows = new ArrayList<FileListingRow>();
	private static int fileCount = 0;
	private static Color borderColor = Color.BLACK;

	private static final int TOP = 0;
	private static final int MIDDLE = 1;
	private static final int BOTTOM = 2;
	private static final int SINGLE = 3;

	private static int maxStringLength = 50;

	/**
	 * Constructor for the fileListing.
	 */
	public FileListing() {

		this.setLayout(new BorderLayout());

		// Set layout and constraints
		listing.setLayout(new GridBagLayout());

		// fileListing.setBorder(BorderFactory.createLineBorder(Color.black));
		fileListingCons = new GridBagConstraints();
		fileListingCons.fill = GridBagConstraints.BOTH;
		fileListingCons.anchor = GridBagConstraints.LINE_START;

		FileListing.fillFileListing();

		this.add(listing);
	}

	/**
	 * Function fills the listing with rows in fileListingRows.
	 */
	public static void fillFileListing() {
		listing.removeAll();
		sortListingByFormat();
		makeHeaderOfListing();
		fileListingCons.gridwidth = 1;
		fileListingCons.gridheight = 1;
		int rows = fileCount;

		if (rows < 20) {
			rows = 20;
		}

		for (int i = 0; i < rows; i++) {

			try {

				// if format is select format, make single row
				if (fileListingRows.get(i).getXmlFormat().getSelectedItem()
						.toString()
						.equals(FileListingRow.getSelectFormatString())) {
					makeRow(FileListing.SINGLE, i);
				}
				// if not select format but last
				else if (fileCount == i + 1) {
					// if in list make bot
					if (i != 0
							&& fileListingRows.get(i - 1).hasEqualFormat(
									fileListingRows.get(i))) {
						makeRow(FileListing.BOTTOM, i);
						System.out.println("for last");
						// else make single
					} else {
						makeRow(FileListing.SINGLE, i);
					}
				}
				// if first row of listing and next row is not same format
				// or previous was different and next is differend
				else if ((i == 0 || !fileListingRows.get(i - 1).hasEqualFormat(
						fileListingRows.get(i)))
						&& !fileListingRows.get(i + 1).hasEqualFormat(
								fileListingRows.get(i))) {
					makeRow(FileListing.SINGLE, i);
				}
				// if first row of listing and next row has same format
				// or previous was different and next is same
				else if ((i == 0 || !fileListingRows.get(i - 1).hasEqualFormat(
						fileListingRows.get(i)))
						&& fileListingRows.get(i + 1).hasEqualFormat(
								fileListingRows.get(i))) {
					makeRow(FileListing.TOP, i);
				}
				// if previous and next are same format make middle row
				else if (fileListingRows.get(i - 1).hasEqualFormat(
						fileListingRows.get(i))
						&& fileListingRows.get(i + 1).hasEqualFormat(
								fileListingRows.get(i))) {
					makeRow(FileListing.MIDDLE, i);
				}
				// if previous is same but next different
				else if (fileListingRows.get(i - 1).hasEqualFormat(
						fileListingRows.get(i))
						&& !fileListingRows.get(i + 1).hasEqualFormat(
								fileListingRows.get(i))) {
					makeRow(FileListing.BOTTOM, i);
					System.out.println("not because last");
				}

			} catch (IndexOutOfBoundsException e) {
				// make empty row
				fileListingCons.gridy = i + 1;
				for (int j = 0; j < 3; j++) {
					fileListingCons.gridx = j;
					JTextField textField = new JTextField();
					textField.setSize(200, 30);
					textField.setEditable(false);
					new FileDrop(textField, textField.getBorder(),
							new FileDrop.Listener() {
								public void filesDropped(final File[] files) {
									for (int i = 0; i < files.length; i++) {
										FileListing.addFile(files[i]);
									}
								}
							});
					listing.add(textField, fileListingCons);
				}
			}

		}
		listing.revalidate();
		listing.repaint();
	}

	/**
	 * sort the row array by group.
	 */
	private static void sortListingByFormat() {
		ArrayList<FileListingRow> newFileListingRows = new ArrayList<FileListingRow>();

		// sort the list by format
		while (!fileListingRows.isEmpty()) {
			FileListingRow tmp = fileListingRows.remove(0);
			newFileListingRows.add(tmp);

			for (int i = 0; i < fileListingRows.size(); i++) {
				if (tmp.getXmlFormat()
						.getSelectedItem()
						.equals(fileListingRows.get(i).getXmlFormat()
								.getSelectedItem())) {
					newFileListingRows.add(fileListingRows.remove(i));
					i--;
				}
			}

		}

		// make the unselected go last

		// add everything but files with unselected format
		for (int i = 0; i < newFileListingRows.size(); i++) {
			if (!newFileListingRows.get(i).getXmlFormat().getSelectedItem()
					.toString().equals(FileListingRow.getSelectFormatString())) {
				fileListingRows.add(newFileListingRows.get(i));
			}
		}
		// add files with unselected format
		for (int i = 0; i < newFileListingRows.size(); i++) {
			if (newFileListingRows.get(i).getXmlFormat().getSelectedItem()
					.toString().equals(FileListingRow.getSelectFormatString())) {
				fileListingRows.add(newFileListingRows.get(i));
			}
		}
	}

	/**
	 * make the first row of the listing.
	 */
	private static void makeHeaderOfListing() {
		fileListingCons.gridx = 0;
		fileListingCons.gridy = 0;
		JTextField tableHeader1 = new JTextField("File");
		tableHeader1.setEnabled(false);
		tableHeader1.setDisabledTextColor(Color.black);
		tableHeader1.setBackground(Color.gray);
		tableHeader1.setPreferredSize(new Dimension(350, 20));
		listing.add(tableHeader1);

		fileListingCons.gridx = 1;
		JTextField tableHeader2 = new JTextField("Format XML");
		tableHeader2.setEnabled(false);
		tableHeader2.setDisabledTextColor(Color.black);
		tableHeader2.setBackground(Color.gray);
		tableHeader2.setPreferredSize(new Dimension(200, 20));
		listing.add(tableHeader2);

		fileListingCons.gridx = 2;
		JTextField tableHeader3 = new JTextField("Delete");
		tableHeader3.setEnabled(false);
		tableHeader3.setDisabledTextColor(Color.black);
		tableHeader3.setBackground(Color.gray);
		tableHeader3.setPreferredSize(new Dimension(50, 20));
		listing.add(tableHeader3);
	}

	/**
	 * make a row in the listing.
	 *
	 * @param rowType
	 *            defines the kind of row needed to be made.
	 * @param index
	 *            the index of the row.
	 */
	private static void makeRow(final int rowType, final int index) {
		// set bordervariables;
		int top = 0;
		int bottom = 0;

		if (rowType == FileListing.TOP || rowType == FileListing.SINGLE) {
			top = 1;
		}
		if (rowType == FileListing.BOTTOM || rowType == FileListing.SINGLE) {
			bottom = 1;
		}

		System.out.println(rowType + " in row " + index + " with bottom "
				+ bottom + " and top " + top);
		// add fileField to grid
		fileListingCons.gridy = index + 1;
		fileListingCons.gridx = 0;
		fileListingRows.get(index).getFileField()
				.setBorder(new MatteBorder(top, 1, bottom, 0, borderColor));
		listing.add(fileListingRows.get(index).getFileField(), fileListingCons,
				(index * 3) + 1);

		fileListingCons.gridx = 1;
		// add xmlformat in single and top, add empty space for mid and bot
		if (rowType == FileListing.TOP || rowType == FileListing.SINGLE) {
			// needed for group format change
			fileListingRows.get(index).setInGroup(false);

			fileListingRows.get(index).getXmlFormat()
					.setBorder(new MatteBorder(top, 0, bottom, 0, borderColor));
			listing.add(fileListingRows.get(index).getXmlFormat(),
					fileListingCons, (index * 3) + 2);
		} else if (rowType == FileListing.BOTTOM
				|| rowType == FileListing.MIDDLE) {
			// needed for group format change
			fileListingRows.get(index).setInGroup(true);

			JTextField textField = new JTextField();
			textField.setSize(200, 30);
			textField.setEditable(false);
			textField.setPreferredSize(new Dimension(200, 25));
			textField.setBorder(new MatteBorder(0, 0, bottom, 0, borderColor));
			new FileDrop(textField, textField.getBorder(),
					new FileDrop.Listener() {
						public void filesDropped(final File[] files) {
							for (int i = 0; i < files.length; i++) {
								FileListing.addFile(files[i], fileListingRows
										.get(i).getXmlFormat()
										.getSelectedItem().toString());
							}
						}
					});
			listing.add(textField, fileListingCons, (index * 3) + 2);
		}

		// add delete button
		fileListingCons.gridx = 2;
		fileListingRows.get(index).getDeleteButton()
				.setBorder(new MatteBorder(top, 0, bottom, 2, borderColor));
		listing.add(fileListingRows.get(index).getDeleteButton(),
				fileListingCons, (index * 3) + 3);

	}

	/**
	 * add a file to the row array.
	 *
	 * @param newFile
	 *            is the new file.
	 */
	public static void addFile(final File newFile) {
		FileListingRow row = new FileListingRow();

		row.setFileString(newFile.getPath(), maxStringLength);
		fileListingRows.add(row);
		fileCount++;
		fillFileListing();
	}

	/**
	 * add a file to rows array.
	 *
	 * @param newFile
	 *            the file to be added.
	 * @param xmlFormat
	 *            the format in which it should be added.
	 */
	public static void addFile(final File newFile, final String xmlFormat) {
		FileListingRow row = new FileListingRow();

		row.setFileString(newFile.getPath(), maxStringLength);
		row.getXmlFormat().setSelectedItem(xmlFormat);
		fileListingRows.add(row);
		fileCount++;
		fillFileListing();
	}

	/**
	 * delete a file from listing array.
	 *
	 * @param toBeDeleted
	 *            the filestring of the to be deleted file.
	 * @param xmlFormat
	 *            the selected xmlFormat for this file.
	 */
	public static void delete(final String toBeDeleted, final String xmlFormat) {
		boolean found = false;
		for (int i = 0; i < fileCount && !found; i++) {
			if (fileListingRows.get(i).getFileString().equals(toBeDeleted)
					&& fileListingRows.get(i).getXmlFormat().getSelectedItem()
							.toString().equals(xmlFormat)) {
				fileListingRows.remove(i);
				found = true;
			}
		}
		fileCount--;
		FileListing.fillFileListing();
	}

	/**
	 * change format of a group of files with same format.
	 *
	 * @param formatXmlString
	 *            the selected format as string.
	 */
	public static void changeFormat(final String formatXmlString) {
		for (int i = 0; i < fileListingRows.size(); i++) {
			// if xmlformat changed
			if (fileListingRows.get(i).getXmlFormat().getSelectedItem()
					.toString().equals(formatXmlString)) {
				// if next is in group but not same
				try {
					if (!fileListingRows.get(i + 1).getXmlFormat()
							.getSelectedItem().toString()
							.equals(formatXmlString)
							&& fileListingRows.get(i + 1).isInGroup()) {
						fileListingRows.get(i + 1).getXmlFormat()
								.setSelectedItem(formatXmlString);
					}
				} catch (IndexOutOfBoundsException e) {
					e.getStackTrace();
				}
			}
		}
	}
}
