package com.health.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Column;
import com.health.Table;
import com.health.ValueType;

public class TextParserTest {
    private TextParser tp;

    @Before
    public void setUp() {
        tp = new TextParser();
    }

    @Test
    public void parse_columns_correct() {
        // InputDescriptor id = mock(InputDescriptor.class);

        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfig.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);

        List<Column> actualColumns = actual.getColumns();
        String[] expectedNames = { "type", "value", "unit", "misc", "date",
                "time" };
        ValueType[] expectedTypes = { ValueType.String, ValueType.Number,
                ValueType.String, ValueType.Number, ValueType.Date,
                ValueType.Number };
        for (int i = 0; i < 6; i++) {
            Column col = actualColumns.get(i);
            assertEquals(expectedNames[i], col.getName());
            assertEquals(expectedTypes[i], col.getType());

        }
    }

    @Test
    public void testIgnoreLastLines() {
        // InputDescriptor id = mock(InputDescriptor.class);

        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigIgnoreLast.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);

    }

    @Test
    public void testNumberNotANumber() throws ParserConfigurationException,
            SAXException, IOException, InputException {
        // InputDescriptor id = mock(InputDescriptor.class);

        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigStringAsNumber.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);
            assertTrue(false);
        } catch (InputException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testDateValue() throws ParserConfigurationException,
            SAXException, IOException {
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfig.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);
        } catch (InputException e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void testWrongDateFormat() throws ParserConfigurationException,
            SAXException, IOException {
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigWrongDateFormat.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);
        } catch (InputException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testUnknown() throws ParserConfigurationException,
            SAXException, IOException {
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigNoValueType.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);
        } catch (InputException e) {
            assertTrue(true);
        }
    }
}
