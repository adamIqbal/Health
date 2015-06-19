package com.health.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputDescriptor;
import com.health.input.InputException;

public class ReadTimeTest {
    @Test
    public void testAddTimeToDate() {
        String txtPath = "test_data_and_xmls/ADMIRE 2.txt";

        try {
            Table actual = Input.readTable(txtPath,
                    "test_data_and_xmls/admireTxtConfigIgnoreLast.xml");

            ReadTime.addTimeToDate(actual, actual.getDateColumn(),
                    actual.getColumn("time"));

            LocalDateTime dateTime = actual.getRecords().get(1)
                    .getDateValue(actual.getDateColumn().getName());

            assertTrue(dateTime.getHour() > 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
