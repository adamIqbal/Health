package com.health;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Column.
 */
public class ColumnTest {
    private static final String name = "column1";
    private static final int index = 0;
    private static final ValueType type = ValueType.String;
    private Column column;

    @Before
    public void setUp() {
        this.column = new Column(name, index, type);
    }

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

    @Test
    public void isFrequencyColumn_givenRegularName_returnsFalse() {
        assertEquals(false, column.isFrequencyColumn());
    }

    @Test
    public void isFrequencyColumn_givenNameStartingWithCount_returnsTrue() {
        Column column = new Column("count_" + name, index, type);

        assertEquals(true, column.isFrequencyColumn());
    }

    public void equals_givenNull_returnsFalse() {
        assertEquals(false, column.equals(null));
    }

    @Test
    public void equals_givenObjectThatIsNotColumn_returnsFalse() {
        assertEquals(false, column.equals(new Object()));
    }

    @Test
    public void equals_givenSameColumn_returnsTrue() {
        assertEquals(false, column.equals(column));
    }

    @Test
    public void equals_givenEqualColumn_returnsTrue() {
        Column equalColumn = new Column(name, index, type);

        assertEquals(true, column.equals(equalColumn));
    }

    @Test
    public void equals_givenColumnWithDifferentName_returnsFalse() {
        Column inequalColumn = new Column("column2", index, type);

        assertEquals(false, column.equals(inequalColumn));
    }

    @Test
    public void equals_givenColumnWithDifferentIndex_returnsFalse() {
        Column inequalColumn = new Column(name, 1, type);

        assertEquals(false, column.equals(inequalColumn));
    }

    @Test
    public void equals_givenColumnWithDifferentType_returnsFalse() {
        Column inequalColumn = new Column(name, index, ValueType.Number);

        assertEquals(false, column.equals(inequalColumn));
    }

    @Test
    public void hashCode_forEqualColumns_returnsSameHash() {
        int expected = column.hashCode();
        int actual = new Column(name, index, type).hashCode();

        assertEquals(expected, actual);
    }
}
