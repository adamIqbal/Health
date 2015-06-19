package com.health.visuals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.health.input.Input;
import com.health.input.InputException;
import com.xeiam.xchart.Chart;

/**
 * Testing frequency bar.
 * @author Lizzy Scholten
 *
 */
public class FreqBarTest {
	private Table table;
	private String filePath;
	private String configPath;
	private String configPath2;
	
	/**
	 * Testing.
	 */
    @Before
	public final void setUp() {
    	filePath = "data/data_use/txtData.txt";
        configPath = "data/configXmls"
        		+ "/admireTxtConfigIgnoreLast.xml";
        configPath2 = "data/data_lizzy"
        		+ "/admireTxtLizzy.xml";
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
    public final void writeFileTest() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        Chart chart = FreqBar.frequencyBar(table, "value");

        FreqBar.saveGraph(chart, "BarChartTest");
        File f = new File("BarChartTest.pdf");
        assertTrue(f.exists());
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void constructorTest() throws Exception {
        Constructor[] ctors = FreqBar.class.getDeclaredConstructors();
        assertEquals("FreqBar class should only have one constructor",
                1, ctors.length);
        Constructor ctor = ctors[0];
        assertFalse("FreqBar class constructor should be inaccessible", 
                ctor.isAccessible());
        ctor.setAccessible(true); // obviously we'd never do this in production
        assertEquals("You'd expect the construct to return the expected type",
                FreqBar.class, ctor.newInstance().getClass());
    }
    
    /**
     * Testing.
     * @throws InputException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     */
    @Test(expected = RuntimeException.class)
    public final void createBarTest2() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        FreqBar.frequencyBar(table);
    }
    
    @Test
    public final void containerTest() throws IOException, ParserConfigurationException, SAXException, InputException {
    	table = Input.readTable(filePath, configPath);
        Chart chart = FreqBar.frequencyBar(table, "value");
        
        Container con = FreqBar.getContainer(chart);
        
        
    }
    
    @Test
    public final void createBarTest3() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        FreqBar.frequencyBar(table, "date");
    }
    
    @Test
    public final void createBarTest4() throws IOException, ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath2);
        FreqBar.frequencyBar(table, "count_value");
    }
    
    @Test
    public final void formatFreqMapTest() throws IOException, ParserConfigurationException, SAXException, InputException {
    	table = Input.readTable(filePath, configPath2);
        FreqBar.frequencyBar(table);
    }
    
}
