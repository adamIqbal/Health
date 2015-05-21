package com.health.control;

import java.io.IOException;
import java.util.Arrays;

import com.health.*;
import com.health.output.Output;

/**
 * 
 *
 */
public class ControlModule {
  private Table data;
  private String script;

  private Table dataset;

  /**
	 * 
	 */
  public ControlModule() {
    this.data = null;
    this.script = null;
    this.dataset = null;
  }

  /**
   * @param data
   * @param script
   */
  public ControlModule(Table data, String script) {
    setData(data);
    setScript(script);
    this.dataset = null;
  }

  public ControlModule(Table data) {
    setData(data);
    this.dataset = null;
  }

  /**
   * Start analysis based on script
   * 
   * @return
   */
  public String startAnalysis() {
    if (this.data == null) {// || this.script == null) {
      return null;
    }

    // data.script

    String output = Output.formatTable(data);

    try {
      Output.writeTable("output.txt", data);
    } catch (IOException e) {
      System.out.println(" data could not be written to file");
    }

    return output;
  }

  /**
   * returns result of the script
   */
  public void getResults() {

  }

  /**
   * Send data to input module
   */
  private void toInputModule() {
    // maakt input module aan..
    // geef data..
    // this.dataset = gevuld Table object.

    return;
  }

  /**
   * Send data to output module
   */
  private void toOutputModule() {

  }

  /**
   * @return
   */
  public Table getData() {
    return data;
  }

  /**
   * @param data2
   */
  public void setData(Table data2) {
    this.data = data2;
  }

  /**
   * @return
   */
  public String getScript() {
    return script;
  }

  /**
   * @param script
   */
  public void setScript(String script) {
    this.script = script;
  }

  /**
   * @return
   */
  public Table getDataset() {
    return dataset;
  }

  /**
   * @param dataset
   */
  public void setDataset(Table dataset) {
    this.dataset = dataset;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ControlModule [data=" + data.toString() + ", script=" + script + ", dataset=" + dataset
        + "]";
  }

}
