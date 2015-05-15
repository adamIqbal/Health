package txtParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

public class StartInput {
    ArrayList<String> input, delimiters, columns, data;

    public StartInput(String xmlPath, String txtPath) throws Exception {

        DomXml xml = new DomXml();
        // domXML xml = new domXML(xmlPath);

        input = xml.xmlColumns;

        System.out.println(input);
        ConfigObject co = new ConfigObject(input);
        delimiters = co.getDelimiters();
        columns = co.getColumns();

        TxtParser tp = new TxtParser(columns, delimiters);
        // TxtParser tp = new TxtParser(columns, delimiters, txtPath);

        data = tp.getData();

    }

    public Table getTable() {
        Table table = new Table(getColumns());
        List<Column> columns = table.getColumns();

        for (String row : data) {
            Record record = new Record(table);
            String[] fields = row.split(delimiters.get(1));

            for (int i = 0; i < fields.length; i++) {
                record.setValue(columns.get(i).getName(), fields[i].trim());
            }
        }

        return table;
    }

    private List<Column> getColumns() {
        int size = columns.size();
        Column[] tableColumns = new Column[size];

        for (int i = 0; i < size; i++) {
            tableColumns[i] = new Column(columns.get(i), i, ValueType.String);
        }

        return Arrays.asList(tableColumns);
    }
}
