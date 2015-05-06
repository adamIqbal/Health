package txtParser;

import java.util.ArrayList;

/**
 * Class which separates the delimiters and the columns, and creates 2 arraylists from them.
 * 
 *
 */

public class ConfigObject {

  public ArrayList<String> columns, delimiters;

  public void columns(ArrayList<String> columns) {
    this.columns = columns;
    columns = strip(columns);
  }

  public ArrayList<String> strip(ArrayList<String> columns2) {
    ArrayList<String> columnsStripped = new ArrayList<String>();
    for (int i = 0; i < columns2.size() - 1; i++) {
      if (i != 0 & i != columns2.size() - 1) {
        columnsStripped.add(columns2.get(i));
      } else {
        delimiters.add(columns2.get(i));
      }
    }

    columns = columnsStripped;
    return columns;
  }

  public ArrayList<String> getColumns() {
    return columns;
  }

  public ArrayList<String> getDelimiters() {
    return delimiters;
  }
}
