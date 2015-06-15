package com.health.operations.functions;

import static org.junit.Assert.*;
import static com.health.operations.functions.ConstrainFunctions.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.health.script.runtime.DateValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.Value;

public class ConstrainFunctionsTest {
  double columns;
  NumberValue value;

  LocalDate date;
  DateValue valueDate = new DateValue();

  @Before
  public void setUp() {

    columns = 3;
    value = new NumberValue();
    value.setValue(2);

    date = LocalDate.now();
    valueDate = new DateValue();

  }

  @Test
  public void testGreaterDate() {
    assertTrue(greater((Object) date, (Value) valueDate));

    assertFalse(greater((Object) date, null));
    assertFalse(greater(null, (Value) value));

    valueDate.setValue(date);

    assertFalse(greater((Object) date, (Value) valueDate));

  }

  @Test
  public void testGreaterEqDate() {
    assertTrue(greaterEq((Object) date, (Value) valueDate));

    valueDate.setValue(date);

    assertTrue(greaterEq((Object) date, (Value) valueDate));
    assertFalse(greaterEq((Object) date, null));
    assertFalse(greaterEq(null, (Value) value));

  }

  //
  // @Test
  // public void testSmallerEqDate() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testSmallerDate() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testEqualDate() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testGreaterString() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testGreaterEqString() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testSmallerEqString() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testSmallerString() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testEqualString() {
  // fail("Not yet implemented");
  // }

  @Test
  public void testGreaterNumber() {

    assertTrue(greater((Object) columns, (Value) value));

    value.setValue(3);

    assertFalse(greater((Object) columns, (Value) value));

    assertFalse(greater((Object) columns, (Value) value));
    assertFalse(greater((Object) columns, null));
    assertFalse(greater(null, (Value) value));

    columns = 4;

    assertTrue(greater((Object) columns, (Value) value));
  }

  @Test
  public void testGreaterEqNumber() {
    assertTrue(greaterEq((Object) columns, (Value) value));

    value.setValue(3);

    assertTrue(greaterEq((Object) columns, (Value) value));
    assertFalse(greaterEq((Object) columns, null));
    assertFalse(greaterEq(null, (Value) value));

    columns = 4;

    assertTrue(greaterEq((Object) columns, (Value) value));
    value.setValue(4);
    assertTrue(greaterEq((Object) columns, (Value) value));

    columns = 1;

    assertFalse(greaterEq((Object) columns, (Value) value));

  }

  @Test
  public void testSmallerEqNumber() {
    assertTrue(greater((Object) columns, (Value) value));

    value.setValue(3);

    assertTrue(smallerEq((Object) columns, (Value) value));

    assertTrue(smallerEq((Object) columns, (Value) value));
    assertFalse(smallerEq((Object) columns, null));
    assertFalse(smallerEq(null, (Value) value));

    columns = 4;

    assertFalse(smallerEq((Object) columns, (Value) value));
    value.setValue(4);
    assertTrue(smallerEq((Object) columns, (Value) value));

    columns = 1;

    assertTrue(smallerEq((Object) columns, (Value) value));
  }

  @Test
  public void testSmallerNumber() {
    assertFalse(smaller((Object) columns, (Value) value));

    value.setValue(3);

    assertFalse(smaller((Object) columns, (Value) value));

    assertFalse(smaller((Object) columns, (Value) value));
    assertFalse(smaller((Object) columns, null));
    assertFalse(smaller(null, (Value) value));

    columns = 400;

    assertFalse(smaller((Object) columns, (Value) value));
    value.setValue(400);
    assertFalse(smaller((Object) columns, (Value) value));

    columns = 100;

    assertTrue(smaller((Object) columns, (Value) value));
  }

  @Test
  public void testEqualNumber() {
    assertFalse(equal((Object) columns, (Value) value));

    value.setValue(3);

    assertTrue(equal((Object) columns, (Value) value));

    assertTrue(equal((Object) columns, (Value) value));
    assertFalse(equal((Object) columns, null));
    assertFalse(equal(null, (Value) value));

    columns = 10;

    assertFalse(equal((Object) columns, (Value) value));
    value.setValue(10);
    assertTrue(equal((Object) columns, (Value) value));

    columns = 1;

    assertFalse(equal((Object) columns, (Value) value));
  }
}
