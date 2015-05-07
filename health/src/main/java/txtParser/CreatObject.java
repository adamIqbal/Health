package txtParser;

public class CreatObject {
  private String Creatine, value, unit, time, date;

  public CreatObject(String crea, String value, String unit, String time, String date) {
    setCreatine(crea);
    setValue(value);
    setUnit(unit);
    setTime(time);
    setDate(date);

  }

  public String getCreatine() {
    return Creatine;
  }

  public void setCreatine(String creatine) {
    Creatine = creatine;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return Creatine + " " + value + " " + unit + " " + time + " " + date;
  }

}
