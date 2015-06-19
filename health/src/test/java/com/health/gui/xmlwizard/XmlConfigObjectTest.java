package com.health.gui.xmlwizard;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        ArrayList<String> currentColumns = (ArrayList<String>) xml.getColumns();
        
        ArrayList<String> newColumns = new ArrayList<String>();
        newColumns.add("2numbercolumn");
        newColumns.add("datecolumn[yyM[M]dd]");
        newColumns.add("string2column");
        xml.setColumns(newColumns);
        
        assertNotEquals(currentColumns, newColumns);  
        
        ArrayList<String> columns = (ArrayList<String>) xml.getColumns();
        assertEquals(newColumns, columns);
    }

    @Test
    public void testSetColumnTypes() throws Exception {
        ArrayList<ValueType> currentColumns = (ArrayList<ValueType>) xml.getColumnTypes();
        
        ArrayList<ValueType> newColumns = new ArrayList<ValueType>();
        newColumns.add(ValueType.Number);
        newColumns.add(ValueType.Date);
        newColumns.add(ValueType.String);
        xml.setColumnTypes(newColumns);
        
        assertNotEquals(currentColumns, newColumns);  
        
        ArrayList<ValueType> actualColumns = (ArrayList<ValueType>) xml.getColumnTypes();
        assertEquals(newColumns, actualColumns);
    }

    @Test
    public void testSetDateFormat() throws Exception {
        String currentFormat = xml.getDateFormat();
        
        String newFormat = "d[d]/M[M]/yyyy";
        xml.setDateFormat(newFormat);
        
        assertNotEquals(currentFormat, newFormat);
        
        String actual = xml.getDateFormat();
        assertEquals(newFormat, actual);
    }

    @Test
    public void testSetPath() throws Exception {
        Path currentPath = xml.getPath();
        
        Path newPath = Paths.get("new/path/to/file.xml");
        xml.setPath(newPath);
        
        assertNotEquals(currentPath, newPath);
        
        Path actual = xml.getPath();
        assertEquals(newPath, actual);
    }

    @Test
    public void testSetType() throws Exception {
        FileType currentType = xml.getType();
        
        FileType newType = FileType.XLS;
        xml.setType(newType);
        
        assertNotEquals(currentType, newType);
        
        FileType actual = xml.getType();
        assertEquals(newType, actual);
    }

    @Test
    public void testSetValues() throws Exception {
        String[] currentValues = {"startdelim", "enddelim", "delim", "ignorelast"};
        
        String[] newValues = {"4", "1", "1"};
        xml.setValues(newValues);
        
        assertNotEquals(currentValues, newValues);
        
        String[] actual = xml.getValues();
        assertArrayEquals(newValues, actual);
    }

    @Test
    public void testToXMLStringTXT() throws Exception {
        fail("not yet implemented");
    }

}
