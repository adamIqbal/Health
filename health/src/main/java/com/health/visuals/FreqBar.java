package com.health.visuals;

import static com.googlecode.charts4j.Color.*;

import java.util.ArrayList;
import java.util.Arrays;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plots;

import java.io.IOException;
import java.net.URL;



public class FreqBar {
	
	public static URL makeBarChart(double[] arIn, String title) throws IOException{
		
		double[][] arOut = new double[arIn.length][2];
		// For checking if event is already 
		boolean check = false;
		
		int count = 0;
		for(int i=0; i<arIn.length; i++){ 
			// Check if value is already present in arOut
			for(int k=0; k<arIn.length; k++){
				if(arOut[k][0] == arIn[i]){
					int ind = k;
					double val = arOut[ind][1];
					val = val + 1;
					arOut[ind][1] = val;
					check = true;
					break;
				}
			}
			// Else put it in arOut
			if(check == false){
				arOut[count][0] = arIn[i];
				arOut[count][1] = 1;
				count = count + 1;
			}
			check = false;	
		}
		
		URL url = makeBarChart(arOut, title);
		return url;
	}
	
	public static URL makeBarChart(double[][] arIn, String title) throws IOException{
		// Defining data
		double[] dataAr = new double[arIn.length];
		// Set count to 0
		int count = 0;
		// Copy second column of input array into dataArray
		for(int i=0; i<arIn.length; i++){
			dataAr[count] = arIn[i][1];
			count = count + 1;
		}	
		// Get maximum value of dataArray
		double maxVal = dataAr[0];
		for(int m = 0; m < dataAr.length; m++) {
			if (dataAr[m] > maxVal) {
				maxVal = dataAr[m];
			}
		}
		// Defining labels
		String[] labelsAr = new String[count];
		count = 0;
		// Create labels
		for(int k=0; k<arIn.length; k++){
			labelsAr[count] = String.valueOf(arIn[k][0]);
			count = count + 1;
		}
		// Turn labels into list
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(labelsAr));
		ArrayList<String> newList = new ArrayList<String>();
		// Delete values from original label array that are useless and that are not going to be used as labels
		// Do this by copying useful ones in new ArrayList
		for(int n=0; n<list.size() ;n++){
			if(!list.get(n).equals("0.0") && !list.get(n).equals(null)){
				newList.add(list.get(n));
			}	
		}
		
		// Transform labels list to array
		String[] labelsUse = newList.toArray(new String[newList.size()]);
		
		// Add data to the chart
		final double MAX_EVENT = maxVal + 1; 
		Data data = DataUtil.scaleWithinRange(0,  MAX_EVENT, dataAr); 
		BarChartPlot dat = Plots.newBarChartPlot(data, RED, "Data");
		BarChart chart = GCharts.newBarChart(dat);
		
		//Defining axis (labels etc)
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);		
		AxisLabels event = AxisLabelsFactory.newAxisLabels("Event", 50.0);
		event.setAxisStyle(axisStyle);
		AxisLabels events = AxisLabelsFactory.newAxisLabels(Arrays.asList(labelsUse));
		events.setAxisStyle(axisStyle);
		AxisLabels frequency = AxisLabelsFactory.newAxisLabels("Frequency", 50.0);
		frequency.setAxisStyle(axisStyle);
		AxisLabels freqCount = AxisLabelsFactory.newNumericRangeAxisLabels(0, MAX_EVENT);
		freqCount.setAxisStyle(axisStyle);
		
		// Add the axis info to the chart.
        chart.addYAxisLabels(freqCount);
        chart.addYAxisLabels(frequency);
        chart.addXAxisLabels(events);
        chart.addXAxisLabels(event);
        chart.setHorizontal(false);
        
        //size should be calculate based on frequencies and amount of labels
        chart.setSize(650, 450);
        chart.setSpaceBetweenGroupsOfBars(20);
		
        // Set title and background
        chart.setTitle(title, BLACK, 17);
        chart.setBackgroundFill(Fills.newSolidFill(LIGHTGREY));
        
        // Create url of the chart
        URL url = new URL(chart.toURLString());
        
        return(url);
	}
}