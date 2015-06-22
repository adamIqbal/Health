package com.health.input;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;

public class InputTest {

    @Test
    public void testTxtInput() throws IOException,
            ParserConfigurationException, SAXException, InputException {
        String filePath = "test_data_and_xmls/ADMIRE 2.txt";
        String configPath = "test_data_and_xmls/admireTxtConfig.xml";
        Table table = Input.readTable(filePath, configPath);
        assertTrue(table != null);
    }

    @Test
    public void testXlsInput() throws IOException,
            ParserConfigurationException, SAXException, InputException {
        String filePath = "test_data_and_xmls/ADMIRE_56_BPM.xls";
        String configPath = "test_data_and_xmls/admireXlsConfig.xml";
        Table table = Input.readTable(filePath, configPath);
        assertTrue(table != null);
    }

    @Test
    public void testXlsxInput() throws IOException,
            ParserConfigurationException, SAXException, InputException {
        String filePath = "test_data_and_xmls/Afspraken_geanonimiseerd.xlsx";
        String configPath = "test_data_and_xmls/AfsprakenXlsx.xml";
        Table table = Input.readTable(filePath, configPath);
        assertTrue(table != null);
    }

    @Test
    public void testNoFormat() throws IOException,
            ParserConfigurationException, SAXException, InputException {
        String filePath = "test_data_and_xmls/ADMIRE 2.txt";
        String configPath = "test_data_and_xmls/admireTxtConfigNoFormatAttribute.xml";
        try {
            Table table = Input.readTable(filePath, configPath);
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testUnknownFormat() throws IOException,
            ParserConfigurationException, SAXException, InputException {
        String filePath = "test_data_and_xmls/ADMIRE 2.txt";
        String configPath = "test_data_and_xmls/admireTxtConfigUnknownFormat.xml";
        try {
            Table table = Input.readTable(filePath, configPath);
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }
    }
}
