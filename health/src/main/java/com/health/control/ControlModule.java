package com.health.control;
import java.util.Arrays;

import com.health.*;

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

	/**
	 * Start analysis based on script
     * @return
     */
    public boolean startAnalysis() {
    	if(this.data == null || this.script == null) {
    		return false;
    	}

    	toInputModule();
    	toOutputModule();

    	return false;
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
    	//maakt input module aan..
    	//geef data..
    	//this.dataset =  gevuld Table object.

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ControlModule [data=" + data.toString() + ", script="
				+ script + ", dataset=" + dataset + "]";
	}
    
}

