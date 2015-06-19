package com.health.visuals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;
import com.xeiam.xchart.Chart;

/**
 * Testing box plot.
 * @author Lizzy Scholten
 *
 */
public class BoxPlotTest {
    private Table table;
    private String filePath;
    private String configPath;
    
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    /**
     * Testing.
     */
    @Before
	public final void setUp() {
        filePath = "data/data_use/txtData.txt";
        configPath = "data/configXmls"
        		+ "/admireTxtConfigIgnoreLast.xml";
    }
    
    /**
     * Test whether chart is indeed saved. 
     * Check if the file under the given name exists.
     * @throws InputException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     */
    @Test
    public void writeFileTest() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        JFreeChart chart = BoxPlot.createBoxPlot(table, "value");

        BoxPlot.writeChartToPDF(chart, "BoxPlotTest");
        File f = new File("BoxPlotTest.pdf");
        assertTrue(f.exists());
    }
  
    @SuppressWarnings("rawtypes")
	@Test
    public void constructorTest() throws Exception {
        Constructor[] ctors = BoxPlot.class.getDeclaredConstructors();
        assertEquals("BoxPlot class should only have one constructor",
                1, ctors.length);
        Constructor ctor = ctors[0];
        assertFalse("BoxPlot class constructor should be inaccessible", 
                ctor.isAccessible());
        ctor.setAccessible(true); // obviously we'd never do this in production
        assertEquals("You'd expect the construct to return the expected type",
                BoxPlot.class, ctor.newInstance().getClass());
    }

    @Test
    public final void visualizeTest() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        JFreeChart chart = BoxPlot.createBoxPlot(table, "value");
        BoxPlot.visualBoxPlot(chart);
    }
    
    @Test
    public final void plotTest() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        BoxPlot.createBoxPlot(table);
        //JPanel p = BoxPlot.visualBoxPlot(chart);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public final void badPlotTest() throws IOException, ParserConfigurationException, SAXException, InputException {
    	table = Input.readTable(filePath, configPath);
        BoxPlot.createBoxPlot(table, "unit");
    }

}
