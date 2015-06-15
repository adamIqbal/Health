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

public class HistogramTest {
    private Table table;
    
    @Before
    public void setUp() {
        String filePath = "data/data_use/txtData.txt";
        String configPath = "data/configXmls"
        		+ "/admireTxtConfigIgnoreLast.xml";
        try {
            table = Input.readTable(filePath, configPath);
            Histogram.createHistogram(table, "value", 10);
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            Assert.fail("Failed creating Table");
        }
    }
    
    @Test
    public void EmptyTest() {
        //Assert.fail("not implemented yet.");
    }
}