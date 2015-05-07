package com.health;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Column.
 */
public class ColumnTest {
    private String defaultName;
    private int defaultIndex;
    private ValueType defaultType;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        this.defaultName = "column1";
        this.defaultIndex = 0;
        this.defaultType = ValueType.String;
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} throws a
     * {@link NullPointerException} when given a null reference for name.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenNameNull_throwsNullPointerException() {
        new Column((String) null, this.defaultIndex, this.defaultType);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} throws a
     * {@link NullPointerException} when given a null reference for type.
     */
    @Test(expected = NullPointerException.class)
    public void constructor_givenTypeNull_throwsNullPointerException() {
        new Column(this.defaultName, this.defaultIndex, (ValueType) null);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} throws an
     * {@link IllegalArgumentException} when given a negative index.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_givenNegativeIndex_throwsIllegalArgumentException() {
        new Column(this.defaultName, -1, this.defaultType);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} sets the
     * column's name when given valid arguments.
     */
    @Test
    public void constructor_givenValidArguments_setsName() {
        Column column = new Column(
                this.defaultName,
                this.defaultIndex,
                this.defaultType);

        String expected = this.defaultName;
        String actual = column.getName();

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} sets the
     * column's index when given valid arguments.
     */
    @Test
    public void constructor_givenValidArguments_setsIndex() {
        Column column = new Column(
                this.defaultName,
                this.defaultIndex,
                this.defaultType);

        int expected = this.defaultIndex;
        int actual = column.getIndex();

        assertEquals(expected, actual);
    }

    /**
     * Tests whether {@link Column#Column(String, int, ValueType)} sets the
     * column's type when given valid arguments.
     */
    @Test
    public void constructor_givenValidArguments_setsType() {
        Column column = new Column(
                this.defaultName,
                this.defaultIndex,
                this.defaultType);

        ValueType expected = this.defaultType;
        ValueType actual = column.getType();

        assertEquals(expected, actual);
    }
}
