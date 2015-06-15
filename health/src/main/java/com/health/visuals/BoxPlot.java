package com.health.visuals;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

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

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * Generates a Box and Whisker plot based on a Table object.
 *
 * @author Bjorn van der Laan
 *
 */
public final class BoxPlot {
    /**
     * Private constructor to prevent instantiation.
     */
    private BoxPlot() {
        // Nothing happens
    }

    /**
     * Creates a diagram with for each Chunk a BoxPlot. This variant does not
     * need a column specified. It just picks a column of the right type.
     * 
     * @param table
     *            Table to use
     */
    public static JPanel boxPlot(final Table table) {
        // no column is given, so just pick a column with type ValueType.Number
        Column column = null;
        for (Column c : table.getColumns()) {
            if (c.getType() == ValueType.Number) {
                column = c;
                break;
            }
        }

        return boxPlot(table, column.getName());
    }

    /**
     * Creates a diagram with for each Chunk a BoxPlot. The selected column must
     * have type ValueType.Number
     * 
     * TODO handling when column is not of type ValueType.Number
     * 
     * @param table
     *            Table to use
     * @param column
     *            column to use. Must be of type ValueType.Number
     */
    public static JPanel boxPlot(final Table table, final String column) {
        if (!(table.getColumn(column).getType() == ValueType.Number)) {
            throw new IllegalArgumentException("Column must be of type Number");
        }

        final String xName = "Plotted column: " + column;
        final String yName = "";
        final Dimension frameDimension = new Dimension(500, 500);
        final double margin = 0.20;

        ApplicationFrame frame = new ApplicationFrame("Vidney");
        frame.setSize(frameDimension);

        final BoxAndWhiskerCategoryDataset dataset = formatDataset(table,
                column);

        final CategoryAxis xAxis = new CategoryAxis(xName);
        xAxis.setLowerMargin(margin);
        xAxis.setUpperMargin(margin);
        final NumberAxis yAxis = new NumberAxis(yName);
        yAxis.setAutoRangeIncludesZero(false);

        final BoxAndWhiskerRenderer renderer = createRenderer(false, true);

        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis,
                renderer);
        final ChartPanel chartPanel = createChartPanel(plot);

        frame.setContentPane(chartPanel);
        frame.setVisible(true);

        return chartPanel;
    }

    /**
     * Creates a dataset object in the right format.
     *
     * @param table
     *            Table to use
     * @param numberColumn
     *            name of the column. Must be of type ValueType.Number
     * @return
     */
    private static BoxAndWhiskerCategoryDataset formatDataset(
            final Table table, final String numberColumn) {
        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        final List<Double> list = new ArrayList<Double>();

        for (Record record : table) {
            list.add((Double) record.getValue(numberColumn));
        }

        String seriesName = numberColumn + " series";
        dataset.add(list, seriesName, "");
        return dataset;
    }

    /**
     * Creates a Renderer object to that can draw the BoxPlot.
     * 
     * @return a BoxAndWhiskerRenderer that can render the boxplot
     */
    private final static BoxAndWhiskerRenderer createRenderer(final boolean meanVisible, final boolean fillBox) {
        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setMeanVisible(meanVisible);
        renderer.setFillBox(fillBox);
        renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());

        return renderer;
    }

    /**
     * Creates a ChartPanel to draw the plot on.
     * 
     * @param plot
     * @return a ChartPanel containing the plot
     */
    private final static ChartPanel createChartPanel(CategoryPlot plot) {
        final JFreeChart chart = new JFreeChart("Boxplot", new Font(
                "SansSerif", Font.BOLD, 14), plot, true);
        final ChartPanel chartPanel = new ChartPanel(chart);

        return chartPanel;
    }
}
