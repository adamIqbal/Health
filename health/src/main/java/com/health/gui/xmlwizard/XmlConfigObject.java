package com.health.gui.xmlwizard;

import java.nio.file.Path;
import java.util.List;

import com.health.ValueType;

public class XmlConfigObject {
	public String startDelimiter, endDelimiter, delimiter;
	public List<String> columns;
	public List<ValueType> columnTypes;
	public Path path;

	public XmlConfigObject() {
		
	}
	
	public void setDelimiters(String startDelim, String endDelim, String delim) {
		this.startDelimiter = startDelim;
		this.endDelimiter = endDelim;
		this.delimiter = delim;		
	}
	
	public void setColumns(List<String> columns, List<ValueType> columnTypes) {
		this.columns = columns;
		this.columnTypes = columnTypes;
	}
	
	public void setPath(Path xml) {
		this.path = xml;
	}
	
	@Override
	public String toString() {
		return "XmlConfigObject [startDelimiter=" + startDelimiter
				+ ", endDelimiter=" + endDelimiter + ", delimiter=" + delimiter
				+ ", columns=" + columns + ", columnTypes=" + columnTypes
				+ ", path=" + path + "]";
	}
}