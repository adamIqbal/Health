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
import com.xeiam.xchart.Chart;

/**
 * Testing frequency bar.
 * @author lizzy
 *
 */
public class FreqBarTest {
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
            Chart chart = FreqBar.frequencyBar(table, "value");

            FreqBar.saveGraph(chart, "BarChartTest.pdf");

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
