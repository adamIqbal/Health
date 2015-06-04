package com.health.control;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

import com.health.Table;
import static com.health.operations.Constraints.*;
import com.health.output.Output;

/**
 * 
 *
 */
public class ControlModule {
  private final String script;
  private List<Table> data;

  /**
   * Creates a new control module with the given script and data.
   *
   * @param parsedData
   *          the script.
   * @param script
   *          the data.
   */
  public ControlModule(final String script, final List<Table> parsedData) {
    Objects.requireNonNull(script);

    this.script = script;
    setData(parsedData);
  }

  /**
   * Start analysis based on the script.
   * 
   * @return
   */
  public String startAnalysis() {
    String output = null;

    if (this.data.size() > 0) {
      Table table = this.data.get(0);

      table = constrain((record) -> record.getNumberValue("value") > 200, table);

      output = Output.formatTable(table);

      try {
        Output.writeTable("output.txt", table);
      } catch (IOException e) {
        System.out.println(" data could not be written to file");
      }
    }

    return output;
  }

  /**
   * Gets the data of this control module.
   *
   * @return the data of this control module.
   */
  public List<Table> getData() {
    return data;
  }

  /**
   * Sets the data of this control module.
   *
   * @param data
   *          the data of this control module.
   */
  public void setData(List<Table> data) {
    this.data = data;
  }

  /**
   * Gets the script of this control module.
   *
   * @return the script of this control module.
   */
  public String getScript() {
    return script;
  }

  @Override
  public String toString() {
    return "ControlModule [data=" + data.toString() + ", script=" + script + "]";
  }
}
