package com.health.visuals;


import java.util.ArrayList;
import java.util.Arrays;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;


public class FreqBarDef{
	
	public static void main(String[] args){
		//Number[][] ar = {{1,1},{2,2},{4,4},{3,3},{5,1},{6,3}};
		//makeBarChart(ar);
		
		Number[] ar2 = {1,2,4,3,5,6,3,2,4,3,4,4};
		makeBarChart(ar2);
		/*
		 * 
		// Create Chart 
		Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(800).height(600).title("Score Histogram").xAxisTitle("Event").yAxisTitle("Frequency").build();
		
		//new ArrayList<String>(Arrays.asList(new String[] { "Blue", "Red", "Green", "Yellow" }))
		
		
		chart.addSeries("test 1", new double[] { 1, 2, 4, 3, 5, 6 }, new double[] { 1, 2, 4, 3, 1, 3 });
		 
		// Customize Chart
		chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
		
		//double[][] ar = {{1,1},{2,2},{4,4},{3,3},{5,1},{6,3}}
		new SwingWrapper(chart).displayChart();
		*/
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




