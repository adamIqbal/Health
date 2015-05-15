package com.health.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.health.Record;
import com.health.Table;

/**
 * Implements a parser for text input files.
 */
public final class TextParser implements Parser {
    /**
     * Given a path to a text file and an input descriptor, parses the input
     * file into a {@link Table}.
     *
     * @param path
     *            the path of the input file.
     * @param config
     *            the input descriptor.
     * @return a table representing the parsed input file.
     * @throws IOException
     *             if any IO errors occur.
     */
    @Override
    public Table parse(final String path, final InputDescriptor config)
            throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(config);

        Table table = config.buildTable();

        List<String> lines = readLines(path, config);

        // Parse each line into a record
        for (String row : lines) {
            // Add a new record to the table
            Record record = new Record(table);

            // Split the line into columns
            String[] columns = row.split(config.getDelimiter());

            // Set the value of each column on the record
            for (int i = 0; i < columns.length; i++) {
                // TODO: Handle column's type
                record.setValue(i, columns[i].trim());
            }
        }

        return table;
    }

    private static List<String> readLines(final String path,
            final InputDescriptor config)
            throws IOException {
        assert path != null;
        assert config != null;

        BufferedReader reader = new BufferedReader(new FileReader(path));

        List<String> lines = new ArrayList<String>();

        String startDelimiter = config.getStartDelimiter();
        String endDelimiter = config.getEndDelimiter();

        String line;

        if (startDelimiter != null) {
            // Skip all lines until the start delimiter
            while ((line = reader.readLine()) != null &&
                    !line.startsWith(startDelimiter)) {
            }
        }

        // Read all lines until the end delimiter
        while ((line = reader.readLine()) != null &&
                (endDelimiter == null || !line.startsWith(endDelimiter))) {
            lines.add(line);
        }

        reader.close();

        return lines;
    }
}
