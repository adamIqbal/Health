package com.health.input;

import com.health.Table;
import com.health.ValueType;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputDescriptorTest {

	
	@Before
	public void setUp() {

	}

	@Test
    public void constructor_Test() throws ParserConfigurationException, InputException, SAXException, IOException {
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
        columnTypes.add(ValueType.Number);
        columnTypes.add(ValueType.Number);

        /*
        startDelimiter : [
        endDelimiter : ]
        delimiter : ,
        format : text
        columns : [type, value, unit, misc, date, time]
        columntypes : [String, Number, String, Number, Number, Number]
         */

        InputDescriptor actual = new InputDescriptor("data/data_bjorn/Admire2config.xml");

        assertEquals(startDelimeter, actual.getStartDelimiter());
        assertEquals(endDelimeter, actual.getEndDelimiter());
        assertEquals(delimeter, actual.getDelimiter());
        
        // Deze attributes hebben geen public getter
        // assertEquals(actual.getColumns(), columns);
        // assertEquals(actual.getColumntypes(), columnTypes);
    }

    @Test
    public void buildTable_Test() throws ParserConfigurationException, InputException, SAXException, IOException {
        InputDescriptor id = new InputDescriptor("data/data_bjorn/Admire2config.xml");
        Table table = id.buildTable();
    }
}
