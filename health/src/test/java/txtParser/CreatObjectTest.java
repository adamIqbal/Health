package txtParser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CreatObjectTest {

  CreatObject co = new CreatObject("crea", "value", "unit", "time", "date");

  @Test
  public void testGetCreatine() {
    assertEquals(co.getCreatine(), "crea");

  }

  @Test
  public void testSetCreatine() {
    co.setCreatine("creat");
    assertEquals(co.getCreatine(), "creat");
    assertFalse(co.getCreatine().equals("crea"));
    assertTrue(co.getCreatine().equals("creat"));

    co.setCreatine("crea");
    assertEquals(co.getCreatine(), "crea");
  }

  @Test
  public void testGetValue() {
    assertEquals(co.getValue(), "value");
    assertFalse(co.getValue().equals("valued"));

  }

  @Test
  public void testSetValue() {
    co.setValue("creat");
    assertEquals(co.getValue(), "creat");
    assertFalse(co.getValue().equals("crea"));
    assertTrue(co.getValue().equals("creat"));

    co.setValue("value");
    assertEquals(co.getValue(), "value");
  }

  @Test
  public void testGetUnit() {
    assertEquals(co.getUnit(), "unit");
    assertFalse(co.getUnit().equals("units"));
  }

  @Test
  public void testSetUnit() {
    co.setUnit("6786");
    assertEquals(co.getUnit(), "6786");
    assertFalse(co.getUnit().equals("crea"));
    assertTrue(co.getUnit().equals("6786"));

    co.setUnit("unit");
    assertEquals(co.getUnit(), "unit");
  }

  @Test
  public void testGetTime() {
    assertEquals(co.getTime(), "time");
    assertFalse(co.getTime().equals("timez"));
  }

  @Test
  public void testSetTime() {
    co.setTime("6786");
    assertEquals(co.getTime(), "6786");
    assertFalse(co.getTime().equals("time"));
    assertTrue(co.getTime().equals("6786"));

    co.setTime("Time");
    assertEquals(co.getTime(), "Time");
  }

  @Test
  public void testGetDate() {
    assertEquals(co.getDate(), "date");
    assertFalse(co.getDate().equals("timez"));
  }

  @Test
  public void testSetDate() {
    co.setDate("6786");
    assertEquals(co.getDate(), "6786");
    assertFalse(co.getDate().equals("Date"));
    assertTrue(co.getDate().equals("6786"));

    co.setDate("Date");
    assertEquals(co.getDate(), "Date");
  }

  @Test
  public void testToString() {
    String result = "crea " + "value " + "unit " + "time " + "date";
    assertEquals(co.toString(), result);
  }

}
