package txtParser;

import java.util.ArrayList;

public class StartInput {
  ArrayList<String> input, delimiters, columns, data;

  public void inputStarter(String xmlPath, String txtPath) throws Exception {

    domXML xml = new domXML();
    // domXML xml = new domXML(xmlPath);

    input = xml.xmlColumns;

    ConfigObject co = new ConfigObject(input);
    delimiters = co.getDelimiters();
    columns = co.getColumns();

    TxtParser tp = new TxtParser(columns, delimiters);
    // TxtParser tp = new TxtParser(columns, delimiters, txtPath);

    data = tp.getData();
    
  }
}
