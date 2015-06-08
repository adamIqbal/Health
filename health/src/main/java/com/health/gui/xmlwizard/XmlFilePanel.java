package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.health.gui.GUImain;

/**
 * Represents the panel where the user can either select a xml file to edit, or
 * create a new xml file.
 * 
 * @author Bjorn
 *
 */
class XmlFilePanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 9063959421496759474L;
    private JButton newFileButton;
    private JButton selectFileButton;
    private FileList fileList;

    public XmlFilePanel() {
        super();
        this.setLayout(new BorderLayout());

        // add list model
        DefaultListModel<Path> listModel = new DefaultListModel<Path>();
        fileList = new FileList(Paths.get(GUImain.PATHTOXMLFORMATS), listModel);
        this.add(fileList, BorderLayout.CENTER);

        // add buttons
        JPanel buttonPanel = new JPanel();
        newFileButton = new JButton("Create a new file");
        selectFileButton = new JButton("Edit selected file");
        
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                XmlWizard.setXml(new XmlConfigObject());
                XmlWizard.nextPanel();
            }
        });
        
        buttonPanel.add(newFileButton);
        buttonPanel.add(selectFileButton);
        this.setOpaque(false);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getNewFileButton() {
        return newFileButton;
    }

    public JButton getSelectFileButton() {
        return selectFileButton;
    }

    public void addActionListenerToNewFileButton(final ActionListener al) {
        newFileButton.addActionListener(al);
    }

    public void addActionListenerToSelectFileButton(final ActionListener al) {
        selectFileButton.addActionListener(al);
    }

    public Path getSelectedFile() {
        return this.fileList.getSelectedValue();
    }

    /**
     * Lists the XML files in the specified folder.
     * 
     * @author Bjorn van der Laan
     *
     */
    private class FileList extends JList<Path> {
        /**
         * Constant serialized ID used for compatibility.
         */
        private static final long serialVersionUID = 7122730191428505542L;

        public FileList(final Path path, final DefaultListModel<Path> listModel) {
            super(listModel);
            this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
            this.setBackground(Color.WHITE);

            try {
                DirectoryStream<Path> stream;
                stream = Files.newDirectoryStream(path);
                Iterator<Path> iterator = stream.iterator();

                while (iterator.hasNext()) {
                    listModel.addElement(iterator.next());
                }
            } catch (IOException e) {
                // Directory not found, add no elements
            }
        }
    }
}