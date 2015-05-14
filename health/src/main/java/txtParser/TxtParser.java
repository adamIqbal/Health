package txtParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TxtParser {

  private String dir = "src/main/txtData.xml";
  private ArrayList<String> delimiters, columns, data;

  public TxtParser(ArrayList<String> columns, ArrayList<String> delimiters) throws Exception {
    this.delimiters = delimiters;
    this.columns = columns;
    parseTxt();

  }

  public TxtParser(ArrayList<String> columns, ArrayList<String> delimiters, String directory)
      throws Exception {
    if (directory != null) {
      dir = directory;
    }
    this.delimiters = delimiters;
    this.columns = columns;
    parseTxt();
  }

  private void parseTxt() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(dir));
    while (br.readLine() != delimiters.get(0)) {
    }
    String current;
    while ((current = br.readLine()) != delimiters.get(delimiters.size())) {

      data.add(current);

    }
    br.close();
  }

  public ArrayList<String> getData() {

    return data;

  }
}
