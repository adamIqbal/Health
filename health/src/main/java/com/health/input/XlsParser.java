package com.health.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.health.Record;
import com.health.Table;

/**
 * Implements a parser for xls input files.
 *
 */
public final class XlsParser implements Parser {

	/**
	 * Given a path to a xls file and an input descriptor, parses the input file
	 * into a {@link Table}.
	 *
	 * @param path
	 *            the path of the input file.
	 * @param config
	 *            the input descriptor.
	 * @return a table representing the parsed input file.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws InputException
	 *             if any input errors occur.
	 */

	@Override
	public Table parse(final String path, final InputDescriptor config)
			throws InputException, IOException {
		Objects.requireNonNull(path);
		Objects.requireNonNull(config);

		Table table = config.buildTable();

		FileInputStream io = new FileInputStream(path);
		HSSFWorkbook wb = new HSSFWorkbook(io);

		StartCell startCell = config.getStartCell();
		int rowCount = 0;
		int columnsCount = config.getColumns().size();

		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			// if at startrow or beyond
			if (rowCount >= startCell.getStartRow()) {
				Record tableRow = new Record(table);

				int columnCountTableRow = 0;
				for (int i = startCell.getStartColumn() - 1; i < columnsCount
						+ startCell.getStartColumn() - 1; i++) {
					tableRow.setValue(columnCountTableRow, row.getCell(i)
							.toString());
					columnCountTableRow++;
				}

			}

			rowCount++;
			// list.add(row);
		}

		wb.close();
		return table;

	}
}
