package com.health.operations.functions;

import static com.health.operations.functions.ConstrainFunctions.equal;
import static com.health.operations.functions.ConstrainFunctions.greater;
import static com.health.operations.functions.ConstrainFunctions.greaterEq;
import static com.health.operations.functions.ConstrainFunctions.smaller;
import static com.health.operations.functions.ConstrainFunctions.smallerEq;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.health.script.runtime.DateValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

public class ConstrainFunctionsTest {

    double columns;
    NumberValue value;

    LocalDateTime date;
    DateValue valueDate = new DateValue();

    String crea;
    StringValue valueString;

    @Before
    public void setUp() {

        columns = 3;
        value = new NumberValue();
        value.setValue(2.0);

        date = LocalDateTime.now();
        valueDate = new DateValue();

        crea = "crea";
        valueString = new StringValue("");

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

        valueDate.setValue(date.minusDays(1));

        assertTrue(greaterEq((Object) date, (Value) valueDate));
        valueDate.setValue(date.plusDays(1));
        valueDate.setValue(date.minusYears(2));

        assertTrue(greaterEq((Object) date, (Value) valueDate));

    }

    @Test
    public void testSmallerEqDate() {
        assertFalse(smallerEq((Object) date, (Value) valueDate));

        valueDate.setValue(date);

        assertTrue(smallerEq((Object) date, (Value) valueDate));
        assertFalse(smallerEq((Object) date, null));
        assertFalse(smallerEq(null, (Value) value));

        valueDate.setValue(date.plusDays(1));

        assertTrue(smallerEq((Object) date, (Value) valueDate));

    }

    @Test
    public void testSmallerDate() {
        assertFalse(smaller((Object) date, (Value) valueDate));

        valueDate.setValue(date);

        assertFalse(smaller((Object) date, (Value) valueDate));

        assertFalse(smaller((Object) date, null));
        assertFalse(smaller(null, (Value) value));

        valueDate.setValue(date.plusDays(1));

        assertTrue(smaller((Object) date, (Value) valueDate));
    }

    @Test
    public void testEqualDate() {
        assertFalse(equal((Object) date, (Value) valueDate));

        valueDate.setValue(date);

        assertTrue(equal((Object) date, (Value) valueDate));

        assertFalse(equal((Object) date, null));
        assertFalse(equal(null, (Value) value));

        valueDate.setValue(date.plusDays(1));

        assertFalse(equal((Object) date, (Value) valueDate));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGreaterString() {
        greater(crea, valueString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGreaterEqString() {
        greaterEq(crea, valueString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSmallerEqString() {
        smallerEq(crea, valueString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSmallerString() {
        smaller(crea, valueString);
    }

    @Test
    public void testEqualString() {
        assertFalse(equal(crea, valueString));
        crea = valueString.getValue();
        System.out.println(crea);
        System.out.println(crea instanceof String);
        System.out.println(valueString);
        assertTrue(equal(crea, valueString));
        valueString.setValue("crea");
        crea = "crea";
        assertTrue(equal(crea, valueString));
        System.out.println(crea);
        System.out.println(valueString);
    }

<<<<<<< HEAD
  @Test
  public void testEqualString() {
    assertFalse(equal(crea, valueString));
    crea = valueString.getValue();
    assertTrue(equal(crea, valueString));
    valueString.setValue("crea");
    crea = "crea";
    assertTrue(equal(crea, valueString));
    
  }
=======
    @Test
    public void testGreaterNumber() {
>>>>>>> master

        assertTrue(greater((Object) columns, (Value) value));

        value.setValue(3.0);

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

        value.setValue(3.0);

        assertTrue(greaterEq((Object) columns, (Value) value));
        assertFalse(greaterEq((Object) columns, null));
        assertFalse(greaterEq(null, (Value) value));

        columns = 4;

        assertTrue(greaterEq((Object) columns, (Value) value));
        value.setValue(4.0);
        assertTrue(greaterEq((Object) columns, (Value) value));

        columns = 1;

        assertFalse(greaterEq((Object) columns, (Value) value));

    }

    @Test
    public void testSmallerEqNumber() {
        assertTrue(greater((Object) columns, (Value) value));

        value.setValue(3.0);

        assertTrue(smallerEq((Object) columns, (Value) value));

        assertTrue(smallerEq((Object) columns, (Value) value));
        assertFalse(smallerEq((Object) columns, null));
        assertFalse(smallerEq(null, (Value) value));

        columns = 4;

        assertFalse(smallerEq((Object) columns, (Value) value));
        value.setValue(4.0);
        assertTrue(smallerEq((Object) columns, (Value) value));

        columns = 1;

        assertTrue(smallerEq((Object) columns, (Value) value));
    }

    @Test
    public void testSmallerNumber() {
        assertFalse(smaller((Object) columns, (Value) value));

        value.setValue(3.0);

        assertFalse(smaller((Object) columns, (Value) value));

        assertFalse(smaller((Object) columns, (Value) value));
        assertFalse(smaller((Object) columns, null));
        assertFalse(smaller(null, (Value) value));

        columns = 400;

        assertFalse(smaller((Object) columns, (Value) value));
        value.setValue(400.0);
        assertFalse(smaller((Object) columns, (Value) value));

        columns = 100;

        assertTrue(smaller((Object) columns, (Value) value));
    }

    @Test
    public void testEqualNumber() {
        assertFalse(equal((Object) columns, (Value) value));

        value.setValue(3.0);

        assertTrue(equal((Object) columns, (Value) value));

        assertTrue(equal((Object) columns, (Value) value));
        assertFalse(equal((Object) columns, null));
        assertFalse(equal(null, (Value) value));

        columns = 10;

        assertFalse(equal((Object) columns, (Value) value));
        value.setValue(10.0);
        assertTrue(equal((Object) columns, (Value) value));

        columns = 1;

        assertFalse(equal((Object) columns, (Value) value));
    }
}
