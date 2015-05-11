package com.health.output;

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
     * Returns an iterable containing the formatted strings for each record in
     * the given table.
     *
     * @param table
     *            the table to format.
     * @param format
     *            a format string as described in ???.
     * @return an iterable containing the formatted strings.
     */
    public static Iterable<String> format(
            final Table table,
            final String format) {
        return null;
    }

    /**
     * Writes the given table to a file.
     *
     * @param file
     *            the file to write to.
     * @param table
     *            the table to write.
     */
    public static void writeTable(
            final String file,
            final Table table) {
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
     *            a format string, see {@link Output#format(Table, String)} for
     *            details.
     * @see Output#format(Table, String)
     */
    public static void writeTable(
            final String file,
            final Table table,
            final String format) {
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
     *            a format string, see {@link Output#format(Table, String)} for
     *            details.
     * @param recordDelimiter
     *            a String that is used separate each of the records in the
     *            formatted String.
     * @see Output#format(Table, String)
     */
    public static void writeTable(
            final String file,
            final Table table,
            final String format,
            final String recordDelimiter) {
    }

    private static String getDefaultFormat(final Table table) {
        return null;
    }
}
