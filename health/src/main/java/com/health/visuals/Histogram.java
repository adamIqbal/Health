package com.health.visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

import com.health.Chunk;
import com.health.Column;
import com.health.Record;
import com.health.Table;


public class Histogram extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static void main(String[] args){
		//makeHistChart();
		
		Histogram frame = new Histogram();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBounds(10, 10, 500, 500);
	    frame.setTitle("Sample graph");
	    frame.setVisible(true);
	}
	/*
	public static void hist (final Table table, final String column) {
        // Get the Column to calculate frequency of
        Column selectedColumn = table.getColumn(column);
        String columnName = selectedColumn.getName();

        // Create map to save frequencies
        Map<String, Integer> freqMap = new HashMap<String, Integer>();

        for (Chunk c : table) {
            for (Record r : c) {
                // Get value of record
                String value = r.getValue(columnName).toString();
                if (!freqMap.containsKey(value)) {
                    freqMap.put(value, 1);
                } else {
                    int currentFrequency = freqMap.get(value);
                    freqMap.replace(value, ++currentFrequency);
                }
            }
        }
        //makeHistChart(freqMap);
    }
*/
	
	public Histogram (){ //(final Map<String, Integer> freqMap) {
		double[] data = {1.0, 2.0, 3.0, 4.0, 4.0, 5.0, 4.0, 1.0, 5.0, 6.0, 7.0};
		
		HistogramDataset dataSet = new HistogramDataset();
		dataSet.addSeries("Hist", data, 10, 0, 10);
		
		JFreeChart chart = ChartFactory.createHistogram(
				"Test Hist",
				"Event",
				"Frequency",
				dataSet,
				PlotOrientation.VERTICAL,
				true,
				false,
				false
			);
		
		chart.setBackgroundPaint(new Color(230,230,230));
	    XYPlot xyplot = (XYPlot)chart.getPlot();
	    xyplot.setForegroundAlpha(0.7F);
	    xyplot.setBackgroundPaint(Color.WHITE);
	    xyplot.setDomainGridlinePaint(new Color(150,150,150));
	    xyplot.setRangeGridlinePaint(new Color(150,150,150));
	    XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
	    xybarrenderer.setShadowVisible(false);
	    xybarrenderer.setBarPainter(new StandardXYBarPainter());
	    
	    try {
	        //ChartUtilities.saveChartAsPNG(new File("test.png"), chart, 300, 300);
	      } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    ChartPanel cpanel = new ChartPanel(chart);
	    getContentPane().add(cpanel, BorderLayout.CENTER);
	}
	
}




