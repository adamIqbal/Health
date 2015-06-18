package com.health.visuals;

import java.io.IOException;

import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.jfree.chart.JFreeChart;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Testing Histogram.
 * @author lizzy
 *
 */
public class HistogramTest {
    private Table table;

    /**
     * Testing.
     */
    @Before
	public final void setUp() {
        String filePath = "data/data_use/txtData.txt";
        String configPath = "data/configXmls"
        		+ "/admireTxtConfigIgnoreLast.xml";
        try {
            table = Input.readTable(filePath, configPath);
            
            final int bin = 10;

            JFreeChart chart = Histogram.createHist(table, "value", bin);
            JPanel p = Histogram.visualHist(chart);
            p.setVisible(true);

            Histogram.writeChartToPDF(chart, "VisualHistTest");
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            Assert.fail("Failed creating Table");
        }
    }

    /**
     * Testing.
     */
    @Test
    public void EmptyTest() {
        //Assert.fail("not implemented yet.");
    }
}
