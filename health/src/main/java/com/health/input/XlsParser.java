package com.health.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.health.Record;
import com.health.Table;

/**
 * Implements a parser for xls input files.
 *
 */
public final class XlsParser implements Parser {

  /**
   * Given a path to a xls file and an input descriptor, parses the input file into a {@link Table}.
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
  public Table parse(final String path, final InputDescriptor config) throws InputException,
      IOException {
    Objects.requireNonNull(path);
    Objects.requireNonNull(config);

    Table table = config.buildTable();

    FileInputStream io = new FileInputStream(path);

    // String ext = getFileExtension(path);
    String ext = config.getFormat().toLowerCase();
    Workbook wb;
    if (ext.equals("xls")) {
      wb = new HSSFWorkbook(io);
    } else if (ext.equals("xlsx")) {
      wb = new XSSFWorkbook(io);
    } else {
      io.close();
      throw new InputException("Not a xls or xlsx file, so cannot parse with XLSParser");
    }

    StartCell startCell = config.getStartCell();
    int rowCount = 0;
    int columnsCount = config.getColumns().size();

    Sheet sheet = wb.getSheetAt(0);
    for (Row row : sheet) {
      // if at start row or beyond
      if (rowCount >= startCell.getStartRow()) {
        Record tableRow = new Record(table);

        int columnCountTableRow = 0;
        for (int i = startCell.getStartColumn() - 1; i < columnsCount + startCell.getStartColumn()
            - 1; i++) {
          String value;
          try {
            value = row.getCell(i).toString();
          } catch (NullPointerException e) {
            value = "NULL";
          }

          switch (table.getColumn(columnCountTableRow).getType()) {
          case String:
            if (!value.equals("NULL")) {
              tableRow.setValue(columnCountTableRow, value);
            }
            break;
          case Number:
            if (!value.equals("NULL")) {
              tableRow.setValue(columnCountTableRow, Double.parseDouble(value));
            }
            break;
          case Date:
            if (config.getDateFormat() != null) {
              try {
                DateTimeFormatter formatter = DateTimeFormatter

                .ofPattern(config.getDateFormat());
                LocalDate dateValue = LocalDate.parse(row.getCell(i).toString(), formatter);
                tableRow.setValue(columnCountTableRow, dateValue);
              } catch (DateTimeParseException e) {
                break;
              }
            }
            break;
          default:
            // The type was null, this should never happen
            assert false;
            throw new InputException("Internal error.", new Exception(
                "Column.getType() returned null."));
          }

          columnCountTableRow++;
        }

      }

      rowCount++;
    }

    wb.close();
    return table;

  }

}