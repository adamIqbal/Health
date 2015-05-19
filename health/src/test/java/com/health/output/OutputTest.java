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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * Unit test for Output.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Column.class, Record.class, Table.class })
public class OutputTest {
    private Table table;
    private Path filePath;
    private static final String delim = "\n";
    private static final String file = "target/test-output/OutputTest.tmp";
    private static final String format = "{abc}, {xyz}";

    @BeforeClass
    public static void setUpClass() throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Constructor<?>[] cons = Output.class.getDeclaredConstructors();
        cons[0].setAccessible(true);
        cons[0].newInstance((Object[]) null);
    }

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

    @Test
    public void formatTableTable_formatsCorrectly() {
        String expected = "one, 1.0\ntwo, ";
        String actual = Output.formatTable(table);

        assertEquals(expected, actual);
    }

    @Test
    public void formatTableTableString_formatsCorrectly() {
        String expected = "one=%{1.0}\ntwo=%{}";
        String actual = Output.formatTable(table, "{abc}=%{{{xyz}}}");

        assertEquals(expected, actual);
    }

    @Test
    public void formatTableTableStringString_formatsCorrectly() {
        String expected = "abcdabc";
        String actual = Output.formatTable(table, "abc", "d");

        assertEquals(expected, actual);
    }

    @Test
    public void formatTableRawTable_formatsCorrectly() {
        Iterable<String> expected = Arrays.asList("one, 1.0", "two, ");
        Iterable<String> actual = Output.formatTableRaw(table);

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void formatTableRaw_givenTableNull_throwsNullPointerException() {
        Output.formatTableRaw((Table) null, "");
    }

    @Test(expected = NullPointerException.class)
    public void formatTableRaw_givenFormatNull_throwsNullPointerException() {
        Output.formatTableRaw(table, (String) null);
    }

    @Test
    public void formatTableRaw_givenValidFormat_returnsIterator() {
        assertNotNull(Output.formatTableRaw(table, ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatTableRaw_givenFormatWithUnmatchedOpeningBrace_throwsIllegalArgumentException() {
        Output.formatTableRaw(table, "{");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatTableRaw_givenFormatWithUnmatchedClosingBrace_throwsIllegalArgumentException() {
        Output.formatTableRaw(table, "}");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatTableRaw_givenFormatWithUnmatchedColumn_throwsIllegalArgumentException() {
        Output.formatTableRaw(table, "{null}");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatTableRaw_givenFormatWithStuff_throwsIllegalArgumentException() {
        Output.formatTableRaw(table, "{null{");
    }

    @Test
    public void formatTableRaw_givenFormatWithEscapedOpeningBrace_formatsCorrectly() {
        Iterable<String> expected = Arrays.asList("{", "{");
        Iterable<String> actual = Output.formatTableRaw(table, "{{");

        assertEquals(expected, actual);
    }

    @Test
    public void formatTableRaw_givenFormatWithEscapedClosingBrace_formatsCorrectly() {
        Iterable<String> expected = Arrays.asList("}", "}");
        Iterable<String> actual = Output.formatTableRaw(table, "}}");

        assertEquals(expected, actual);
    }

    @Test
    public void formatTableRaw_givenFormatWithPercentage_formatsCorrectly() {
        Iterable<String> expected = Arrays.asList("%", "%");
        Iterable<String> actual = Output.formatTableRaw(table, "%");

        assertEquals(expected, actual);
    }

    @Test
    public void formatTableRaw_formatsCorrectly() {
        Iterable<String> expected = Arrays.asList("one=%{1.0}", "two=%{}");
        Iterable<String> actual = Output.formatTableRaw(table,
                "{abc}=%{{{xyz}}}");

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
    public void writeTableStringTableString_writesCorrectResult()
            throws IOException {
        Output.writeTable(file, table, "abc");

        assertEquals("abc\nabc", getOutput());
    }

    @Test
    public void writeTable_writesCorrectResult() throws Exception {
        Output.writeTable(file, table, "abc", "d");

        assertEquals("abcdabc", getOutput());
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
