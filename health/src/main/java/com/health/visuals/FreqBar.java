package com.health.visuals;

import static com.googlecode.charts4j.Color.*;

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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FreqBar {
	public static void main(String args[]) throws Exception {
		makeBarChart();
	}
	
	public static void makeBarChart() throws IOException{
		//Defining data
		final int MAX_EVENT = 71; //define definite value later
		Data data = DataUtil.scaleWithinRange(0,  100, Arrays.asList(23, 40, 30, 50, 32)); //use to be calculated frequency info from input
		BarChartPlot dat = Plots.newBarChartPlot(data, RED, "Data");
		BarChart chart = GCharts.newBarChart(dat);
		
		//Defining axis
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		
		AxisLabels event = AxisLabelsFactory.newAxisLabels("Event", 50.0);
		event.setAxisStyle(axisStyle);
		AxisLabels events = AxisLabelsFactory.newAxisLabels("Event1", "Event2", "Event3", "Event4", "Event5");
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
        chart.setSize(450, 450);
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