package com.health.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
   * Given a path to a text file and an input descriptor, parses the input file into a {@link Table}
   * .
   *
   * @param path
   *          the path of the input file.
   * @param config
   *          the input descriptor.
   * @return a table representing the parsed input file.
   * @throws IOException
   *           if any IO errors occur.
   * @throws InputException
   *           if any input errors occur.
   */
  @Override
  public Table parse(final String path, final InputDescriptor config) throws IOException,
      InputException {
    Objects.requireNonNull(path);
    Objects.requireNonNull(config);

    Table table = config.buildTable();

    List<String> lines = readLines(path, config);

    // Parse each line into a record
    for (int i = 0; i < lines.size(); i++) {
      // Add a new record to the table
      Record record = new Record(table);

      // Split the line into columns
      String[] columns = lines.get(i).split(config.getDelimiter());

      int numColumns = Math.min(columns.length, table.getColumns().size());

      // Set the value of each column on the record
      for (int j = 0; j < numColumns; j++) {
        String value = columns[j].trim();

        // Convert the value to the correct type and insert it
        switch (table.getColumn(j).getType()) {
        case String:
          record.setValue(j, value);
          break;
        case Number:
          record.setValue(j, stringToNumber(value, i));
          break;
        case Date:
          if (config.getDateFormat() != null) {
            try {
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern(config.getDateFormat());
              LocalDate dateValue = LocalDate.parse(value, formatter);
              record.setValue(j, dateValue);
            } catch (DateTimeParseException e) {
              break;
            }
          } else {
            break;
          }
          break;
        default:
          // The type was null, this should never happen
          assert false;
          throw new InputException("Internal error.", new Exception(
              "Column.getType() returned null."));
        }
      }
    }

    table = deleteLastLines(table, config);

    return table;
  }

  private static Double stringToNumber(final String value, final int lineNumber)
      throws InputException {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException ex) {
      String message = String.format("Input contained an invalid number on" + " line %d.",
          lineNumber + 1);

      throw new InputException(message, ex);
    }
  }

  private static List<String> readLines(final String path, final InputDescriptor config)
      throws IOException {
    assert path != null;
    assert config != null;

    BufferedReader reader = new BufferedReader(new FileReader(path));

    List<String> lines = new ArrayList<String>();

    String startDelimiter = config.getStartDelimiter();
    String endDelimiter = config.getEndDelimiter();

    // Skip all lines until the start delimiter
    if (startDelimiter != null) {
      while (true) {
        String line = reader.readLine();

        if (line == null || line.startsWith(startDelimiter)) {
          break;
        }
      }
    }

    // Read all lines until the end delimiter
    while (true) {
      String line = reader.readLine();

      if (line == null || (endDelimiter != null && line.startsWith(endDelimiter))) {
        break;
      }

      lines.add(line);
    }

    reader.close();

    return lines;
  }
}
