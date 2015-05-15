package com.health;

import java.util.Comparator;

/**
 * Implements a comparison function to compare two {@link Column}s.
 *
 * @author Martijn
 */
public class ColumnComparator implements Comparator<Column> {

    /**
     * Compares two columns based on their indices.
     *
     * @param c1
     *            the first column to compare.
     * @param c2
     *            the second column to compare.
     * @return the value 0 if <code>c1.getIndex() == c2.getIndex()</code>; a
     *         value less than 0 if
     *         <code>c1.getIndex() &#60; c2.getIndex()</code>; and a value
     *         greater than 0 if <code>c1.getIndex() &#62; c2.getIndex()</code>.
     */
    @Override
    public final int compare(final Column c1, final Column c2) {
        return Integer.compare(c1.getIndex(), c2.getIndex());
    }
}
