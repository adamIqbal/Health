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
    public void testGetColumnTypes() throws Exception {
        ArrayList<ValueType> expected = new ArrayList<ValueType>();
        expected.add(ValueType.Date);
        expected.add(ValueType.Number);
        expected.add(ValueType.String);

        ArrayList<ValueType> actual = (ArrayList<ValueType>) xmlConfig
                .getColumnTypes();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetDateFormat() throws Exception {
        String expected = "yyMMdd";
        String actual = xmlConfig.getDateFormat();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPath() throws Exception {
        Path expected = Paths.get("path/to/file.xml");
        Path actual = xmlConfig.getPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetType() throws Exception {
        FileType expected = FileType.TXT;
        FileType actual = xmlConfig.getType();
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

        assertEquals(
                "XmlConfigObject [type=TXT, values=[, , , ], columns=[], columnTypes=[String], path=null]",
                xmlConfig.toString());
    }

    @Test
    public void testXLSxml() {
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

    public void testGetValues() throws Exception {
        String[] expected = { "startdelim", "enddelim", "delim", "ignorelast" };
        String[] actual = xmlConfig.getValues();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSetColumns() throws Exception {
        ArrayList<String> currentColumns = (ArrayList<String>) xmlConfig
                .getColumns();

        ArrayList<String> newColumns = new ArrayList<String>();
        newColumns.add("2numbercolumn");
        newColumns.add("datecolumn[yyM[M]dd]");
        newColumns.add("string2column");
        xmlConfig.setColumns(newColumns);

        assertNotEquals(currentColumns, newColumns);

        ArrayList<String> columns = (ArrayList<String>) xmlConfig.getColumns();
        assertEquals(newColumns, columns);
    }

    @Test
    public void testSetColumnTypes() throws Exception {
        ArrayList<ValueType> currentColumns = (ArrayList<ValueType>) xmlConfig
                .getColumnTypes();

        ArrayList<ValueType> newColumns = new ArrayList<ValueType>();
        newColumns.add(ValueType.Number);
        newColumns.add(ValueType.Date);
        newColumns.add(ValueType.String);
        xmlConfig.setColumnTypes(newColumns);

        assertNotEquals(currentColumns, newColumns);

        ArrayList<ValueType> actualColumns = (ArrayList<ValueType>) xmlConfig
                .getColumnTypes();
        assertEquals(newColumns, actualColumns);
    }

    @Test
    public void testSetDateFormat() throws Exception {
        String currentFormat = xmlConfig.getDateFormat();

        String newFormat = "d[d]/M[M]/yyyy";
        xmlConfig.setDateFormat(newFormat);

        assertNotEquals(currentFormat, newFormat);

        String actual = xmlConfig.getDateFormat();
        assertEquals(newFormat, actual);
    }

    @Test
    public void testSetPath() throws Exception {
        Path currentPath = xmlConfig.getPath();

        Path newPath = Paths.get("new/path/to/file.xml");
        xmlConfig.setPath(newPath);

        assertNotEquals(currentPath, newPath);

        Path actual = xmlConfig.getPath();
        assertEquals(newPath, actual);
    }

    @Test
    public void testSetType() throws Exception {
        FileType currentType = xmlConfig.getType();

        FileType newType = FileType.XLS;
        xmlConfig.setType(newType);

        assertNotEquals(currentType, newType);

        FileType actual = xmlConfig.getType();
        assertEquals(newType, actual);
    }

    @Test
    public void testSetValues() throws Exception {
        String[] currentValues = { "startdelim", "enddelim", "delim",
                "ignorelast" };

        String[] newValues = { "4", "1", "1" };
        xmlConfig.setValues(newValues);

        assertNotEquals(currentValues, newValues);

        String[] actual = xmlConfig.getValues();
        assertArrayEquals(newValues, actual);
    }

    @Test
    public void testXLSXxml() {
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
