package com.health.visuals;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;
import com.health.visuals.BoxPlot;

public class BoxPlotTest {
    @Before
    public void setUp() {
        String filePath = "/home/bjorn/Documents/Context/Health/health/data/data_use/txtData.txt";
        String configPath = "/home/bjorn/Documents/Context/Health/health/data/configXmls/admireTxtConfig.xml";
        Table table;
        
        try {
            table = Input.readTable(filePath, configPath);
            BoxPlot.boxPlot(table, "value");
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            Assert.assertTrue("Exception is thrown during setUp", false);
        }
    }
    
    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
