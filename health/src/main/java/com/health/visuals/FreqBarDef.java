package com.health.visuals;



import java.util.ArrayList;
import java.util.HashMap;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;


public class FreqBarDef{
	
	public static void main(String[] args){
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("A", 10);
		map.put("B", 21);
		map.put("C", 32);
		map.put("D", 15);
		map.put("E", 7);
		map.put("F", 18);
		map.put("G", 27);
		
		makeBarChart(map);
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




