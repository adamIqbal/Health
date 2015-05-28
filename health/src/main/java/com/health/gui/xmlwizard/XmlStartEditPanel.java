package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.health.FileType;
import com.health.gui.xmlwizard.editsubpanels.XmlStartEditSubPanel;
import com.health.gui.xmlwizard.editsubpanels.XmlTxtEditPanel;
import com.health.gui.xmlwizard.editsubpanels.XmlXlsEditPanel;

/**
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlStartEditPanel extends JPanel implements ItemListener {
	private JComboBox<FileType> fileTypeSelector;
	private JPanel editPanel;
	private HashMap<FileType, XmlStartEditSubPanel> panels;

	public XmlStartEditPanel() {
		super();
		init();
	}

	public XmlStartEditPanel(FileType type) {
		super();
		init();

		fileTypeSelector.setSelectedItem(type);
	}

	private void init() {
		panels = new HashMap<FileType, XmlStartEditSubPanel>();
		this.setLayout(new BorderLayout());

		fileTypeSelector = new JComboBox(FileType.values());
		fileTypeSelector.addItemListener(this);
		this.add(fileTypeSelector, BorderLayout.NORTH);

		editPanel = new JPanel();
		editPanel.setLayout(new CardLayout());
		XmlTxtEditPanel txtPanel = new XmlTxtEditPanel();
		XmlXlsEditPanel xlsPanel = new XmlXlsEditPanel();
		editPanel.add(txtPanel, "TXT");
		editPanel.add(xlsPanel, "XLS");
		this.add(editPanel);

		// add panels to the map
		panels.put(FileType.TXT, txtPanel);
		panels.put(FileType.XLS, xlsPanel);
	}

	public String[] getValues(FileType type) {
		if (panels.containsKey(type)) {
			return panels.get(type).getValues();
		}
		return null;
	}

	public FileType getSelectedType() {
		return (FileType) fileTypeSelector.getSelectedItem();
	}

	public void setValues(String[] values, FileType type) {
		if (panels.containsKey(type)) {
			panels.get(type).setValues(values);
		}
	}

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout) (editPanel.getLayout());
		cl.show(editPanel, evt.getItem().toString());
	}
}
