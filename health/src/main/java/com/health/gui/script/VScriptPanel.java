package com.health.gui.script;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

import com.health.gui.VButton;
import com.health.gui.VidneyPanel;

/**
 * Represents the panel where the script is typed.
 *
 * @author Bjorn van der Laan and Daan Vermunt
 *
 */
public final class VScriptPanel extends VidneyPanel {
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 4322421568728565558L;
	// Declare buttons.
	private VButton startAnalysisButton;
	private VButton prevButton;
	private VButton loadScriptButton;
	private VButton clearScriptButton;
	private VButton saveScriptButton;
	private ScriptMainPanel scriptMainPanel;

	/**
	 * Constructor.
	 */
	public VScriptPanel() {
		super();

		JPanel mainPanel = new JPanel(new BorderLayout());

		scriptMainPanel = new ScriptMainPanel();
		mainPanel.add(scriptMainPanel, BorderLayout.CENTER);

		startAnalysisButton = new VButton("Run Analysis");
		startAnalysisButton.setBackground(Color.GREEN);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.add(rigidArea());
		prevButton = new VButton("Previous");
		topPanel.add(prevButton);
		topPanel.add(rigidArea());
		loadScriptButton = new VButton("Load existing script");
		topPanel.add(loadScriptButton);
		topPanel.add(rigidArea());
		clearScriptButton = new VButton("Clear Script");
		topPanel.add(clearScriptButton);
		topPanel.add(rigidArea());
		saveScriptButton = new VButton("Save Script");
		topPanel.add(saveScriptButton);
		topPanel.add(rigidArea());
		topPanel.add(rigidArea());
		topPanel.add(startAnalysisButton);

		mainPanel.add(topPanel, BorderLayout.NORTH);

		this.setLeft(mainPanel);

		ScriptPanelSidebar sidePanel = new ScriptPanelSidebar();
		this.setRight(sidePanel);
	}

	/**
	 * Get analysis button.
	 * @return analysis button
	 */
	public VButton getAnalysisButton() {
		return startAnalysisButton;
	}

	/**
	 * Get previous button.
	 * @return previous button
	 */
	public VButton getPrevButton() {
		return prevButton;
	}

	/**
	 * Get load script button.
	 * @return load script button
	 */
	public VButton getLoadScriptButton() {
		return loadScriptButton;
	}

	/**
	 * Get clear script button.
	 * @return clear script button
	 */
	public VButton getClearScriptButton() {
		return clearScriptButton;
	}

	/**
	 * Get save script button.
	 * @return save script button
	 */
	public VButton getSaveScriptButton() {
		return saveScriptButton;
	}

	/**
	 * Get script main panel.
	 * @return script main panel
	 */
	public ScriptMainPanel getScriptMainPanel() {
		return scriptMainPanel;
	}

	private Component rigidArea() {
		return Box.createRigidArea(new Dimension(10, 0));
	}

	/**
	 * Sets the script area text.
	 *
	 * @param text
	 *            the text to set
	 */
	public void setScriptAreaText(final String text) {
		scriptMainPanel.setScript(text);
	}

	/**
	 * Reads the txt file containing the script.
	 *
	 * @param file
	 *            File containing the script
	 * @throws IOException
	 *             thrown if there is an IO error
	 */
	public void setScript(final File file) throws IOException {
		String script = FileUtils.readFileToString(file);
		setScriptAreaText(script);
	}

}
