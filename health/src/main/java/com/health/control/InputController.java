package com.health.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import com.health.Table;
import com.health.gui.GUImain;
import com.health.gui.ProgressDialog;
import com.health.gui.UserInterface;
import com.health.gui.input.FileListing;
import com.health.gui.input.FileListingRow;
import com.health.gui.input.VInputPanel;

/**
 * Controls the selection and loading of the input files.
 *
 * @author lizzy
 *
 */
public final class InputController implements InputLoadedObserver {
	private VInputPanel inputPanel;
	private InputLoader inputLoader = new InputLoader();

	/**
	 * Create new instance of input controller.
	 *
	 * @param inputP
	 *            the input panel
	 */
	public InputController(final VInputPanel inputP) {
		inputPanel = inputP;
	}

	/**
	 * Activate controller by adding all the event listeners.
	 */
	public void control() {
		ListenForAddFile lforAddFile = new ListenForAddFile();
		inputPanel.getFileSelectionPanel().getAddButton()
				.addActionListener(lforAddFile);

		ListenForNext lnext = new ListenForNext();
		inputPanel.getFileSelectionPanel().getNextButton()
				.addActionListener(lnext);

		inputLoader.addInputLoadedObserver(this);
	}

	/**
	 * Gets map with loaded tables.
	 *
	 * @return map
	 */
	public Map<String, Table> getTables() {
		return inputLoader.getMap();
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
		@Override
		public void actionPerformed(final ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser
					.setCurrentDirectory(new File(UserInterface.PATH_TO_DATA));
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				FileListing.addFile(fileChooser.getSelectedFile());

			}

		}

	}

	/**
	 * Action listener for next button.
	 *
	 * @author lizzy
	 *
	 */
	private class ListenForNext implements ActionListener {

		/**
		 * Handles the button click.
		 *
		 * @param e
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			Worker work = new Worker();
			work.execute();
		}

		class Worker extends SwingWorker<Object, Object> {

			@Override
			protected Object doInBackground() throws Exception {
				ProgressDialog.getProgressDialog().showDialog();

				inputLoader.loadTables(getInputData());

				return null;
			}
		}

		private List<InputData> getInputData() {
			List<FileListingRow> files = FileListing.getFileListingRows();
			List<InputData> parsedData = new ArrayList<InputData>();

			for (int i = 0; i < files.size(); i++) {
				String xmlFormat = files.get(i).getXmlFormat()
						.getSelectedItem().toString();
				String fileString = files.get(i).getFileString();

				String name = "table" + i;

				xmlFormat = UserInterface.PATH_TO_CONFIG_XML + xmlFormat
						+ ".xml";

				parsedData.add(new InputData(fileString, xmlFormat, name));
			}

			return parsedData;
		}
	}

	@Override
	public void inputLoaded() {
		GUImain.selectedTab(0, 1);
		ProgressDialog.getProgressDialog().hideDialog();
		GUImain.goToTab("Step 2: Script");
	}

}
