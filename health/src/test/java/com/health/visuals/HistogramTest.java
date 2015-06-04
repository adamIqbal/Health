package com.health.visuals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Histogram test.
 * @author Lizzy Scholten
 *
 */
public class HistogramTest {

	/**
	 * Setup.
	 */
	@Before
	public final void setUp() {
        String filePath = "/Users/lizzy/Documents/GitHub/healthgit/Health/health/data/data_use/txtData.txt";
        String configPath = "/Users/lizzy/Documents/GitHub/healthgit/Health/health/data/configXmls/admireTxtConfig.xml";
        Table table;

        try {
            table = Input.readTable(filePath, configPath);
            //Bins should be input.
            final int bins = 13;
            Histogram.createHistogram(table, "value", bins);
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            Assert.assertTrue("Exception is thrown during setUp", false);
        }
    }


	/**
	 * Empty.
	 */
    @Test
	public final void test() {
        //fail("Not yet implemented");
    }
}
