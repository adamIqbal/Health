package com.health.visuals;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Chunk;
import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class BoxPlot {
    
    public static void main(final String[] args) {

        boxPlot();

    }

 private static BoxAndWhiskerCategoryDataset createSampleDataset() {
        
        final int seriesCount = 3;
        final int categoryCount = 4;
        final int entityCount = 22;
        
        final DefaultBoxAndWhiskerCategoryDataset dataset 
            = new DefaultBoxAndWhiskerCategoryDataset();
        for (int i = 0; i < seriesCount; i++) {
            for (int j = 0; j < categoryCount; j++) {
                final List list = new ArrayList();
                // add some values...
                for (int k = 0; k < entityCount; k++) {
                    final double value1 = 10.0 + Math.random() * 3;
                    list.add(new Double(value1));
                    final double value2 = 11.25 + Math.random(); // concentrate values in the middle
                    list.add(new Double(value2));
                }
                dataset.add(list, "Series " + i, " Type " + j);
            }
            
        }
        
        return dataset;
    }
 
    /**
     * Creates a diagram with for each Chunk a BoxPlot.
     * 
     * @param table
     *            Table to use
     */
    public static void boxPlot() {
        //configs
        final String xName = "Type";
        final String yName = "Value";
        
        //
        
        JFrame frame = new JFrame();
        frame.setPreferredSize(new java.awt.Dimension(450, 270));
        
        //
        final BoxAndWhiskerCategoryDataset dataset = createSampleDataset();
        //
        
        final CategoryAxis xAxis = new CategoryAxis(xName);
        final NumberAxis yAxis = new NumberAxis(yName);
        yAxis.setAutoRangeIncludesZero(false);
        
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart("Box-and-Whisker Demo", new Font("SansSerif", Font.BOLD, 14), plot, true);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        frame.setContentPane(chartPanel);
        frame.setVisible(true);
    }
    
    

    /**
     * Creates a Map containing frequencies of column values within the Chunk.
     * 
     * @param chunk the Chunk to use
     * @param column the column to count
     * @return a Map containing the frequencies
     */
    private Map<String, Integer> createFrequencyMap(Chunk chunk, String column) {
        String columnName = column;

        // Create map to save frequencies
        Map<String, Integer> freqMap = new HashMap<String, Integer>();

        for (Record record : chunk) {
            // Get value of record
            String value = record.getValue(columnName).toString();
            if (!freqMap.containsKey(value)) {
                freqMap.put(value, 1);
            } else {
                int currentFrequency = freqMap.get(value);
                freqMap.replace(value, ++currentFrequency);
            }
        }

        return freqMap;
    }

//    public static void main(String[] args) {
//        String filePath = "/home/bjorn/Documents/Context/Health/health/data/data_use/txtData.txt";
//        String configPath = "/home/bjorn/Documents/Context/Health/health/data/configXmls/admireTxtConfig.xml";
//
//        try {
//            Table table = Input.readTable(filePath, configPath);
//            // method to test
//        } catch (IOException | ParserConfigurationException | SAXException
//                | InputException e) {
//            System.out.println("Error!");
//        }
//    }
}