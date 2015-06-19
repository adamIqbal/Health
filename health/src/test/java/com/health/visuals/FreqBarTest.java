package com.health.visuals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
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
	 * Setup before testing.
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
        Chart chart = FreqBar.frequencyBar(table, "value");

        FreqBar.saveGraph(chart, "BarChartTest");
        File f = new File("BarChartTest.pdf");
        assertTrue(f.exists());
    }

    /**
     * Test that constructor cannot be called.
     * @throws Exception
     * 				exception
     */
    @SuppressWarnings("rawtypes")
	@Test
	public final void constructorTest() throws Exception {
        Constructor[] ctors = FreqBar.class.getDeclaredConstructors();
        assertEquals("FreqBar class should only have one constructor",
                1, ctors.length);
        Constructor ctor = ctors[0];
        assertFalse("FreqBar class constructor should be inaccessible",
                ctor.isAccessible());
        ctor.setAccessible(true);
        assertEquals("You'd expect the construct to return the expected type",
                FreqBar.class, ctor.newInstance().getClass());
    }

    /**
     * Testing bar chart creation with only table as input.
     * @throws IOException
     * 				io exception
     * @throws ParserConfigurationException
     * 				parser configuration exception
     * @throws SAXException
     * 				SAX exception
     * @throws InputException
     * 				input exception
     */
    @Test(expected = RuntimeException.class)
    public final void createBarTest2() throws IOException,
    					ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        FreqBar.frequencyBar(table);
    }

    /**
     * Testing bar chart creation with table and value column as input.
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
    public final void containerTest() throws IOException,
    				ParserConfigurationException, SAXException, InputException {
    	table = Input.readTable(filePath, configPath);
        Chart chart = FreqBar.frequencyBar(table, "value");
        FreqBar.getContainer(chart);
    }

    /**
     * Testing bar creation with date column.
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
    public final void createBarTest3() throws IOException,
    				ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath);
        FreqBar.frequencyBar(table, "date");
    }

    /**
     * Testing bar creation with frequency column.
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
    public final void createBarTest4() throws IOException,
    				ParserConfigurationException, SAXException, InputException {
        table = Input.readTable(filePath, configPath2);
        FreqBar.frequencyBar(table, "count_value");
    }

    /**
     * Testing the formatting of frequency map.
     * Input is table with frequency column.
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
    public final void formatFreqMapTest() throws IOException,
    				ParserConfigurationException, SAXException, InputException {
    	table = Input.readTable(filePath, configPath2);
        FreqBar.frequencyBar(table);
    }
}
