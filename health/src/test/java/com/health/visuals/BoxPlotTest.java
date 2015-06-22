package com.health.visuals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.xml.parsers.ParserConfigurationException;

import org.jfree.chart.JFreeChart;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Testing box plot.
 * @author Lizzy Scholten
 *
 */
public class BoxPlotTest {
    private Table table;
    private String filePath;
    private String configPath;

    /**
     * Setup before testing.
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
     * @throws IOException
     * 				io exception
     * @throws ParserConfigurationException
     * 				parser configuration exception
     * @throws SAXException
     * 				SAX exception
     * @throws InputException
     * 				input exception
     */
    @Test
	public final void writeFileTest() throws IOException,
						ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        JFreeChart chart = BoxPlot.createBoxPlot(table, "value");

        BoxPlot.writeChartToPDF(chart, "BoxPlotTest");
        File f = new File("BoxPlotTest.pdf");
        assertTrue(f.exists());
    }

    /**
     * Test that constructor does not work.
     * @throws Exception
     * 				exception
     */
    @SuppressWarnings("rawtypes")
	@Test
	public final void constructorTest() throws Exception {
        Constructor[] ctors = BoxPlot.class.getDeclaredConstructors();
        assertEquals("BoxPlot class should only have one constructor",
                1, ctors.length);
        Constructor ctor = ctors[0];
        assertFalse("BoxPlot class constructor should be inaccessible",
                ctor.isAccessible());
        ctor.setAccessible(true);
        assertEquals("You'd expect the construct to return the expected type",
                BoxPlot.class, ctor.newInstance().getClass());
    }

    /**
     * Test the visualization.
     * @throws IOException
     * 				io exception
     * @throws ParserConfigurationException
     * 				parser configuration exception
     * @throws SAXException
     * 				SAX exception
     * @throws InputException
     * 				input exception
     */
    @Test
    public final void visualizeTest() throws IOException,
    						ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        JFreeChart chart = BoxPlot.createBoxPlot(table, "value");
        BoxPlot.visualBoxPlot(chart);
    }

    /**
     * Test the actual plotting of the box plot.
     * @throws IOException
     * 				io exception
     * @throws ParserConfigurationException
     * 				parser configuration exception
     * @throws SAXException
     * 				SAX exception
     * @throws InputException
     * 				input exception
     */
    @Test
    public final void plotTest() throws IOException,
    						ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        BoxPlot.createBoxPlot(table);
    }

    /**
     * Test plotting of the box plot with invalid input.
     * @throws IOException
     * 				io exception
     * @throws ParserConfigurationException
     * 				parser configuration exception
     * @throws SAXException
     * 				SAX exception
     * @throws InputException
     * 				input exception
     */
    @Test(expected = IllegalArgumentException.class)
    public final void badPlotTest() throws IOException,
    						ParserConfigurationException, SAXException, InputException {
    	table = Input.readTable(filePath, configPath);
        BoxPlot.createBoxPlot(table, "unit");
    }

}
