package com.health.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.ValueType;

public class InputDescriptorTest {

    @Before
    public void setUp() {

    }

    @Test
    public void constructor_Test() throws ParserConfigurationException,
            InputException, SAXException, IOException {
        String startDelimeter = "[";
        String endDelimeter = "]";
        String delimeter = ",";
        String format = "text";

        List<String> columns = new ArrayList<String>();
        columns.add("type");
        columns.add("value");
        columns.add("unit");
        columns.add("misc");
        columns.add("date");
        columns.add("time");

        List<ValueType> columnTypes = new ArrayList<ValueType>();
        columnTypes.add(ValueType.String);
        columnTypes.add(ValueType.Number);
        columnTypes.add(ValueType.String);
        columnTypes.add(ValueType.Number);
        columnTypes.add(ValueType.Date);
        columnTypes.add(ValueType.Number);

        /*
         * startDelimiter : [ endDelimiter : ] delimiter : , format : text
         * columns : [type, value, unit, misc, date, time] columntypes :
         * [String, Number, String, Number, Number, Number]
         */

        InputDescriptor actual = new InputDescriptor(
                "test_data_and_xmls/admireTxtConfig.xml");

        assertEquals(startDelimeter, actual.getStartDelimiter());
        assertEquals(endDelimeter, actual.getEndDelimiter());
        assertEquals(delimeter, actual.getDelimiter());

        // Deze attributes hebben geen public getter
        assertEquals(actual.getColumns(), columns);
        assertEquals(actual.getColumnTypes(), columnTypes);
        assertEquals("text", actual.getFormat());
        assertEquals(0, actual.getIgnoreLast());
        assertEquals("yyMMdd", actual.getDateFormat());
    }

    @Test
    public void buildTable_Test() throws ParserConfigurationException,
            InputException, SAXException, IOException {
        InputDescriptor id = new InputDescriptor(
                "test_data_and_xmls/admireTxtConfig.xml");
        Table table = id.buildTable();
    }

    @Test
    public void testNoDateFormat() throws ParserConfigurationException,
            InputException, SAXException, IOException {
        InputDescriptor id = new InputDescriptor(
                "test_data_and_xmls/admireTxtConfigNoDateFormat.xml");

        assertEquals(ValueType.String, id.getColumnTypes().get(4));
    }

    @Test
    public void testStartCell() throws ParserConfigurationException,
            InputException, SAXException, IOException {
        InputDescriptor id = new InputDescriptor(
                "test_data_and_xmls/admireXlsConfig.xml");

        assertEquals(4, id.getStartCell().getStartRow());
        assertEquals(1, id.getStartCell().getStartColumn());
    }

    @Test
    public void testNoValueType() throws ParserConfigurationException,
            InputException, SAXException, IOException {
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigNoValueType.xml");
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testValidate() throws ParserConfigurationException,
            InputException, SAXException, IOException {
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigNoDataTag.xml");
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigNoFormatAttribute.xml");
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigNoColumns.xml");
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }

    }
}
