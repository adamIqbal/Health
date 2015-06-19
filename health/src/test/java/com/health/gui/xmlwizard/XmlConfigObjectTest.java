package com.health.gui.xmlwizard;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.health.FileType;
import com.health.ValueType;
import com.health.gui.input.xmlwizard.XmlConfigObject;

public class XmlConfigObjectTest {

    XmlConfigObject xmlConfig;

    @Before
    public void setup() {
        xmlConfig = new XmlConfigObject();
    }

    @Test
    public void testColumns() {
        List<String> expected = new ArrayList<String>();
        expected.add("col1");
        expected.add("col2");
        expected.add("col3");
        expected.add("col4");
        xmlConfig.setColumns(expected);

        List<String> actual = xmlConfig.getColumns();

        assertEquals(expected, actual);

    }

    @Test
    public void testColumnTypes() {
        List<ValueType> expected = new ArrayList<ValueType>();
        expected.add(ValueType.Date);
        expected.add(ValueType.Number);
        expected.add(ValueType.Number);
        expected.add(ValueType.String);
        xmlConfig.setColumnTypes(expected);

        List<ValueType> actual = xmlConfig.getColumnTypes();

        assertEquals(expected, actual);
    }

    @Test
    public void testDateFormat() {
        String dateFormat = "dd/MM/yyyy";
        xmlConfig.setDateFormat(dateFormat);

        assertEquals(dateFormat, xmlConfig.getDateFormat());
    }

    @Test
    public void testPath() {
        Path expected = Paths.get("test_data_and_xmls/admireTxtConfig.xml");
        xmlConfig.setPath(expected);

        Path actual = xmlConfig.getPath();

        assertEquals(expected, actual);
    }

    @Test
    public void testType() {

        xmlConfig.setType(FileType.TXT);

        String[] values = { "", "", "", "" };
        xmlConfig.setValues(values);
        List<String> columns = new ArrayList<String>();
        columns.add("");
        xmlConfig.setColumns(columns);
        List<ValueType> columnTypes = new ArrayList<ValueType>();
        columnTypes.add(ValueType.String);
        xmlConfig.setColumnTypes(columnTypes);
        
        assertTrue(xmlConfig.toXMLString().contains("text"));
        
        assertEquals(values[1], xmlConfig.getValues()[1]);
        
        assertEquals("XmlConfigObject [type=TXT, values=[, , , ], columns=[], columnTypes=[String], path=null]",xmlConfig.toString());
    }
    
    @Test
    public void testXLSxml(){
        xmlConfig.setType(FileType.XLS);

        String[] values = { "", "", "" };
        xmlConfig.setValues(values);
        List<String> columns = new ArrayList<String>();
        columns.add("");
        xmlConfig.setColumns(columns);
        List<ValueType> columnTypes = new ArrayList<ValueType>();
        columnTypes.add(ValueType.String);
        xmlConfig.setColumnTypes(columnTypes);
        
        assertTrue(xmlConfig.toXMLString().contains("xls"));
    }
    
    @Test
    public void testXLSXxml(){
        xmlConfig.setType(FileType.XLSX);

        String[] values = { "", "", "" };
        xmlConfig.setValues(values);
        List<String> columns = new ArrayList<String>();
        columns.add("date[dd/MM/yyyy]");
        xmlConfig.setColumns(columns);
        List<ValueType> columnTypes = new ArrayList<ValueType>();
        columnTypes.add(ValueType.Date);
        xmlConfig.setColumnTypes(columnTypes);
        
        assertTrue(xmlConfig.toXMLString().contains("xlsx"));
    }
}
