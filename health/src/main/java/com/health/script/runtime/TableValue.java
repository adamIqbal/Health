package com.health.script.runtime;

import java.util.Objects;

import com.health.Table;

/**
 * Represents a table value in the script.
 */
public final class TableValue extends ComplexValue {
    private static ScriptType type;
    private final Table table;

    static {
        ScriptTypeBuilder table = new ScriptTypeBuilder();
        table.setTypeName("Table");
        table.defineConstructor((args) -> null);
        table.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(((TableValue) args[0]).table.toString());
                }));

        TableValue.type = table.buildType();
    }

    /**
     * Creates a new value with the given underlying table.
     *
     * @param table
     *            the table.
     */
    public TableValue(final Table table) {
        super(TableValue.type);

        Objects.requireNonNull(table);

        this.table = table;
    }

    /**
     * Gets the underlying table of this script table.
     *
     * @return the underlying table of this script table.
     */
    public Table getValue() {
        return this.table;
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link TableValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link TableValue}.
     */
    public static ScriptType getStaticType() {
        return TableValue.type;
    }
}
