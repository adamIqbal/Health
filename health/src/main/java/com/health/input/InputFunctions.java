package com.health.input;

import java.util.List;

import com.health.Record;
import com.health.Table;

/**
 * A class for functions with can be called on all input formats.
 */
public class InputFunctions {

    /**
     * a never used constructor.
     */
    protected InputFunctions() {
    }

    /**
     * Deletes the last x lines which are not needed as specified by the user.
     *
     * @param table
     *            Gets the table with the redundant lines.
     * @param config
     *            Gets the InputDescriptor which is used to create the original
     *            file.
     * @return the new table with deleted lines.
     */
    public static Table deleteLastLines(final Table table,
            final InputDescriptor config) {
        int deletions = config.getIgnoreLast();
        int size = table.size() - 1;
        List<Record> tab = table.getRecords();

        while (deletions > 0) {
            table.removeRecord(tab.get(size));
            size--;
            deletions--;
        }

        return table;
    }
}
