package com.health;

import java.util.Objects;

/**
 * Represents a column of a {@link Table}.
 *
 * @author Martijn
 */
public class Column {
    private final String name;
    private final int index;
    private final ValueType type;
    private boolean isFrequencyColumn;

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
        this.isFrequencyColumn = false;
    }

    /**
     * Gets the name of this column.
     *
     * @return the name of this column.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Gets the index of this column.
     *
     * @return the index of this column.
     */
    public final int getIndex() {
        return this.index;
    }

    /**
     * Gets the type of the value of this column.
     *
     * @return the type of the value of this column.
     */
    public final ValueType getType() {
        return this.type;
    }

    /**
     * Gets the boolean that describes whether this column is a frequency
     * column.
     * 
     * @return the boolean that describes whether this column is a frequency
     *         column
     */
    public final boolean getIsFrequencyColumn() {
        return this.isFrequencyColumn;
    }

    /**
     * Sets the boolean that describes whether this column is a frequency
     * column.
     * 
     * @param param
     *            the boolean that describes whether this column is a frequency
     *            column
     */
    public final void setIsFrequencyColumn(boolean param) {
        this.isFrequencyColumn = param;
    }
    
    public final boolean equals(Object other){
    	if(other instanceof Column){
    		Column that = (Column)other;
    		if((this.isFrequencyColumn && that.getIsFrequencyColumn() )||(!this.isFrequencyColumn && !that.getIsFrequencyColumn())){
    			if(this.name.equals(that.getName())){
    				if(this.type == that.getType()){
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
}
