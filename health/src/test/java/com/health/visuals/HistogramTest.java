package com.health.visuals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

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
 * @author Lizzy Scholten
 *
 */
public class HistogramTest {
    private Table table;
    private String filePath; 
    private String configPath; 

    /**
     * Testing.
     */
    @Before
	public final void setUp() {
        filePath = "data/data_use/txtData.txt";
        configPath = "data/configXmls"
    		+ "/admireTxtConfigIgnoreLast.xml";
    }
    
    @Test
    public void writeFileTest() throws IOException, ParserConfigurationException, SAXException, InputException{
    	table = Input.readTable(filePath, configPath);

        final int bin = 10;
        JFreeChart chart = Histogram.createHist(table, "value", bin);

        Histogram.writeChartToPDF(chart, "HistogramTest");
        File f = new File("HistogramTest.pdf");
        assertTrue(f.exists());
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void constructorTest() throws Exception {
        Constructor[] ctors = Histogram.class.getDeclaredConstructors();
        assertEquals("Histogram class should only have one constructor",
                1, ctors.length);
        Constructor ctor = ctors[0];
        assertFalse("Histogram class constructor should be inaccessible", 
                ctor.isAccessible());
        ctor.setAccessible(true); // obviously we'd never do this in production
        assertEquals("You'd expect the construct to return the expected type",
                Histogram.class, ctor.newInstance().getClass());
    }
    
    
	@Test
    public void visualizeTest() throws IOException, ParserConfigurationException, SAXException, InputException  {
		table = Input.readTable(filePath, configPath);

        final int bin = 10;
        JFreeChart chart = Histogram.createHist(table, "value", bin);
        Histogram.visualHist(chart);
        
	}
}
