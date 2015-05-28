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
import com.googlecode.charts4j.collect.Lists;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.mockito.internal.util.collections.ArrayUtils;

//How often do patients measure themselves before they enter data into Mijnnierinzicht?
public class FreqBar {
	
	
	public static void main(String args[]) throws Exception {
		//temp
		//double[] list = {23,40,30,50,32};
		//makeBarChart(list);
		
		//double[] arIn = {1,2,4,3,5,6,3,2,4,5};
		//double[] ar2 = {34,2,43,54,5,4,43,43,43,6,5};
		
		
		
		double[] intAr = {1,2,4,3,5,6,3,2,4,3,4,4};
		for(double i:intAr){
			Double.toString(i);//.toString();
		}
		String[] arIn = intAr;
		String[][] arOut = new String[12][2];
		String[] arCheck = new String[12];
		boolean check = false;
		
		int count = 0;
		int count2 = 0;
		for(int i=0; i<12; i++){ /* length arIn */
			System.out.println(arIn[i]);
			//if(arChk != false){
			for(int k=0; k<arIn.length; k++){
				if(arOut[k][0] == arIn[i]){
					int ind = k;
					double val = (Double) arOut[ind][1];
					val = val + 1;
					arOut[ind][1] = val;
					check = true;
					break;
				}
			}
			/*
			if(Arrays.asList(arCheck).contains(arIn[i])){
				int ind = 0;
				
				for (int k = 0 ; k < 12; k++){
			         if (Double.valueOf(arOut[k][0]).equals(arIn[i])){
			              ind = k;
			              break;
			         }
				}
				//int ind = Arrays.asList(arOut).indexOf(arIn[i]);
				double val = arOut[ind][1];
				val = val + 1;
				arOut[ind][1] = val;
			}
			else{
				*/
			if(check == false){
				arCheck[count2] = arIn[i];
				arOut[count][0] = arIn[i];
				arOut[count][1] = 1;
				count = count + 1;
				count2 = count2 + 1;
			}
			check = false;
			
		}
		System.out.println(Arrays.toString(arIn));

		System.out.println(Arrays.toString(arCheck));
		System.out.println(Arrays.deepToString(arOut));//Arrays.toString(arOut[1]));
		
		makeBarChart(arOut);
		
	}
	
	public static void makeBarChart(Object[][] ar) throws IOException{
		//Defining data
		Object[] dataAr = new Object[ar.length];
		
		int count = 0;
		
		for(int i=0; i<ar.length; i++){
			dataAr[count] = ar[i][1];
			count = count + 1;
		}
		String[] labelsAr = new String[count];
		count = 0;
		for(int k=0; k<ar.length; k++){
			labelsAr[count] = String.valueOf(ar[k][0]);
			count = count + 1;
		}
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(labelsAr));
		ArrayList<String> newList = new ArrayList<String>();
		//System.out.println(list);
		int[] rem = new int[labelsAr.length];
		int countRem = 0;
		for(int n=0; n<list.size() ;n++){
			if(!list.get(n).equals("0.0") && !list.get(n).equals(null)){
				//rem[countRem] = n;
				//countRem = countRem + 1;
				//list.remove(n);//list.set(n, null);
				newList.add(list.get(n));
			}
			
		}
		
		
		String[] labelsUse = newList.toArray(new String[newList.size()]);
		System.out.println(Arrays.toString(labelsUse));
		
		final int MAX_EVENT = 10; //define definite value later
		Data data = DataUtil.scaleWithinRange(0,  10, dataAr); //use to be calculated frequency info from input
		BarChartPlot dat = Plots.newBarChartPlot(data, RED, "Data");
		BarChart chart = GCharts.newBarChart(dat);
		
		//Defining axis
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		
		AxisLabels event = AxisLabelsFactory.newAxisLabels("Event", 50.0);
		event.setAxisStyle(axisStyle);
		//AxisLabels events = AxisLabelsFactory.newAxisLabels("Event1", "Event2", "Event3", "Event4", "Event5", "Event 6");
		AxisLabels events = AxisLabelsFactory.newAxisLabels(Arrays.asList(labelsUse));
		events.setAxisStyle(axisStyle);
		AxisLabels frequency = AxisLabelsFactory.newAxisLabels("Frequency", 50.0);
		frequency.setAxisStyle(axisStyle);
		
		AxisLabels freqCount = AxisLabelsFactory.newNumericRangeAxisLabels(0, MAX_EVENT);
		freqCount.setAxisStyle(axisStyle);
		
		// Adding axis info to chart.
        chart.addYAxisLabels(freqCount);
        chart.addYAxisLabels(frequency);
        chart.addXAxisLabels(events);
        chart.addXAxisLabels(event);
        chart.setHorizontal(false);
        chart.setSize(650, 450);
        chart.setSpaceBetweenGroupsOfBars(20);
		
        
        chart.setTitle("Frequency Test Bar Chart", BLACK, 17);
        chart.setBackgroundFill(Fills.newSolidFill(LIGHTGREY));
        URL url = new URL(chart.toURLString());
        showChart(url);
	}
	
	public static void showChart(URL url) throws IOException{
		BufferedImage image = ImageIO.read(url);
		JLabel label = new JLabel(new ImageIcon(image));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.getContentPane().add(label);
	    f.pack();
	    f.setLocation(200,200);
	    f.setVisible(true);
	}
}