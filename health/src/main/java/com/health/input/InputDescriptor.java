package com.health.input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.health.Column;
import com.health.Table;
import com.health.ValueType;

/**
 * Represents an XML input descriptor file that describes the content and layout
 * of an input file.
 */
public final class InputDescriptor {
	private String startDelimeter;
	private String endDelimeter;
	private String delimeter;
	private String format;
	private List<String> columns;
	private List<ValueType> columnTypes;
	private StartCell startCell;
	private String dateFormat;

	/**
	 * Creates a new {@link InputDescriptor}, give the path of the input
	 * descriptor file.
	 *
	 * @param path
	 *            the path of the input descriptor file.
	 * @throws ParserConfigurationException
	 *             if a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 * @throws SAXException
	 *             if any parse error occur.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws InputException
	 *             if the input descriptor file is not formatted correctly.
	 */
	public InputDescriptor(final String path)
			throws ParserConfigurationException, SAXException, IOException,
			InputException {
		Objects.requireNonNull(path);

		columns = new ArrayList<String>();
		columnTypes = new ArrayList<ValueType>();

		Element root = parseXml(path);

		try {
			validate(root);
			extractAttributes(root);
			extractColumns(root);
		} catch (InputException ex) {
			throw new InputException(String.format(
					"An exception occured when reading input "
							+ "descriptor %s: %s", path, ex.getMessage()), ex);
		}
	}

	/**
	 * Creates an empty table based on this input descriptor.
	 *
	 * @return an empty table based on this input descriptor.
	 */
	public Table buildTable() {
		int size = columns.size();

		Column[] tableColumns = new Column[size];

		// Create the column objects
		for (int i = 0; i < size; i++) {
			tableColumns[i] = new Column(columns.get(i), i, columnTypes.get(i));
		}

		// Create a table with the columns
		return new Table(Arrays.asList(tableColumns));
	}

	/**
	 * Gets the startcell object needed for xls and xlsx files, to determine
	 * where the table starts.
	 *
	 * @return the startcell of the table.
	 */
	public StartCell getStartCell() {
		return startCell;
	}

	/**
	 * Gets the list of Columns.
	 *
	 * @return the lis of colomns.
	 */
	public List<String> getColumns() {
		return columns;
	}

	/**
     * Gets a List containing the ValueType of the columns as described in the input file
     * @return a List of ValueType objects
     */
    public List<ValueType> getColumnTypes() {
    	return columnTypes;
    }
    
	/**
	 * Gets the format of the input file. For example <code>txt</code> or
	 * <code>xlsx</code>.
	 *
	 * @return the format of the input file.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Gets the delimiter between columns of a <code>txt</code> input file.
	 *
	 * @return the delimiter between columns of a <code>txt</code> input file;
	 *         or null if there is no delimiter.
	 */
	public String getDelimiter() {
		return delimeter;
	}

	/**
	 * Gets delimiter indicating the start of the data of a <code>txt</code>
	 * input file.
	 *
	 * @return delimiter indicating the start of the data of a <code>txt</code>
	 *         input file; or null if there is no start delimiter.
	 */
	public String getStartDelimiter() {
		return startDelimeter;
	}

	/**
	 * Gets delimiter indicating the end of the data of a <code>txt</code> input
	 * file.
	 *
	 * @return delimiter indicating the end of the data of a <code>txt</code>
	 *         input file; or null if there is no end delimiter.
	 */
	public String getEndDelimiter() {
		return endDelimeter;
	}

	private Element parseXml(final String path)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse(new File(path));

		Element root = document.getDocumentElement();

		root.normalize();

		return root;
	}

	private void extractAttributes(final Element root) {
		assert root != null;

		format = root.getAttribute("format");

		if (root.hasAttribute("delimeter")) {
			delimeter = root.getAttribute("delimeter");
		}

		if (root.hasAttribute("start")) {
			startDelimeter = root.getAttribute("start");
		}

		if (root.hasAttribute("end")) {
			endDelimeter = root.getAttribute("end");
		}

		if (root.hasAttribute("startRow") && root.hasAttribute("startColumn")) {
			int startRow = Integer.parseInt(root.getAttribute("startRow"));
			int startColumn = Integer
					.parseInt(root.getAttribute("startColumn"));
			startCell = new StartCell(startRow, startColumn);
		}
	}

	private void extractColumns(final Element root) throws InputException {
		assert root != null;

		// Get the descendant column elements from the root element
		NodeList children = root.getElementsByTagName("column");

		// Populate the columns list
		for (int i = 0; i < children.getLength(); i++) {
			Element column = (Element) children.item(i);

			columns.add(column.getTextContent());
			ValueType type = getColumnType(column);
			if(type == ValueType.Date ){
				if(column.hasAttribute("format")){
					this.dateFormat = column.getAttribute("format");
				}else{
					type = ValueType.String;
				}
			}
			
			columnTypes.add(type);
			
			
		}
	}

	private static ValueType getColumnType(final Element column)
			throws InputException {
		assert column != null;

		if (!column.hasAttribute("type")) {
			return ValueType.String;
		}

		try {
			return ValueType.valueOf(column.getAttribute("type"));
		} catch (IllegalArgumentException ex) {
			throw new InputException(String.format(
					"Column '%s' has an invalid type: '%s'.",
					column.getTextContent(), column.getAttribute("type")), ex);
		}
	}

	private void validate(final Element root) throws InputException {
		assert root != null;

		// The document must contain a root element named 'data'
		if (root.getTagName() != "data") {
			throw new InputException(
					"An input descriptor file must contain a root element "
							+ "named 'data'.");
		}

		// The root element must have a an attribute named 'format'
		if (!root.hasAttribute("format")) {
			throw new InputException(
					"The root element of an input descriptor file must have an "
							+ "attribute named 'format'.");
		}

		// The input descriptor must contain at least one column
		if (root.getElementsByTagName("column").getLength() <= 0) {
			throw new InputException(
					"An input descriptor file must contain at least one column "
							+ "element.");
		}
	}

	/**
	 * @return the dateFormat.
	 */
	public String getDateFormat() {
		return dateFormat;
	}
}
