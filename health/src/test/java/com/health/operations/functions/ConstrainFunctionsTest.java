package com.health.operations.functions;

import static org.junit.Assert.*;
import static com.health.operations.functions.ConstrainFunctions.*;

import org.junit.Test;

import com.health.script.runtime.NumberValue;
import com.health.script.runtime.Value;

public class ConstrainFunctionsTest {

  @Test
  public void testGreaterNumber() {
    double columns = 3;
    NumberValue value = new NumberValue();
    value.setValue(2);

    assertTrue(greater((Object) columns, (Value) value));

    value.setValue(3);

    assertFalse(greater((Object) columns, (Value) value));

    assertFalse(greater((Object) columns, (Value) value));
    assertFalse(greater((Object) columns, null));
    assertFalse(greater(null, (Value) value));

    columns = 4;

    assertTrue(greater((Object) columns, (Value) value));
  }

//  @Test
//  public void testGreaterEqNumber() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testSmallerEqNumber() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testSmallerNumber() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testEqualNumber() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testGreaterDate() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testGreaterEqDate() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testSmallerEqDate() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testSmallerDate() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testEqualDate() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testGreaterString() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testGreaterEqString() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testSmallerEqString() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testSmallerString() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testEqualString() {
//    fail("Not yet implemented");
//  }

}
