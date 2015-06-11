package com.health.visuals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;

/**
 * Generates a Frequency Bar Diagram based on a Table object.
 *
 * @author Bjorn van der Laan &amp; Lizzy Scholten
 *
 */
public final class FreqBar {
    /**
     * Private constructor to prevent instantiation.
     */
    private FreqBar() {
        // Nothing happens
    }

    /**
     * Generates a Frequency bar diagram. This variant has no column specified.
     * It chooses the last date column and last frequency column in the Table
     * object.
     * 
     * @param table
     *            Table to use
     */
    public static void frequencyBar(final Table table) {
        // Check if the Table contains a frequency and a date column
        Column freqColumn = null;
        Column dateColumn = null;
        for (Column c : table.getColumns()) {
            if (c.isFrequencyColumn()) {
                freqColumn = c;
            } else if (c.getType() == ValueType.Date) {
                dateColumn = c;
            }
        }
        // If both exist, format the frequency map based on these columns.
        if (freqColumn != null && dateColumn != null) {
            Map<String, Integer> freqMap = formatFrequencyMap(table,
                    freqColumn.getName(), dateColumn.getName());
            makeBarChart(freqMap, dateColumn.getName());
        } else {
            // Not good.
            System.out
                    .println("Table contains either no frequency column or no date column.");
        }
    }

    /**
     * Generates a Frequency Bar diagram.
     *
     * @param table
     *            Table to use
     * @param column
     *            Column to display frequency of
     */
    public static void frequencyBar(final Table table, final String column) {
        // Check if the Table contains a frequency column
        Column freqColumn = null;
        for (Column c : table.getColumns()) {
            if (c.isFrequencyColumn()) {
                freqColumn = c;
            }
        }
        // If the Table contains a frequency column, use it to format the
        // frequency map
        // Else if no frequency column exists, count occurrences of values in
        // the specified column
        if (freqColumn != null) {
            Map<String, Integer> freqMap = formatFrequencyMap(table, freqColumn.getName(), column);
            makeBarChart(freqMap, column);
        } else {
            Map<String, Integer> freqMap = createFrequencyMap(table, column);
            makeBarChart(freqMap, column);
        }
    }

    /**
     * Creates a frequency map from the input Table to serve as input for
     * makeBarChart.
     *
     * @param table
     *            Table to use
     * @param freqColumn
     *            the frequency column
     * @param column
     *            the column
     * @return frequency map
     */
    private static Map<String, Integer> formatFrequencyMap(final Table table,
            final String freqColumn, final String column) {
        // Create map to save frequencies
        Map<String, Integer> freqMap = new HashMap<String, Integer>();

        for (Record r : table) {
            String value = r.getValue(column).toString();
            int frequency = (int) r.getValue(freqColumn);
            freqMap.put(value, frequency);
        }

        return freqMap;
    }

    /**
     * Counts the occurrences of each value of column and creates a frequency
     * map. Used when no the table contains no frequency column
     *
     * @param table
     *            Table to use
     * @param column
     *            Column to count
     * @return frequency map
     */
    private static Map<String, Integer> createFrequencyMap(final Table table,
            final String column) {
        // Create map to save frequencies
        Map<String, Integer> freqMap = new HashMap<String, Integer>();

        for (Record r : table) {
            // Get value of record
            String value = r.getValue(column).toString();
            if (!freqMap.containsKey(value)) {
                freqMap.put(value, 1);
            } else {
                int currentFrequency = freqMap.get(value);
                freqMap.replace(value, ++currentFrequency);
            }
        }

        return freqMap;
    }

    /**
     * Creates a frequency bar diagram based on the frequency map.
     *
     * @param freqMap
     *            frequency map
     */
    private static void makeBarChart(final Map<String, Integer> freqMap,
            final String seriesName) {
        final int frameWidth = 800;
        final int frameHeight = 600;
        // Convert input data for processing
        ArrayList<String> labels = new ArrayList<String>(freqMap.keySet());
        ArrayList<Integer> frequency = new ArrayList<Integer>(freqMap.values());

        // Create Chart
        Chart chart = new ChartBuilder().chartType(ChartType.Bar)
                .width(frameWidth).height(frameHeight).title("Score Histogram")
                .xAxisTitle(seriesName).yAxisTitle("Frequency").build();

        chart.addSeries(seriesName, new ArrayList<String>(labels),
                new ArrayList<Integer>(frequency));

        // Customize Chart
        chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);

        new SwingWrapper(chart).displayChart();
    }

}
