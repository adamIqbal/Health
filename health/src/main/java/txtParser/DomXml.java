package txtParser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomXml {
    NodeList nodeList;
    ArrayList<String> xmlColumns;
    String dir = "data/testDomParser.xml";

    public DomXml(String directory) {
        if (directory != null) {
            dir = directory;
        }
        try {
            buildXML();
        } catch (Exception e) {
            System.out.println("XML could not be build");
        }

    }

    public DomXml() {
        try {
            buildXML();
        } catch (Exception e) {
            System.out.println("XML could not be build");
        }

    }

    public ArrayList<String> getColumnsList() {
        return xmlColumns;
    }

    private void buildXML() throws Exception {
        xmlColumns = new ArrayList<String>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(dir));

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        nodeList = document.getElementsByTagName("column");

        visitEveryNode();
    }

    private void visitEveryNode() {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                xmlColumns.add(node.getTextContent());
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                sb.append("column name = " + node.getNodeName()
                        + "| content = " + node.getTextContent()
                        + "\r\n ");
            }
        }
        return sb.toString();
    }

}
