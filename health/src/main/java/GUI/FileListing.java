package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import externalClasses.FileDrop;

public class FileListing extends JPanel {

	static GridBagConstraints fileListingCons;
	static JPanel listing = new JPanel();
	static ArrayList<FileListingRow> fileListingRows = new ArrayList<FileListingRow>();
	static int fileCount = 0;
	static Color borderColor = Color.BLACK;

	static int TOP = 0;
	static int MIDDLE = 1;
	static int BOTTOM = 2;
	static int SINGLE = 3;

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
				if (fileListingRows.get(i).xmlFormat.getSelectedItem()
						.toString().equals(FileListingRow.selectFormatString)) {
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
								public void filesDropped(java.io.File[] files) {
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

	private static void sortListingByFormat() {
		ArrayList<FileListingRow> newFileListingRows = new ArrayList<FileListingRow>();

		// sort the list by format
		while (!fileListingRows.isEmpty()) {
			FileListingRow tmp = fileListingRows.remove(0);
			newFileListingRows.add(tmp);

			for (int i = 0; i < fileListingRows.size(); i++) {
				if (tmp.xmlFormat.getSelectedItem().equals(
						fileListingRows.get(i).xmlFormat.getSelectedItem())) {
					newFileListingRows.add(fileListingRows.remove(i));
					i--;
				}
			}

		}

		// make the unselected go last

		// add everything but files with unselected format
		for (int i = 0; i < newFileListingRows.size(); i++) {
			if (!newFileListingRows.get(i).xmlFormat.getSelectedItem()
					.toString().equals(FileListingRow.selectFormatString)) {
				fileListingRows.add(newFileListingRows.get(i));
			}
		}
		// add files with unselected format
		for (int i = 0; i < newFileListingRows.size(); i++) {
			if (newFileListingRows.get(i).xmlFormat.getSelectedItem()
					.toString().equals(FileListingRow.selectFormatString)) {
				fileListingRows.add(newFileListingRows.get(i));
			}
		}
	}

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

	private static void makeRow(int rowType, int index) {
		// set bordervariables;
		int top = 0;
		int bottom = 0;

		
		
		if (rowType == FileListing.TOP || rowType == FileListing.SINGLE) {
			top = 1;
		}
		if (rowType == FileListing.BOTTOM || rowType == FileListing.SINGLE) {
			bottom = 1;
		}

		System.out.println(rowType + " in row " + index + " with bottom " + bottom + " and top " + top);
		// add fileField to grid
		fileListingCons.gridy = index + 1;
		fileListingCons.gridx = 0;
		fileListingRows.get(index).fileField.setBorder(new MatteBorder(top, 1,
				bottom, 0, borderColor));
		listing.add(fileListingRows.get(index).fileField, fileListingCons,
				(index * 3) + 1);

		fileListingCons.gridx = 1;
		// add xmlformat in single and top, add empty space for mid and bot
		if (rowType == FileListing.TOP || rowType == FileListing.SINGLE) {
			// needed for group format change
			fileListingRows.get(index).inGroup = false;

			fileListingRows.get(index).xmlFormat.setBorder(new MatteBorder(top,
					0, bottom, 0, borderColor));
			listing.add(fileListingRows.get(index).xmlFormat, fileListingCons,
					(index * 3) + 2);
		} else if (rowType == FileListing.BOTTOM
				|| rowType == FileListing.MIDDLE) {
			// needed for group format change
			fileListingRows.get(index).inGroup = true;

			JTextField textField = new JTextField();
			textField.setSize(200, 30);
			textField.setEditable(false);
			textField.setPreferredSize(new Dimension(200, 25));
			textField.setBorder(new MatteBorder(0, 0, bottom, 0, borderColor));
			new FileDrop(textField, textField.getBorder(),
					new FileDrop.Listener() {
						public void filesDropped(java.io.File[] files) {
							for (int i = 0; i < files.length; i++) {
								FileListing.addFile(files[i], fileListingRows
										.get(i).xmlFormat.getSelectedItem()
										.toString());
							}
						}
					});
			listing.add(textField, fileListingCons, (index * 3) + 2);
		}

		// add delete button
		fileListingCons.gridx = 2;
		fileListingRows.get(index).deleteButton.setBorder(new MatteBorder(top,
				0, bottom, 2, borderColor));
		listing.add(fileListingRows.get(index).deleteButton, fileListingCons,
				(index * 3) + 3);

	}

	public static void addFile(File newFile) {
		FileListingRow row = new FileListingRow();

		row.setFileString(newFile.getPath());
		fileListingRows.add(row);
		fileCount++;
		fillFileListing();
	}

	public static void addFile(File newFile, String XMLFormat) {
		FileListingRow row = new FileListingRow();

		row.setFileString(newFile.getPath());
		row.xmlFormat.setSelectedItem(XMLFormat);
		fileListingRows.add(row);
		fileCount++;
		fillFileListing();
	}

	public static void delete(String toBeDeleted, String xmlFormat) {
		boolean found = false;
		for (int i = 0; i < fileCount && !found; i++) {
			if (fileListingRows.get(i).fileString.equals(toBeDeleted)
					&& fileListingRows.get(i).xmlFormat.getSelectedItem()
							.toString().equals(xmlFormat)) {
				fileListingRows.remove(i);
				found = true;
			}
		}
		fileCount--;
		FileListing.fillFileListing();
	}

	public static void changeFormat(String formatXmlString) {
		for (int i = 0; i < fileListingRows.size(); i++) {
			// if xmlformat changed
			if (fileListingRows.get(i).xmlFormat.getSelectedItem().toString()
					.equals(formatXmlString)) {
				// if next is in group but not same
				try {
					if (!fileListingRows.get(i + 1).xmlFormat.getSelectedItem()
							.toString().equals(formatXmlString) && fileListingRows.get(i + 1).inGroup) {
						fileListingRows.get(i + 1).xmlFormat.setSelectedItem(formatXmlString);
					}
				} catch (IndexOutOfBoundsException e) {

				}
			}
		}
	}
}
