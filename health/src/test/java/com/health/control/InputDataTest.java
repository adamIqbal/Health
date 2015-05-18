package com.health.control;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.health.control.InputData;

public class InputDataTest {
	private InputData ip;
	
	@Before
	public void setUp() {
		ip = new InputData("path/to/xml.xml", "path/to/data.txt");
	}
	
	@Test
	public void getXML() {
		String expected = "path/to/xml.xml";
		String actual = ip.getXmlPath();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getData() {
		String expected = "path/to/data.txt";
		String actual = ip.getDataPath();
		
		assertEquals(expected, actual);
	}

}
