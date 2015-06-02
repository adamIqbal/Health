package com.health.visuals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.health.Chunk;
import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.SwingWrapper;

/**
 * Generates a Frequency Bar Diagram based on a Table object.
 * 
 * @author Bjorn van der Laan & Lizzy Scholten
 *
 */
public final class FreqBar {
    /**
     * Private constructor to prevent instantiation.
     */
    private FreqBar() {
        Object nullObject = null;
    }
    
    /**
     * Generates a Frequency bar diagram.
     * @param table Table to use
     */
    public static void frequencyBar(final Table table) {
        // Check if the Table contains a frequency column
        Column freqColumn = null;
        Column dateColumn = null;
        for (Column c : table.getColumns()) {
            if (c.getIsFrequencyColumn()) {
                freqColumn = c;
            }
            else if(c.getType() == ValueType.Date) {
                
            }
        }
        if (freqColumn != null) {
            Map<String, Integer> freqMap = formatFrequencyMap(table,
                    freqColumn.getName(), column);
            makeBarChart(freqMap); 
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
        // Create frequency map based on column
        else {
            Map<String, Integer> freqMap = createFrequencyMap(table, column);
            makeBarChart(freqMap);
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

        for (Chunk c : table) {
            for (Record r : c) {
                String value = r.getValue(column).toString();
                int frequency = (int) r.getValue(freqColumn);
                freqMap.put(value, frequency);
            }
        }

        return freqMap;
    }

    /**
     * Counts the occurences of each value of column and creates a frequency
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

        for (Chunk c : table) {
            for (Record r : c) {
                // Get value of record
                String value = r.getValue(column).toString();
                if (!freqMap.containsKey(value)) {
                    freqMap.put(value, 1);
                } else {
                    int currentFrequency = freqMap.get(value);
                    freqMap.replace(value, ++currentFrequency);
                }
            }
        }

        return freqMap;
    }

    /**
     * Creates a frequency bar diagram based on the frequency map
     * 
     * @param freqMap
     *            frequency map
     */
    private static void makeBarChart(final Map<String, Integer> freqMap) {
        // Convert input data for processing
        ArrayList<String> labels = new ArrayList<String>(freqMap.keySet());
        ArrayList<Integer> frequency = new ArrayList<Integer>(freqMap.values());

        // Create Chart
        Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(800)
                .height(600).title("Score Histogram").xAxisTitle("Event")
                .yAxisTitle("Frequency").build();

        chart.addSeries("Test 1", new ArrayList<String>(labels),
                new ArrayList<Integer>(frequency));

        // Customize Chart
        chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);

        new SwingWrapper(chart).displayChart();
    }

}
