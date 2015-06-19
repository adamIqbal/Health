package com.health.visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.ui.ApplicationFrame;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Generates a Histogram based on a Table object.
 *
 * @author Lizzy Scholten
 *
 */
public final class Histogram extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Private constructor to prevent instantiation.
	 */
	private Histogram() {
		// Nothing happens
	}

	/**
	 * Create Histogram chart.
	 *
	 * @param table
	 *            table to use for chart
	 * @param column
	 *            column from table that should be used
	 * @param bin
	 *            the amount of bins to use
	 * @return chart
	 */
	public static JFreeChart createHist(final Table table, final String column,
			final int bin) {
		final String xName = "Plotted column: " + column;
		final String yName = "";
		final double margin = 0.20;

		HistogramDataset dataSet = createDataset(table, column, bin);

		final CategoryAxis xAxis = new CategoryAxis(xName);
		xAxis.setLowerMargin(margin);
		xAxis.setUpperMargin(margin);
		final NumberAxis yAxis = new NumberAxis(yName);
		yAxis.setAutoRangeIncludesZero(false);

		JFreeChart chart = makeChart(dataSet, column);

		final int gridIn = 150;

		XYPlot xyplot = (XYPlot) chart.getPlot();
		xyplot.setBackgroundPaint(Color.WHITE);
		xyplot.setDomainGridlinePaint(new Color(gridIn, gridIn, gridIn));
		xyplot.setRangeGridlinePaint(new Color(gridIn, gridIn, gridIn));

		XYBarRenderer renderer = (XYBarRenderer) xyplot.getRenderer();
		renderer.setDrawBarOutline(true);
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardXYBarPainter());
		renderer.setShadowVisible(false);

		return chart;
	}

	/**
	 * Create the actual chart.
	 * @param dataSet
	 * 			dataset to use for creating the chart
	 * @param column
	 * 			column name
	 * @return
	 * 			chart
	 */
	public static JFreeChart makeChart(final HistogramDataset dataSet,
			final String column) {
		// Create Chart
		JFreeChart chart = ChartFactory.createHistogram(column + " Frequency",
				"Event", "Frequency", dataSet, PlotOrientation.VERTICAL, true,
				false, false);

		final int backSet = 230;
		chart.setBackgroundPaint(new Color(backSet, backSet, backSet));

		return chart;
	}

	/**
	 * Visualizes the Histogram.
	 *
	 * @param chart
	 *            chart that should be visualized
	 * @return JPanel
	 */
	public static JPanel visualHist(final JFreeChart chart) {
		final Dimension frameDimension = new Dimension(500, 500);

		ApplicationFrame frame = new ApplicationFrame("Vidney");
		frame.setSize(frameDimension);

		final ChartPanel chartPanel = new ChartPanel(chart);

		frame.setContentPane(chartPanel);

		return chartPanel;
	}

	/**
	 * Creates dataset for histogram.
	 *
	 * @param table
	 *            table with data
	 * @param column
	 *            column of which you want the data
	 * @param bin
	 *            for dividing the output in certain amount of parts
	 * @return dataset
	 */
	public static HistogramDataset createDataset(final Table table,
			final String column, final int bin) {
		HistogramDataset dataSet = new HistogramDataset();
		// Convert table to usable form of data
		double[] data = hist(table, column);

		// Calculate maximum value that appears in data
		// Calculate minimum value that appears in data
		double max = data[0];
		double min = data[0];
		for (int i = 0; i < data.length; i++) {
			if (data[i] > max) {
				max = data[i];
			}
			if (data[i] < min) {
				min = data[i];
			}
		}

		dataSet.addSeries("Hist", data, bin, 0, max);

		return dataSet;
	}

	/**
	 * Save the chart as pdf.
	 *
	 * @param chart
	 *            chart that should be saved
	 * @param fileName
	 *            file name under which chart should be saved
	 */
	public static void writeChartToPDF(final JFreeChart chart,
			final String fileName) {
		PdfWriter writer = null;

		com.itextpdf.text.Document document = new com.itextpdf.text.Document(
				PageSize.A4);
		final int width = (int) PageSize.A4.getWidth();
		final int height = (int) PageSize.A4.getHeight();

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					fileName + ".pdf"));
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = template.createGraphics(width, height,
					new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
					height);

			chart.draw(graphics2d, rectangle2d);

			graphics2d.dispose();
			contentByte.addTemplate(template, 0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
	}

	/**
	 * Converts Table object into array which can be used as input for the
	 * histogram.
	 *
	 * @param table
	 *            table to use
	 * @param column
	 *            column to use
	 * @return array with values that should be visualized in histogram
	 */
	private static double[] hist(final Table table, final String column) {

		// Get the Column to calculate frequency of
		Column selectedColumn = table.getColumn(column);
		String columnName = selectedColumn.getName();

		int count = table.getRecords().size();
		double[] data = new double[count];

		// Put all the data from table into array
		int i = 0;
		for (Record r : table) {
			// Get value of record
			Double value = (Double) r.getValue(columnName);
			data[i++] = value;
		}

		return data;
	}
}
