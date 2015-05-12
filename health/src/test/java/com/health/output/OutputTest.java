// https://leelevett.wordpress.com/2015/03/27/junit-powermock-mockito-and-junitparams-for-testing-legacy-code/
// https://github.com/Pragmatists/JUnitParams/tree/master/src/test/java/junitparams
package com.health.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * Unit test for Output.
 */
@RunWith(JUnitParamsRunner.class)
@PrepareForTest({ Column.class, Record.class, Table.class })
public class OutputTest {
    private Table table;
    private Path filePath;
    private static final String delim = "\n";
    private static final String file = "target/test-output/OutputTest.tmp";
    private static final String format = "{abc}, {xyz}";

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUpTest() {
        filePath = Paths.get(file);

        // Create mock columns
        Column column1 = mock(Column.class);
        Column column2 = mock(Column.class);
        when(column1.getName()).thenReturn("abc");
        when(column2.getName()).thenReturn("xyz");
        when(column1.getType()).thenReturn(ValueType.String);
        when(column2.getType()).thenReturn(ValueType.Number);
        when(column1.getIndex()).thenReturn(0);
        when(column2.getIndex()).thenReturn(1);

        // Create mock table
        table = mock(Table.class);
        when(table.getColumns()).thenReturn(Arrays.asList(column1, column2));
        when(table.getColumn(anyString())).thenReturn(null);
        when(table.getColumn("abc")).thenReturn(column1);
        when(table.getColumn("xyz")).thenReturn(column2);

        // Create mock records
        Record record1 = createMockRecord(table, "one", 1.0);
        Record record2 = createMockRecord(table, "two", null);

        when(table.getRecords()).thenReturn(Arrays.asList(record1, record2));
    }

    @After
    public void tearDownTest() throws IOException {
        Files.deleteIfExists(filePath);
    }

    @Test(expected = NullPointerException.class)
    public void format_givenTableNull_throwsNullPointerException() {
        Output.format((Table) null, "");
    }

    @Test(expected = NullPointerException.class)
    public void format_givenFormatNull_throwsNullPointerException() {
        Output.format(table, (String) null);
    }

    @Test
    public void format_givenValidFormat_returnsIterator() {
        assertNotNull(Output.format(table, ""));
    }

    @Test(expected = IllegalArgumentException.class)
    @TestCaseName("format=\"{0}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormatWithUnmatchedOpeningBrace")
    public void format_givenFormatWithUnmatchedOpeningBrace_throwsIllegalArgumentException(
            String format) {
        Output.format(table, format);
    }

    @Test(expected = IllegalArgumentException.class)
    @TestCaseName("format=\"{0}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormatWithUnmatchedClosingBrace")
    public void format_givenFormatWithUnmatchedClosingBrace_throwsIllegalArgumentException(
            String format) {
        Output.format(table, format);
    }

    @Test(expected = IllegalArgumentException.class)
    @TestCaseName("format=\"{0}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormatWithUnmatchedColumn")
    public void format_givenFormatWithUnmatchedColumn_throwsIllegalArgumentException(
            String format) {
        Output.format(table, "{null}");
    }

    @Test
    @TestCaseName("format=\"{0}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormatWithEscapeBrace")
    public void format_givenFormatWithEscapedBrace_formatsCorrectly(
            String format,
            Iterable<String> expected) {
        Iterable<String> actual = Output.format(table, format);

        assertEquals(expected, actual);
    }

    @Test
    @TestCaseName("format=\"{0}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormat")
    public void format_formatsCorrectly(
            String format,
            Iterable<String> expected) {
        Iterable<String> actual = Output.format(table, format);

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeTable_givenFileNull_throwsNullPointerException()
            throws IOException {
        Output.writeTable((String) null, table, format, delim);
    }

    @Test(expected = NullPointerException.class)
    public void writeTable_givenTableNull_throwsNullPointerException()
            throws IOException {
        Output.writeTable(file, (Table) null, format, delim);
    }

    @Test(expected = NullPointerException.class)
    public void writeTable_givenFormatNull_throwsNullPointerException()
            throws IOException {
        Output.writeTable(file, table, (String) null, delim);
    }

    @Test(expected = NullPointerException.class)
    public void writeTable_givenDelimiterNull_throwsNullPointerException()
            throws IOException {
        Output.writeTable(file, table, format, (String) null);
    }

    @Test
    public void writeTable_createsFile() throws IOException {
        Output.writeTable(file, table, format, delim);

        assertTrue(Files.exists(filePath));
    }

    @Test
    public void writeTable_writesFile() throws IOException {
        Output.writeTable(file, table, "abc", " ");

        assertEquals("abc abc", getOutput());
    }

    @Test
    public void writeTable_overwritesFile() throws IOException {
        // Create the directory and file
        new File(new File(file).getParent()).mkdirs();
        Files.write(filePath, Arrays.asList("--overwrite me--"));

        Output.writeTable(file, table, "abc", " ");

        assertEquals("abc abc", getOutput());
    }

    @Test
    public void writeTableStringTable_writesCorrectResult()
            throws IOException {
        Output.writeTable(file, table);

        assertEquals("one, 1.0\ntwo, ", getOutput());
    }

    @Test
    @TestCaseName("format=\"{0}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormatForWriteTable")
    public void writeTableStringTableString_writesCorrectResult(
            String format,
            String expected) throws IOException {
        Output.writeTable(file, table, format);

        assertEquals(expected, getOutput());
    }

    @Test
    @TestCaseName("format=\"{0}\" delim=\"{1}\"")
    @Parameters(source = OutputTestParameters.class, method = "getFormatAndDelimiterForWriteTable")
    public void writeTable_writesCorrectResult(
            String format,
            String delim,
            String expected) throws Exception {
        Output.writeTable(file, table, format, delim);

        assertEquals(expected, getOutput());
    }

    private String getOutput() throws IOException {
        if (Files.exists(filePath)) {
            return String.join("\n", Files.readAllLines(filePath));
        }
        else {
            return null;
        }
    }

    private static Record createMockRecord(Table table, String abcValue,
            Double xyzValue) {
        Record record = mock(Record.class);

        when(record.getTable()).thenReturn(table);
        when(record.getValue("abc")).thenReturn(abcValue);
        when(record.getValue("xyz")).thenReturn(xyzValue);
        when(record.getValues()).thenReturn(Arrays.asList(abcValue, xyzValue));

        return record;
    }
}
