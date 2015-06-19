package com.health.gui.input;

import static org.junit.Assert.*;

import java.awt.Component;
import java.io.File;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;

public class FileListingTest {

    private FileListing listing;
    
    @Before
    public void testConstructor() {
        listing = new FileListing();
        Component[] coms = listing.getComponents();
        assertNotNull(coms);
        assertEquals(1, coms.length);
    }
    
    @Test
    public void testAddFile(){
        File newFile = new File("path");
        
        assertEquals(0, FileListing.getFileListingRows().size());
        FileListing.addFile(newFile);
        
        assertEquals(1, FileListing.getFileListingRows().size());
        assertEquals("path", FileListing.getFileListingRows().get(0).getFileString());
        
    }
    
    @Test
    public void testAddFileWithString(){
        File newFile = new File("path");
        FileListingRow row = FileListing.getFileListingRows().get(0);
        String xmlPath = row.getXmlFormat().getItemAt(1).toString();
        
        assertEquals(1, FileListing.getFileListingRows().size());
        FileListing.addFile(newFile, xmlPath);
        
        assertEquals(2, FileListing.getFileListingRows().size());
        assertEquals("path", FileListing.getFileListingRows().get(1).getFileString());
        row = FileListing.getFileListingRows().get(0);
        assertEquals(xmlPath, row.getXmlFormat().getSelectedItem());
    }
        
    @Test
    public void testDelete(){
        FileListing.delete("path", "select format");
        FileListing.fillFileListing();
        assertEquals(7, FileListing.getFileListingRows().size());
        assertEquals("path", FileListing.getFileListingRows().get(0).getFileString());
    }
    
    @Test
    public void testFillFileListing(){
        File newFile = new File("path");
        JComboBox<String> xmls = FileListing.getFileListingRows().get(0).getXmlFormat();
        
        FileListing.addFile(newFile, xmls.getItemAt(0));
        FileListing.addFile(newFile, xmls.getItemAt(0));
        FileListing.addFile(newFile, xmls.getItemAt(0));
        
        FileListing.addFile(newFile, xmls.getItemAt(1));
        FileListing.addFile(newFile, xmls.getItemAt(1));
        
        FileListing.addFile(newFile);
        
        assertEquals(8, FileListing.getFileListingRows().size());
        
    }
    

}
