package com.health;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for Column.
 */
public class ColumnTest {
	/**
	 * Tests whether {@link Column#Column(String, int, ValueType)} throws a {@link NullPointerException}
	 * when given a null reference.
	 */
	@Test(expected=NullPointerException.class)
	public void constructor_givenNameNull_throwsNullPointerException() {
		String name = null;
		int index = 0;
		ValueType type = ValueType.Number;
		
		new Column(name, index, type);
	}
	
	/**
	 * Tests whether {@link Column#Column(String, int, ValueType)} throws an {@link IllegalArgumentException}
	 * when given a negative index.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void constructor_givenNegativeIndex_throwsIllegalArgumentException() {
		String name = "col1";
		int index = -1;
		ValueType type = ValueType.Number;
		
		new Column(name, index, type);
	}
	
	/**
	 * Tests whether {@link Column#Column(String, int, ValueType)} sets the column's name
	 * when given valid arguments.
	 */
	@Test
	public void constructor_givenValidArguments_setsName() {
		String name = "col1";
		int index = 0;
		ValueType type = ValueType.Number;
		
		Column column = new Column(name, index, type);
		
		String expected = name;
		String actual = column.getName();
		
		assertEquals(expected, actual);
	}

	/**
	 * Tests whether {@link Column#Column(String, int, ValueType)} sets the column's index
	 * when given valid arguments.
	 */
	@Test
	public void constructor_givenValidArguments_setsIndex() {
		String name = "col1";
		int index = 1;
		ValueType type = ValueType.Number;
		
		Column column = new Column(name, index, type);
		
		int expected = index;
		int actual = column.getIndex();
		
		assertEquals(expected, actual);
	}

	/**
	 * Tests whether {@link Column#Column(String, int, ValueType)} sets the column's type
	 * when given valid arguments.
	 */
	@Test
	public void constructor_givenValidArguments_setsType() {
		String name = "col1";
		int index = 0;
		ValueType type = ValueType.Number;
		
		Column column = new Column(name, index, type);
		
		ValueType expected = type;
		ValueType actual = column.getType();
		
		assertEquals(expected, actual);
	}
}
