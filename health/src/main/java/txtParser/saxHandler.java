package txtParser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class saxHandler extends DefaultHandler {

	public void startElement(String start, String[] attributes) throws SAXException {

		
		// for loop which puts all the attributes in the defining place. Maybe not meant to be handled here.
		
		
		switch (start) {
		case "Crea":
			break;
	
		}
	}

	public void endElement(String end) throws SAXException {
		switch (end) {
		case "Crea":
			break;

		}
	}
}
