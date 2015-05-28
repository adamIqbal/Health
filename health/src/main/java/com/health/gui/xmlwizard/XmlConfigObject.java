package com.health.gui.xmlwizard;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.health.FileType;
import com.health.ValueType;

/**
 * Models a XML Config file and providing methods to write is as a String
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlConfigObject {
	public FileType type;
	public String[] values;
	public List<String> columns;
	public List<ValueType> columnTypes;
	public Path path;

	public XmlConfigObject() {
		this.path = null;
	}

	public void setColumns(List<String> columns, List<ValueType> columnTypes) {
		this.columns = columns;
		this.columnTypes = columnTypes;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public void setPath(Path xml) {
		this.path = xml;
	}
	
	public void setType(FileType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "XmlConfigObject [type=" + type + ", values="
				+ Arrays.toString(values) + ", columns=" + columns
				+ ", columnTypes=" + columnTypes + ", path=" + path + "]";
	}

	/**
	 * Formats the object as XML.
	 * 
	 * @return a String in a XML format
	 */
	public String toXMLString() {
		String xml = null;
		
		switch(this.type) {
		case TXT:
			xml = this.toXMLStringTXT();
			break;
		case XLS:
			break;
		default:
			break;
		}
		
		return xml;
	}
	
	public String toXMLStringTXT() {
		String startDelimiter = this.values[0];
		String endDelimiter = this.values[0];
		String delimiter = this.values[0];
		
		
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n\r";
		String dataStart = "<data format=\"text\" start=\""
				+ startDelimiter + "\" end=\"" + endDelimiter
				+ "\" delimeter=\"" + delimiter + "\">" + "\n\r";

		String columnTags = "";
		int n = columns.size();

		// if columns List and columntype List are not of equal length, there is
		// something wrong.
		if (n != this.columnTypes.size()) {
			return null;
		}

		for (int i = 0; i < n; i++) {
			columnTags += "\t" + "<column type=\"" + this.columnTypes.get(i)
					+ "\">" + this.columns.get(i) + "</column>" + "\n\r";
		}

		String dataEnd = "</data>";

		return header + dataStart + columnTags + dataEnd;
	}
}