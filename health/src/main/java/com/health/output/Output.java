package com.health.output;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.health.Column;
import com.health.Record;
import com.health.Table;

/**
 * Utility class for writing and formatting output data.
 *
 * @author Martijn
 */
public final class Output {
    private Output() {
    }

    /**
     * <p>
     * Returns an iterable containing the formatted strings for each record in
     * the given table.
     * </p>
     * <p>
     * Example:<br>
     * <code>
     * &nbsp;&nbsp;&nbsp;&nbsp;Table table = ...;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;Iterable&#60;String&#62; result =
     * Output.formatTable(table, "{Date} - creatinine level: {Creatinine}");<br>
     * </code><br>
     * The table in the example must at least contain a column named "Date" and
     * "Creatinine".
     * </p>
     *
     * @param table
     *            the table to format.
     * @param format
     *            a format string.
     * @return an iterable containing the formatted strings.
     */
    public static Iterable<String> formatTable(
            final Table table,
            final String format) {
        Objects.requireNonNull(table);
        Objects.requireNonNull(format);

        List<String> result =
                new ArrayList<String>(/* table.getRecords().size() */);
        List<GetValueClosure> getValueClosures =
                new ArrayList<GetValueClosure>();

        // Process the format string into a format compatible with String.format
        // and create a list of closures that will be used to fill in the
        // argument list for String.format
        String javaFormat = buildFormatString(table, format, getValueClosures);

        // Format each record
        for (Record record : table.getRecords()) {
            Object[] args = new Object[getValueClosures.size()];

            // Process the getValue closures to retrieve the value for each
            // column specified in the format string and build up the argument
            // list for String.format
            int size = getValueClosures.size();
            for (int i = 0; i < size; i++) {
                args[i] = getValueClosures.get(i).invoke(record);
            }

            // Use String.format to format the record and add it to the result
            result.add(String.format(javaFormat, args));
        }

        return result;
    }

    /**
     * Writes the given table to a file.
     *
     * @param file
     *            the file to write to.
     * @param table
     *            the table to write.
     * @throws IOException
     *             if an I/O error occurs writing to or creating the file.
     */
    public static void writeTable(
            final String file,
            final Table table) throws IOException {
        Output.writeTable(file, table, Output.getDefaultFormat(table));
    }

    /**
     * Writes the given table to a file according to the specified format.
     *
     * @param file
     *            the file to write to.
     * @param table
     *            the table to write.
     * @param format
     *            a format string, see {@link Output#formatTable(Table, String)}
     *            for details.
     * @throws IOException
     *             if an I/O error occurs writing to or creating the file.
     * @see Output#formatTable(Table, String)
     */
    public static void writeTable(
            final String file,
            final Table table,
            final String format) throws IOException {
        Output.writeTable(file, table, format, "\n");
    }

    /**
     * Writes the given table to a file according to the specified format.
     *
     * @param file
     *            the file to write to.
     * @param table
     *            the table to write.
     * @param format
     *            a format string, see {@link Output#formatTable(Table, String)}
     *            for details.
     * @param recordDelimiter
     *            a String that is used separate each of the records in the
     *            formatted String.
     * @throws IOException
     *             if an I/O error occurs writing to or creating the file.
     * @see Output#formatTable(Table, String)
     */
    public static void writeTable(
            final String file,
            final Table table,
            final String format,
            final String recordDelimiter) throws IOException {
        // Join each record on the given delimiter
        String output = String.join(recordDelimiter,
                Output.formatTable(table, format));

        // Create the directory for the file
        File parentFile = new File(file).getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }

