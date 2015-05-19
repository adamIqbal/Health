package com.health.input;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.health.Table;

/**
 * Implements a parser for xls input files.
 *
 */
public class XlsParser implements Parser {
  private ArrayList<Row> list;

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
  public Table parse(String path, InputDescriptor config) throws InputException, IOException {
    Objects.requireNonNull(path);
    Objects.requireNonNull(config);

    Table table = config.buildTable();

    FileInputStream io = new FileInputStream(path);
    HSSFWorkbook wb = new HSSFWorkbook(io);

    Sheet sheet = wb.getSheetAt(0);
    for (Row row : sheet) {
      list.add(row);

    }

    wb.close();
    return table;

  }
}
