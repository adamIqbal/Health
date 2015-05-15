package txtParser;

import java.util.ArrayList;

/**
 * Class which separates the delimiters and the columns, and creates 2
 * arraylists from them.
 * 
 *
 */

public class ConfigObject {

    private ArrayList<String> columns, delimiters;

    public ConfigObject(ArrayList<String> rawColumns) {
        delimiters = new ArrayList<String>();

        strip(rawColumns);
    }

    private void strip(ArrayList<String> rawColumns) {
        ArrayList<String> columnsStripped = new ArrayList<String>();
        for (int i = 0; i < rawColumns.size(); i++) {
            if (i != 0 && i != (rawColumns.size() - 1) && i != 1) {
                columnsStripped.add(rawColumns.get(i));
            } else {
                delimiters.add(rawColumns.get(i));
            }
        }

        columns = columnsStripped;
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public ArrayList<String> getDelimiters() {
        return delimiters;
    }
}
