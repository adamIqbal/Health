package com.health.gui.input;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.health.gui.GUImain;

import externalClasses.FileDrop;

/**
 * @author daan
 *
 */
public final class FileListingRow {

    private JTextField fileField;
    private String fileString = "";
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
     * @return fileField, texfield component which shows the url.
     */
    public JTextField getFileField() {
        return fileField;
    }

    /**
     * @return fileString, the url of the file.
     */
    public String getFileString() {
        return fileString;
    }

    /**
     * @param fileString
     *            the url of the file.
     */
    public void setFileString(final String fileString) {
        this.fileString = fileString;
    }

    /**
     * @return xmlFormat, the combo box with all possible xml formats.
     */
    public JComboBox<String> getXmlFormat() {
        return xmlFormat;
    }

    /**
     * @return deleteButton, the button component which handles deletion.
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * @return inGroup, if in a group with same format.
     */
    public boolean isInGroup() {
        return inGroup;
    }

    /**
     * @param inGroup
     *            set true if in group of files with same format.
     */
    public void setInGroup(final boolean inGroup) {
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
        deleteButton.setBackground(new Color(200, 200, 200));
        lforDelete = new ListenForDeleteFile(fileString);
        deleteButton.addActionListener(lforDelete);
        // filedrop for fileField
        new FileDrop(fileField, fileField.getBorder(), new FileDrop.Listener() {
            @Override
            public void filesDropped(final File[] files) {
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
     *
     * @param fileString
     *            the url of the file.
     * @param maxStringLength
     *            the length of string that fits in fileField.
     */
    public void setFileString(final String fileString, final int maxStringLength) {
        this.fileString = fileString;
        String fileFieldText = fileString;

        // if string wont fit the field
        if (fileFieldText.length() > maxStringLength) {
            // trim of till 50 chars
            fileFieldText = this.fileString.substring(fileFieldText.length()
                    - maxStringLength);

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
     * fill the combobox with all possible xmlformats.
     */
    public void fillComboBox() {

        try {
            List<File> filesInFolder = Files
                    .walk(Paths.get(GUImain.PATH_TO_CONFIG_XML))
                    .filter(Files::isRegularFile).map(Path::toFile)
                    .collect(Collectors.toList());
            String[] formats = new String[filesInFolder.size() + 1];
            formats[0] = selectFormatString;
            for (int i = 1; i <= filesInFolder.size(); i++) {
                String tmp = filesInFolder.get(i - 1).getName();
                formats[i] = tmp.substring(0, tmp.lastIndexOf('.'));
            }
            xmlFormat = new JComboBox<String>(formats);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @author daan
     *
     */
    private class ListenForDeleteFile implements ActionListener {

        private String stringToBeDeleted;

        /**
         * set the string to be deleted.
         *
         * @param fileString
         *            the string of the file.
         */
        public ListenForDeleteFile(final String fileString) {
            this.stringToBeDeleted = fileString;
        }

        /**
         * set the string with construction.
         *
         * @param newFileString
         *            new url of the file.
         */
        public void setStringToBeDeleted(final String newFileString) {
            this.stringToBeDeleted = newFileString;
        }

        @Override
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
     *
     * @param that
     *            the row to compare with.
     * @return true if equal formats are selected.
     */
    public boolean hasEqualFormat(final FileListingRow that) {
        return this.xmlFormat.getSelectedItem().toString()
                .equals(that.xmlFormat.getSelectedItem().toString());
    }

}
