package com.health.gui.xmlwizard;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.health.FileType;
import com.health.ValueType;

/**
 * Models a XML Config file and providing methods to write is as a String.
 * @author Bjorn van der Laan
 *
 */
public class XmlConfigObject {
    private FileType type = null;
    private String[] values = null;
    private List<String> columns = null;
    private List<ValueType> columnTypes = null;
    private Path path = null;
    private String dateFormat = null;

    /**
     * Constructs a XmlConfigObject object.
     */
    public XmlConfigObject() {

    }

    /*
     * replaced by two seperate setters public void setColumns(List<String>
     * columns, List<ValueType> columnTypes) { this.columns = columns;
     * this.columnTypes = columnTypes; }
     */

    @Override
    public final String toString() {
        return "XmlConfigObject [type=" + type + ", values="
                + Arrays.toString(values) + ", columns=" + columns
                + ", columnTypes=" + columnTypes + ", path=" + path + "]";
    }

    /**
     * Calls a toXMLString variant method based on the format attribute.
     * @return a String in a XML format
     */
    public final String toXMLString() {
        String xml = null;

        switch (this.type) {
        case TXT:
            xml = this.toXMLStringTXT();
            break;
        case XLS:
            xml = this.toXMLStringXLS();
            break;
        default:
            break;
        }

        return xml;
    }

    /**
     * Generates XML string of a config XML describing a TXT data set.
     * @return XML string of the config XML
     */
    public final String toXMLStringTXT() {
        String startDelimiter = this.values[0];
        String endDelimiter = this.values[1];
        String delimiter = this.values[2];

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n\r";
        String dataStart = "<data format=\"text\" start=\"" + startDelimiter
                + "\" end=\"" + endDelimiter + "\" delimeter=\"" + delimiter
                + "\">" + "\n\r";

        String columnTags = this.columnsToXML();

        String dataEnd = "</data>";

        return header + dataStart + columnTags + dataEnd;
    }

    /**
     * Generates XML string of a config XML describing a XLS data set.
     * @return XML string of the config XML
     */
    public final String toXMLStringXLS() {
        String startRow = this.values[0];
        String startCol = this.values[1];

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n\r";
        String dataStart = "<data format=\"xls\" startRow=\"" + startRow
                + "\" startColumn=\"" + startCol + "\">" + "\n\r";

        String columnTags = this.columnsToXML();

        String dataEnd = "</data>";

        return header + dataStart + columnTags + dataEnd;
    }
    
    private final String columnsToXML() {
        String columnTags = "";
        int n = columns.size();

        // if columns List and columntype List are not of equal length, there is
        // something wrong.
        if (n != this.columnTypes.size()) {
            return null;
        }

        for (int i = 0; i < n; i++) {
            if (columnTypes.get(i) == ValueType.Date) {
                String input = this.columns.get(i);
                String[] parts = this.splitString(input, "[");
                
                columnTags += "\t" + "<column type=\""
                        + this.columnTypes.get(i) + "\" format = \"" + parts[1] + "\">" + parts[0]
                        + "</column>" + "\n\r";
            } else {
                columnTags += "\t" + "<column type=\""
                        + this.columnTypes.get(i) + "\">" + this.columns.get(i)
                        + "</column>" + "\n\r";
            }
        }
        return columnTags;
    }

    /**
     * Gets the value of the type attribute.
     * @return the type attribute
     */
    protected final FileType getType() {
        return type;
    }

    /**
     * Sets the value of the type attribute.
     * @param type
     *            new value of type
     */
    protected final void setType(final FileType type) {
        this.type = type;
    }
    

    protected String getDateFormat() {
        return dateFormat;
    }

    protected void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Gets the value of the values attribute.
     * @return the type attribute
     */
    protected final String[] getValues() {
        return values;
    }

    /**
     * Sets the value of the values attribute.
     * @param values
     *            new value of values
     */
    protected final void setValues(final String[] values) {
        this.values = values;
    }

    /**
     * Gets the value of the columns attribute.
     * @return the type attribute
     */
    protected final List<String> getColumns() {
        return columns;
    }

    /**
     * Sets the value of the columns attribute.
     * @param columns
     *            new value of columns
     */
    protected final void setColumns(final List<String> columns) {
        this.columns = columns;
    }

    /**
     * Gets the value of the columnTypes attribute.
     * @return the type attribute
     */
    protected final List<ValueType> getColumnTypes() {
        return columnTypes;
    }

    /**
     * Sets the value of the columnTypes attribute.
     * @param columnTypes
     *            new value of columnTypes
     */
    protected final void setColumnTypes(final List<ValueType> columnTypes) {
        this.columnTypes = columnTypes;
    }

    /**
     * Gets the value of the path attribute.
     * @return the type attribute
     */
    protected final Path getPath() {
        return path;
    }

    /**
     * Sets the value of the path attribute.
     * @param path
     *            new value of path
     */
    protected final void setPath(final Path path) {
        this.path = path;
    }

    private String[] splitString(String source, String splitter)
    {
      String[] rv = new String[2];
      int last = 0;
      int next = 0;

      next = source.indexOf(splitter, last);
      if (next != -1)
      {
        // isolate from last thru before next
        rv[0] = source.substring(last, next);
        last = next + splitter.length();
      }

      if (last < source.length())
      {
        rv[1] = source.substring(last, source.length()-1);
      }

      // convert to array
      return rv;
    }
}
