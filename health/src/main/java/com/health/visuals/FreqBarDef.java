package com.health.visuals;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.health.Chunk;
import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;


public class FreqBarDef{
	
	public static void main(String[] args){
		
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
	    Map<Object, Integer> freqMap = new HashMap<Object, Integer>();
	    
	    for(Chunk c : table) {
	        for(Record r : c) {
	            //Get value of record
	            Object value = r.getValue(columnName);
	            if(!freqMap.containsKey(value)) {
	                freqMap.put(value, 1);
	            }
	            else {
	                int currentFrequency = freqMap.get(value);
	                freqMap.replace(value, ++currentFrequency);
	            }
	        }
	    }
	    
	    System.out.println(freqMap.toString());
	}


	public static void makeBarChart(Number[] arIn){
		Number[][] arOut = new Number[12][2];
		Number[] arCheck = new Number[12];
		boolean check = false;
		
		int count = 0;
		int count2 = 0;
		for(int i=0; i<12; i++){ /* length arIn */
			System.out.println(arIn[i]);
			//if(arChk != false){
			for(int k=0; k<arIn.length; k++){
				if(arOut[k][0] == arIn[i]){
					int ind = k;
					Number val = Double.valueOf(arOut[ind][1].doubleValue()) + 1;
					//val = val + 1;
					arOut[ind][1] = val;
					check = true;
					break;
				}
			}
			if(check == false){
				arCheck[count2] = arIn[i];
				arOut[count][0] = arIn[i];
				arOut[count][1] = 1;
				count = count + 1;
				count2 = count2 + 1;
			}
			check = false;
			
		}
		//System.out.println(Arrays.toString(arIn));
		
		makeBarChart(arOut);
		
	}
	
	public static void makeBarChart(Number[][] arIn){
		// Define data
		Number[] dataAr = new Number[arIn.length];
		
		int count = 0;
		
		for(int i=0; i<arIn.length; i++){
			dataAr[count] = arIn[i][1];
			count = count + 1;
		}
		
		String[] labelsAr = new String[count];
		count = 0;
		for(int k=0; k<arIn.length; k++){
			labelsAr[count] = String.valueOf(arIn[k][0]);
			count = count + 1;
		}
		
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(labelsAr));
		ArrayList<String> newList = new ArrayList<String>();
		//System.out.println(list);

		for(int n=0; n<list.size() ;n++){
			if(!list.get(n).equals("0.0") && !list.get(n).equals(null)){
				newList.add(list.get(n));
			}	
		}
		
		
		String[] labelsUse = newList.toArray(new String[newList.size()]);
		System.out.println(Arrays.toString(labelsUse));
		
		
		// Create Chart
		Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(800).height(600).title("Score Histogram").xAxisTitle("Event").yAxisTitle("Frequency").build();

		chart.addSeries("test 1", new ArrayList<String>(Arrays.asList(labelsUse)), new ArrayList<Number>(Arrays.asList(dataAr)));
		 
		// Customize Chart
		chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
		
		//double[][] ar = {{1,1},{2,2},{4,4},{3,3},{5,1},{6,3}}
		new SwingWrapper(chart).displayChart();
	}

}




