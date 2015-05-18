package com.health.control;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.health.control.*;

public class ControlModuleTest {
	private ControlModule cm;
	
	@Before
	public void setUp() {
		cm = new ControlModule();
	}
	
	@Test
	public void setData() {
		InputData id1 = new InputData("path/to/xml1.xml", "path/to/data1.txt");
		InputData id2 = new InputData("path/to/xml2.xml", "path/to/data2.txt");
		InputData id3 = new InputData("path/to/xml3.xml", "path/to/data3.txt");
		
		InputData[] id = {id1, id2, id3};
		cm.setData(id);
		
		InputData[] expected = {id1, id2, id3};
		
		assertArrayEquals(expected, cm.getData());
	}
	
	@Test
	public void setScript() {
		cm.setScript("hist('histogram.png', avgByDay.AvgCreatinine, avgByDay.DateTime.DayOfYear, 365);");
		String expected = "hist('histogram.png', avgByDay.AvgCreatinine, avgByDay.DateTime.DayOfYear, 365);";
		
		assertEquals(expected, cm.getScript());
	}
	
	@Test
	public void getData() {
		
	}

}
