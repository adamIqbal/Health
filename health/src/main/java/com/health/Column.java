package com.health;

import java.util.Objects;

/**
 * Represents a column of a {@link Table}.
 *
 * @author Martijn
 */
public final class Column {
    private final String name;
    private final int index;
    private final ValueType type;

    /**
     * Constructs a new column with the given name, index and type.
     *
     * @param name
     *            the name of the column.
     * @param index
     *            the index of the column.
     * @param type
     *            the type of the value of the column.
     * @throws NullPointerException
     *             if name is null;
     * @throws IllegalArgumentException
     *             if index is negative.
     */
    public Column(final String name, final int index, final ValueType type) {
        Objects.requireNonNull(name, "Argument name cannot be null.");
        Objects.requireNonNull(type, "Argument type cannot be null.");

        if (index < 0) {
            throw new IllegalArgumentException(
                    "Argument index cannot be negative.");
        }

        this.name = name;
        this.index = index;
        this.type = type;
    }

    /**
     * Gets the name of this column.
     *
     * @return the name of this column.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the index of this column.
     *
     * @return the index of this column.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Gets the type of the value of this column.
     *
     * @return the type of the value of this column.
     */
    public ValueType getType() {
        return this.type;
    }

    /**
     * Gets the boolean that describes whether this column is a frequency
     * column.
     *
     * @return the boolean that describes whether this column is a frequency
     *         column
     */
    public boolean isFrequencyColumn() {
        return this.name.startsWith("count_");
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Column)) {
            return false;
        }

        Column that = (Column) other;

        return this.type == that.type
                && this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.index, this.name, this.type);
    }
}
