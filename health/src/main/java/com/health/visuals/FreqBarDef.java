package com.health.visuals;




import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Chunk;
import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;


public class FreqBarDef{
	
	public static void main(String[] args){

	    String filePath = "/home/bjorn/Documents/Context/Health/health/data/data_use/txtData.txt";
        String configPath = "/home/bjorn/Documents/Context/Health/health/data/configXmls/admireTxtConfig.xml";
        try {
            Table table = Input.readTable(filePath, configPath);
            frequencyBar(table, "date");
        } catch (IOException | ParserConfigurationException | SAXException | InputException e) {
            System.out.println("Error FreqBar");
        }
        
	}
	
	/**
	 * Generates a Frequency Bar diagram.
	 * @param table Table to use
	 * @param column Column to display frequency of
	 */
	public static void frequencyBar(Table table, String column) {
	    //Get the Column to calculate frequency of
	    Column selectedColumn = table.getColumn(column);
	    int columnIndex = selectedColumn.getIndex();
	    String columnName = selectedColumn.getName();
	    	    
	    //Create map to save frequencies
	    Map<String, Integer> freqMap = new HashMap<String, Integer>();
	    
	    for(Chunk c : table) {
	        for(Record r : c) {
	            //Get value of record
	            String value = r.getValue(columnName).toString();
	            if(!freqMap.containsKey(value)) {
	                freqMap.put(value, 1);
	            }
	            else {
	                int currentFrequency = freqMap.get(value);
	                freqMap.replace(value, ++currentFrequency);
	            }
	        }
	    }
	    
	    
	}
	
	public static void makeBarChart(HashMap<String, Integer> arIn){
		// Convert input data for processing
		ArrayList<String> labels = new ArrayList<String>(arIn.keySet());
		ArrayList<Integer> frequency = new ArrayList<Integer>(arIn.values());
		
		// Create Chart
		Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(800).height(600).title("Score Histogram").xAxisTitle("Event").yAxisTitle("Frequency").build();

		chart.addSeries("Test 1", new ArrayList<String>(labels), new ArrayList<Integer>(frequency));
		 
		// Customize Chart
		chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
		
		new SwingWrapper(chart).displayChart();
	}

}




