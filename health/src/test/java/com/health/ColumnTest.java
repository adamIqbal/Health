package com.health;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for Column.
 */
public class ColumnTest {
    private static final String name = "column1";
    private static final int index = 0;
    private static final ValueType type = ValueType.String;

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} throws a
     * {@link NullPointerException} when given a null reference for name.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenNameNull_throwsNullPointerException() {
        new Column((String) null, index, type);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} throws a
     * {@link NullPointerException} when given a null reference for type.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenTypeNull_throwsNullPointerException() {
        new Column(name, index, (ValueType) null);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} throws an
     * {@link IllegalArgumentException} when given a negative index.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenNegativeIndex_throwsIllegalArgumentException() {
        new Column(name, -1, type);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} sets the
     * column's name when given valid arguments.
     */
    @Test
    public void constructor_givenValidArguments_setsName() {
        Column column = new Column(name, index, type);

        String expected = name;
        String actual = column.getName();

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} sets the
     * column's index when given valid arguments.
     */
    @Test
    public void constructor_givenValidArguments_setsIndex() {
        Column column = new Column(name, index, type);

        int expected = index;
        int actual = column.getIndex();

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} sets the
     * column's type when given valid arguments.
     */
    @Test
    public void constructor_givenValidArguments_setsType() {
        Column column = new Column(name, index, type);

        ValueType expected = type;
        ValueType actual = column.getType();

        assertEquals(expected, actual);
    }
}
