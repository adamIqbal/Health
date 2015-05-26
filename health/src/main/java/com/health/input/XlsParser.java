package com.health.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class XlsParser implements Parser {
	private ArrayList<Row> list;

	/**
	 * just for testing
	 * 
	 */
	public static void main(String[] args0) {
		XlsParser testparser = new XlsParser();
		try {
			InputDescriptor config = new InputDescriptor(
					"data/configXmls/admireXlsConfig.xml");
			testparser.parse("data/data_use/ADMIRE_56_BPM.xls", config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public Table parse(String path, InputDescriptor config)
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
			//if at startrow or beyond
			if (rowCount >= startCell.getStartRow()) {
				Record tableRow = new Record(table);
				
				int columnCountTableRow =0;
				for(int i = startCell.getStartColumn()-1; i < columnsCount + startCell.getStartColumn()-1; i ++){
					tableRow.setValue(columnCountTableRow, row.getCell(i).toString());
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
