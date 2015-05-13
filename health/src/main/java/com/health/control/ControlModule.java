package com.health.control;
import java.util.Arrays;

import com.health.*;

/**
 * @author bjorn
 *
 */
public class ControlModule {
	private InputData[] data;
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
    public ControlModule(InputData[] data, String script) {
    	setData(data);
    	setScript(script);
    	this.dataset = null;
    }


	/**
	 * @return
	 */
	public InputData[] getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(InputData[] data) {
		this.data = data;
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

	/**
     * @return
     */
    public boolean startAnalysis() {
    	if(this.data == null || this.script == null) {
    		return false;
    	}

    	if(toInputModule()) {
    		toOutputModule();
    	}

    	return false;
    }	

    /**
     * 
     */
    public void getResults() {
    	toGUI();
    }

    /**
     * @return
     */
    private boolean toInputModule() {
    	//maakt input module aan..
    	//geef data..
    	//this.dataset =  gevuld Table object.

    	return false;
    }

    /**
     * 
     */
    private void toOutputModule() {
    	//maakt output module aan..
    	//geeft data..
    	//krijgt output dingen terug
    }

    /**
     * 
     */
    private void toGUI() {
    	//stuurt alles terug naar GUI
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ControlModule [data=" + Arrays.toString(data) + ", script="
				+ script + ", dataset=" + dataset + "]";
	}
    
}

