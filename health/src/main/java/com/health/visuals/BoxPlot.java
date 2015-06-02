package com.health.visuals;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.Outlier;
import org.jfree.chart.renderer.OutlierList;
import org.jfree.chart.renderer.OutlierListCollection;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

import com.health.Chunk;
import com.health.Record;

public class BoxPlot {

    public static void main(final String[] args) {

        boxPlot();

    }

    private static BoxAndWhiskerCategoryDataset createSampleDataset() {
        final int seriesCount = 1;
        final int categoryCount = 1;
        final int entityCount = 22;

        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        for (int i = 0; i < seriesCount; i++) {
            for (int j = 0; j < categoryCount; j++) {
                final List list = new ArrayList();

                // Here you add the values
                for (int k = 0; k < entityCount; k++) {
                    final double value1 = 10.0 + Math.random() * 3;
                    list.add(new Double(value1));
                    final double value2 = 11.25 + Math.random(); // concentrate
                                                                 // values in
                                                                 // the middle
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
        final String xName = "Type";
        final String yName = "Value";
        final Dimension frameDimension = new Dimension(500, 500);

        ApplicationFrame frame = new ApplicationFrame("Vidney");
        frame.setSize(frameDimension);

        // TODO replace sample dataset with real
        final BoxAndWhiskerCategoryDataset dataset = createSampleDataset();

        final CategoryAxis xAxis = new CategoryAxis(xName);
        xAxis.setLowerMargin(0.20);
        xAxis.setUpperMargin(0.20);
        final NumberAxis yAxis = new NumberAxis(yName);
        yAxis.setAutoRangeIncludesZero(false);

        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setMeanVisible(false);
        renderer.setFillBox(true);
        renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis,
                renderer);

        final JFreeChart chart = new JFreeChart("Boxplot", new Font(
                "SansSerif", Font.BOLD, 14), plot, true);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        frame.setContentPane(chartPanel);
        frame.setVisible(true);
    }

    /**
     * Creates a Map containing frequencies of column values within the Chunk.
     * 
     * @param chunk
     *            the Chunk to use
     * @param column
     *            the column to count
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

    // public static void main(String[] args) {
    // String filePath =
    // "/home/bjorn/Documents/Context/Health/health/data/data_use/txtData.txt";
    // String configPath =
    // "/home/bjorn/Documents/Context/Health/health/data/configXmls/admireTxtConfig.xml";
    //
    // try {
    // Table table = Input.readTable(filePath, configPath);
    // // method to test
    // } catch (IOException | ParserConfigurationException | SAXException
    // | InputException e) {
    // System.out.println("Error!");
    // }
    // }
}