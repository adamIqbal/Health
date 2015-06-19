package com.health.input;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.health.Table;

public class InputFunctionsTest {

    @Test
    public void testdeleteLastLines() {
        TextParser tp = new TextParser();
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfig.xml");
            String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

            actual = tp.parse(txtPath, id);

            int before = actual.getRecords().size();

            id = new InputDescriptor(
                    "test_data_and_xmls/admireTxtConfigIgnoreLast.xml");

            actual = InputFunctions.deleteLastLines(actual, id);
            int after = actual.getRecords().size();

            assertTrue(before > after);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);
    }
}
