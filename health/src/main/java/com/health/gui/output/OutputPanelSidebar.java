package com.health.gui.output;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Represents the sidebar of the Output section.
 *
 * @author Bjorn van der Laan
 *
 */
public class OutputPanelSidebar extends JPanel {
	private static Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
	/**
	 * Contains all completed visualizations.
	 */
	private JList<String> list = new JList<String>(
			new DefaultListModel<String>());
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 9050949741413643882L;

	/**
	 * Adds a new analysis to the Output section.
	 *
	 * @param data
	 *            data of this analysis
	 */
	public final void add(final Map<String, Object> data) {
		Date date = new Date();
		String name = "Analysis " + date.toString();
		addElement(name);
		dataMap.put(name, data);
	}

	private void addElement(final String el) {
		DefaultListModel<String> model = (DefaultListModel<String>) list
				.getModel();
		model.addElement(el);
		list.repaint();
		list.revalidate();
	}

	/**
	 * Returns an array containing the names of all performed analyses.
	 *
	 * @return array containing names
	 */
	public final Object[] getAnalyses() {
		return dataMap.keySet().toArray();
	}

	/**
	 * Gets the analysis data for a particular analysis.
	 *
	 * @param name
	 *            name of the analysis
	 * @return a Map containing the data
	 */
	public final Map<String, Object> getAnalysisData(final String name) {
		return dataMap.get(name);
	}

	/**
	 * Gets the list of completed analyses.
	 *
	 * @return a JList containing all analyses
	 */
	public final JList<String> getList() {
		return list;
	}

	/**
	 * Constructor.
	 */
	public OutputPanelSidebar() {
		super();
		this.setLayout(new BorderLayout());

		list.setBackground(Color.white);
		this.add(list, BorderLayout.CENTER);

		this.setVisible(true);
	}

}
