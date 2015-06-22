package com.health.gui.input;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FileListingRowTest {

    private FileListingRow testRow;

    @Before
    public void setup() {
        testRow = new FileListingRow();

    }

    @Test
    public void testConstructor() {
        assertEquals("", testRow.getFileString());
        assertNotNull(testRow.getDeleteButton());
        assertEquals("select format", testRow.getSelectFormatString());
        assertEquals(5, testRow.getXmlFormat().getItemCount());
    }

    @Test
    public void testFillComboBox() {
        String[] xmls = { "select format", "null", "MetingenWebpageXlsConfig",
                "admireTxtConfigIgnoreLast", "AfsprakenXls" };

        for (int i = 0; i < 5; i++) {
            assertEquals(xmls[i], testRow.getXmlFormat().getItemAt(i));
        }
    }

    @Test
    public void testHasEqualFormat() {
        FileListingRow testRow2 = new FileListingRow();
        assertTrue(testRow.hasEqualFormat(testRow2));

        testRow2.getXmlFormat().setSelectedIndex(1);
        assertFalse(testRow.hasEqualFormat(testRow2));
    }
    
    @Test
    public void testInGroup(){
        assertFalse(testRow.isInGroup());
        testRow.setInGroup(true);
        assertTrue(testRow.isInGroup());
    }
    
    @Test
    public void testSetFileString(){
        testRow.setFileString("newString");
        assertEquals("newString", testRow.getFileString());
    }
    
    @Test
    public void testSetFileStringWithInt(){
        testRow.setFileString("newString", 200);
        assertEquals("newString", testRow.getFileString());
        
        testRow.setFileString("/test/newString", 12);
        assertEquals(".../newString", testRow.getFileField().getText());
    }
}
