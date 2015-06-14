package com.health.visuals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

public class FreqBarTest {
    private Table table;
    
    @Before
    public void setUp() {
        String filePath = "test_data_and_xmls/ADMIRE 2.txt";
        String configPath = "test_data_and_xmls/admireTxtConfig.xml";
        try {
            Table table = Input.readTable(filePath, configPath);
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            Assert.fail("Failed creating Table");
        }
    }
    
    @Test
    public void EmptyTest() {
        Assert.fail("not implemented yet.");
    }
}
