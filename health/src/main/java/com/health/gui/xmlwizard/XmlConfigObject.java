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
	
	public String toXMLString() {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n\r";
		String dataStart = "<data format=\"text\" start=\""+ this.startDelimiter +"\" end=\""+this.endDelimiter+"\" delimeter=\""+this.delimiter+"\">"+"\n\r";
		
		String columnTags = "";
		int n = columns.size();
		
		//if columns List and columntype List are not of equal length, there is something wrong.
		if(n != this.columnTypes.size()) {
			return null;
		}
		
		for(int i = 0; i < n; i++) {
			columnTags += "\t"+"<column type=\""+this.columnTypes.get(i)+"\">"+this.columns.get(i)+"</column>"+"\n\r";
		}
		
		String dataEnd = "</data>";
		
		return header+dataStart+columnTags+dataEnd;
	}
}