        // Create and write the file
        Files.write(Paths.get(file), Arrays.asList(output));
    }

    private static String getDefaultFormat(final Table table) {
        assert table != null;

        StringBuilder format = new StringBuilder();
        boolean first = true;

        // Add braces around every column's name and join the columns on ", "
        for (Column column : table.getColumns()) {
            if (first) {
                first = false;
            } else {
                format.append(", ");
            }

            format.append('{');
            format.append(column.getName());
            format.append('}');
        }

        return format.toString();
    }

    /**
     * Builds a format string for {@link String#format(String, Object...)} and
     * fills in the given list of getValue closures so that it can be used to
     * build the argument list for {@link String#format(String, Object...)} for
     * a given record.
     *
     * @param table
     *            the table for which to build the format string.
     * @param format
     *            the format string.
     * @param getValueClosures
     *            [out] a list that will contain a getValue closure in place of
     *            every column specifier in the given format.
     * @return a format string compatible with
     *         {@link String#format(String, Object...)}.
     */
    private static String buildFormatString(final Table table,
            final String format,
            final List<GetValueClosure> getValueClosures) {
        assert table != null;
        assert format != null;
        assert getValueClosures != null;

        Reader reader = new StringReader(format);
        StringBuilder formatBuilder = new StringBuilder();

        // Process the format string
        while (true) {
            // Read the next character
            int cur = read(reader);

            // If the EOF is reached, break from the loop
            if (cur == -1) {
                break;
            }

            if (cur == '{') {
                // '{' indicates the start of a column specifier

                // Read the next character
                cur = read(reader);

                // "{{" was read, which is the escape sequence for '{'
                if (cur == '{') {
                    formatBuilder.append('{');
                    continue;
                }

                // Otherwise (try to) read the column specifier
                String column = readColumnSpecifier(reader, cur);

                // Verify that the column matches a column in the table
                if (table.getColumn(column) == null) {
                    throw new IllegalArgumentException(
                            "Could not find column %s");
                }

                // Append a format specifier for the column and add a
                // GetValueClosure to get the value for that column
                formatBuilder.append("%s");
                getValueClosures.add(new GetValueClosure(column));
            } else if (cur == '}') {
                // '}' indicates the end of a column specifier

                // Since we are currently not reading a column specifier it must
                // be follower by another '}' to to form the escape sequence
                // "}}", otherwise throw an exception
                if (read(reader) == '}') {
                    formatBuilder.append('}');
                } else {
                    throw new IllegalArgumentException(
                            "Input string was not in a correct format.");
                }
            } else if (cur == '%') {
                // '%' indicates a format specifier in String.format, so it must
                // be escaped
                formatBuilder.append("%%");
            } else {
                // Otherwise just append the character
                formatBuilder.append((char) cur);
            }
        }

        return formatBuilder.toString();
    }

    /**
     * Reads a column specifier from the given reader with the specified initial
     * character.
     *
     * @param reader
     *            the reader to read from.
     * @param initialCharacter
     *            the first character inside the column specifier.
     * @return the name of the column.
     */
    private static String readColumnSpecifier(
            final Reader reader,
            final int initialCharacter) {
        StringBuilder columnBuilder = new StringBuilder();

        // Read the initial character
        int cur = initialCharacter;

        // Read the column specifier
        do {
            if (cur == -1 || cur == '{') {
                // EOF or '{' was found inside a column specifier
                throw new IllegalArgumentException(
                        "Input string was not in a correct format.");
            } else if (cur == '}') {
                // '}' indicates the end of a column specifier
                break;
            } else {
                // Otherwise append the character
                columnBuilder.append((char) cur);
            }

            // Read the next character
            cur = read(reader);
        } while (true);

        return columnBuilder.toString();
    }

    /**
     * Reads a single character from the given reader. Returns -1 if the end of
     * stream has been reached or an IOException occurred.
     *
     * @param reader
     *            the reader to read from.
     * @return the character read read as an integer in the range 0 to 65535
     *         (0x00-0xffff), or -1 if the end of the stream has been reached or
     *         an IOException occurred.
     */
    private static int read(final Reader reader) {
        try {
            return reader.read();
        } catch (IOException ex) {
            // In StringReader an IOException will happen iff read(), ready(),
            // mark() or reset() is invoked after calling close().
            // This will never happen unless there is a fault in the code
            assert false;

            return -1;
        }
    }

    /**
     * Closure for a {@link Record#getValue(String)} invocation on a given
     * record.
     */
    private static class GetValueClosure {
        private final String column;

        public GetValueClosure(final String column) {
            assert column != null;

            this.column = column;
        }

        public String invoke(final Record record) {
            assert record != null;

            Object value = record.getValue(this.column);

            if (value == null) {
                return "";
            } else {
                return value.toString();
            }
        }
    }
}
