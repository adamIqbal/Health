package com.health.visuals;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Testing box plot.
 * @author lizzy
 *
 */
public class BoxPlotTest {
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
            JFreeChart chart = BoxPlot.createBoxPlot(table);
            JPanel p = BoxPlot.visualBoxPlot(chart);

            final Dimension frameDimension = new Dimension(500, 500);

            ApplicationFrame frame = new ApplicationFrame("Vidney");
            frame.setSize(frameDimension);

            frame.setContentPane(p);
            BoxPlot.writeChartToPDF(chart, "BoxPlotTes0.pdf");

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
