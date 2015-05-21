package com.health.input;

import java.util.List;

import com.health.Table;
import com.health.Column;
import com.health.ValueType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextParserTest {
	private TextParser tp;
	
	@Before
	public void setUp() { 
		tp = new TextParser();
	}
	
	@Test
	public void parse_columns_correct() {
		//InputDescriptor id = mock(InputDescriptor.class);
		
		Table actual = null;
		try {
			InputDescriptor id = new InputDescriptor("data/data_bjorn/Admire2config.xml");
	        String txtPath = "data/data_bjorn/Admire2.txt";
	        
	        actual = tp.parse(txtPath, id);
		} catch (Exception e) {
			assertTrue(false);
		}
        assertNotNull(actual);
        
        List<Column> actualColumns = actual.getColumns();
        String[] expectedNames = {"type", "value", "unit", "misc", "date", "time"};
        ValueType[] expectedTypes = {ValueType.String, ValueType.Number, ValueType.String, ValueType.Number, ValueType.Number, ValueType.Number};
        for(int i = 0; i < 6; i++) {
        	Column col = actualColumns.get(i);
        	assertEquals(expectedNames[i], col.getName());
        	assertEquals(expectedTypes[i], col.getType());
        }
    }
}
