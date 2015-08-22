package com.health.gui.script;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import externalClasses.TextLineNumber;

/**
 * Panel containing script.
 * @author lizzy
 *
 */
public class ScriptMainPanel extends JPanel {

	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 5017484222082184353L;
	private JTextArea editor;

	/**
     * Create new script main panel.
     */
	public ScriptMainPanel() {
		super();
		this.setLayout(new BorderLayout());
		editor = new JTextArea("");
		editor.setFont(new Font("Helvetica", Font.BOLD, 12));
		JScrollPane scriptPane = new JScrollPane(editor);
		TextLineNumber tln = new TextLineNumber(editor);
		scriptPane.setRowHeaderView(tln);

		this.add(scriptPane);
	}

	/**
	 * Sets the script.
	 *
	 * @param newScript
	 *            the new script
	 */
	public final void setScript(final String newScript) {
		// not sure first line is needed.
		editor.setText("");
		editor.setText(newScript);
	}

	/**
	 * Gets the script.
	 * @return	the script
	 */
	public final String getScript() {
		return editor.getText();
	}
}
