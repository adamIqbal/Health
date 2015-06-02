package com.health.visuals;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.xml.sax.SAXException;

import com.health.Chunk;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Generates a Box and Whisker plot based on a Table object.
 * 
 * @author Bjorn van der Laan
 *
 */
public final class BoxPlot {

    /**
     * Temporary main method for testing.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        String filePath = "/home/bjorn/Documents/Context/Health/health/data/data_use/txtData.txt";
        String configPath = "/home/bjorn/Documents/Context/Health/health/data/configXmls/admireTxtConfig.xml";

        try {
            Table table = Input.readTable(filePath, configPath);
            boxPlot(table, "value");
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            System.out.println("Error!");
        }
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private BoxPlot() {
        Object nullObject = null;
    }

    /**
     * Creates a diagram with for each Chunk a BoxPlot. The selected column must
     * have type ValueType.Number
     * 
     * @param table
     *            Table to use
     * @param column
     *            column to use
     */
    public static void boxPlot(final Table table, final String column) {
        final String xName = "Plotted column: " + column;
        final String yName = "";
        final Dimension frameDimension = new Dimension(500, 500);
        final double margin = 0.20;

        ApplicationFrame frame = new ApplicationFrame("Vidney");
        frame.setSize(frameDimension);

        final BoxAndWhiskerCategoryDataset dataset = formatDataset(table,
                column);
        if (dataset == null) {
            System.out
                    .println("Column selected for BoxPlot does not contain Numbers");
            return;
        }

        final CategoryAxis xAxis = new CategoryAxis(xName);
        xAxis.setLowerMargin(margin);
        xAxis.setUpperMargin(margin);
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

        frame.setContentPane(chartPanel);
        frame.setVisible(true);
    }

    /**
     * Creates a dataset object in the right format.
     * 
     * @param table
     *            Table to use
     * @param numberColumn
     *            name of the column
     * @return
     */
    private static BoxAndWhiskerCategoryDataset formatDataset(
            final Table table, final String numberColumn) {
        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        final List<Double> list = new ArrayList<Double>();

        // Column type must be Number
        if (!(table.getColumn(numberColumn).getType() == ValueType.Number)) {
            return null;
        }

        for (Chunk chunk : table) {
            for (Record record : chunk) {
                list.add((Double) record.getValue(numberColumn));
            }
        }
        String seriesName = numberColumn + " series";
        dataset.add(list, seriesName, "");
        return dataset;
    }
}