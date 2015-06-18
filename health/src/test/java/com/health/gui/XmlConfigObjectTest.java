package com.health.gui;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.health.FileType;
import com.health.ValueType;
import com.health.gui.input.xmlwizard.XmlConfigObject;

public class XmlConfigObjectTest {
    private XmlConfigObject xml;

    @Before
    public void setUp() throws Exception {
        xml = new XmlConfigObject();
        xml.setType(FileType.TXT);
        xml.setPath(Paths.get("path/to/file.xml"));
        xml.setDateFormat("yyMMdd");
        
        String[] values = {"startdelim", "enddelim", "delim", "ignorelast"};
        xml.setValues(values);
        
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("datecolumn[yyMMdd]");
        columns.add("numbercolumn");
        columns.add("stringcolumn");
        xml.setColumns(columns);
        
        ArrayList<ValueType> columnTypes = new ArrayList<ValueType>();
        columnTypes.add(ValueType.Date);
        columnTypes.add(ValueType.Number);
        columnTypes.add(ValueType.String);
        xml.setColumnTypes(columnTypes);
    }

    @Test
    public void testColumnsToXML() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testGetColumns() throws Exception {
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("datecolumn[yyMMdd]");
        expected.add("numbercolumn");
        expected.add("stringcolumn");
        xml.setColumns(expected);
        
        ArrayList<String> actual = (ArrayList<String>) xml.getColumns();
        
        assertEquals(expected, actual);
    }

    @Test
    public void testGetColumnTypes() throws Exception {
        ArrayList<ValueType> expected = new ArrayList<ValueType>();
        expected.add(ValueType.Date);
        expected.add(ValueType.Number);
        expected.add(ValueType.String);
        
        ArrayList<ValueType> actual = (ArrayList<ValueType>) xml.getColumnTypes();
        
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDateFormat() throws Exception {
        String expected = "yyMMdd";
        String actual = xml.getDateFormat();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPath() throws Exception {
        Path expected = Paths.get("path/to/file.xml");
        Path actual = xml.getPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetType() throws Exception {
        FileType expected = FileType.TXT;
        FileType actual = xml.getType();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetValues() throws Exception {
        String[] expected = {"startdelim", "enddelim", "delim", "ignorelast"};
        String[] actual = xml.getValues();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSetColumns() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testSetColumnTypes() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testSetDateFormat() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testSetPath() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testSetType() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testSetValues() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testSplitString() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testToXMLStringTXT() throws Exception {
        
        System.out.println(xml.toXMLStringTXT());
        fail("not yet implemented");
    }

}
