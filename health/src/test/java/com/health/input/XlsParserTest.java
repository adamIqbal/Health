package com.health.input;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Table;
import com.health.ValueType;

public class XlsParserTest {
private XlsParser xp;
	
	@Before
	public void setUp() { 
		xp = new XlsParser();
	}
	
	@Test
	public void parse_columns_correct() {
		Table actual = null;
		try {
			InputDescriptor id = new InputDescriptor("test_data_and_xmls/admireXlsConfig.xml");
	        String xlsPath = "test_data_and_xmls/ADMIRE_56_BPM.xls";
	        
	        actual = xp.parse(xlsPath, id);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
        assertNotNull(actual);
        
        List<Column> actualColumns = actual.getColumns();
        String[] expectedNames = {"date", "time", "sys", "dia", "puls"};
        ValueType[] expectedTypes = {ValueType.Date, ValueType.String, ValueType.Number, ValueType.Number, ValueType.Number};
        for(int i = 0; i < expectedTypes.length; i++) {
        	Column col = actualColumns.get(i);
        	assertEquals(expectedNames[i], col.getName());
        	assertEquals(expectedTypes[i], col.getType());
        }
    }
	    
	@Test
	public void testXlsx(){
	    Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor("test_data_and_xmls/MetingenWebpageXlsConfig.xml");
            String xlsxPath = "test_data_and_xmls/Q_ADMIRE_metingen_pagevisits_141214.xlsx";
            
            actual = xp.parse(xlsxPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);
        
        System.out.println(actual.getColumn("ModifiedDate").getName());
	}

}
