package com.health.input;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Record;
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
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireXlsConfig.xml");
            String xlsPath = "test_data_and_xmls/ADMIRE_56_BPM.xls";

            actual = xp.parse(xlsPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);

        List<Column> actualColumns = actual.getColumns();
        String[] expectedNames = { "date", "time", "sys", "dia", "puls" };
        ValueType[] expectedTypes = { ValueType.Date, ValueType.String,
                ValueType.Number, ValueType.Number, ValueType.Number };
        for (int i = 0; i < expectedTypes.length; i++) {
            Column col = actualColumns.get(i);
            assertEquals(expectedNames[i], col.getName());
            assertEquals(expectedTypes[i], col.getType());
        }
    }

    @Test
    public void testXlsx() {
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/MetingenWebpageXlsConfig.xml");
            String xlsxPath = "test_data_and_xmls/Q_ADMIRE_metingen_pagevisits_141214.xlsx";

            actual = xp.parse(xlsxPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);
        
        int expectedSize = 33372;
        assertTrue(expectedSize == actual.getRecords().size());
        assertFalse(expectedSize < actual.getRecords().size());
        assertFalse(expectedSize > actual.getRecords().size());

        Record rec = actual.getRecords().get(100);
        
        assertEquals("Dag", rec.getValues().get(2));
        assertEquals(852.0, rec.getValues().get(0));
        LocalDateTime dateTime = (LocalDateTime)rec.getValues().get(14);
        assertEquals(21, dateTime.getHour());

        assertEquals(23, actual.getColumns().size());
    }

    @Test
    public void testXls() {
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/admireXlsConfig.xml");
            String xlsxPath = "test_data_and_xmls/ADMIRE_56_BPM.xls";

            actual = xp.parse(xlsxPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);

        int expectedSize = 136;
        assertTrue(expectedSize == actual.getRecords().size());
        assertFalse(expectedSize < actual.getRecords().size());
        assertFalse(expectedSize > actual.getRecords().size());

        Record rec = actual.getRecords().get(10);
        
        assertEquals(null, rec.getValues().get(0));
        assertEquals(181.0, rec.getValues().get(2));
        assertEquals(102.0, rec.getValues().get(4));

        assertEquals(5, actual.getColumns().size());
    }

    @Test
    public void testXlsxWithNullDates() {
        Table actual = null;
        try {
            InputDescriptor id = new InputDescriptor(
                    "test_data_and_xmls/AfsprakenXlsx.xml");
            String xlsxPath = "test_data_and_xmls/Afspraken_geanonimiseerd.xlsx";

            actual = xp.parse(xlsxPath, id);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertNotNull(actual);

        int expectedSize = 4114;
        assertTrue(expectedSize == actual.getRecords().size());
        assertFalse(expectedSize < actual.getRecords().size());
        assertFalse(expectedSize > actual.getRecords().size());

        Record rec = actual.getRecords().get(100);

        assertEquals(3.0, rec.getValues().get(0));
        assertEquals(1.0, rec.getValues().get(1));
        assertEquals("30-Aug-2012", rec.getValues().get(2));

        assertEquals(4, actual.getColumns().size());

    }

}
