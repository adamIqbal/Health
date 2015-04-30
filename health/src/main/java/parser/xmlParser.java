package parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class xmlParser {

	public static void main(String[] args) {
		try {
			SAXParserFactory parseFactory = SAXParserFactory.newInstance();
			SAXParser parser = parseFactory.newSAXParser();

			saxHandler handler = new saxHandler();

			parser.parse(
					ClassLoader.getSystemResourceAsStream("doc/testXml.xml"),
					handler);
		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();
		}

	}
}
