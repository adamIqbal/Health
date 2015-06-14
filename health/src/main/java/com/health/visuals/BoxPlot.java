package com.health.visuals;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;

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

import com.health.Chunk;
import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

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
        //Nothing happens
    }
    
    /**
     * Creates a diagram with for each Chunk a BoxPlot.
     * This variant does not need a column specified. It just picks a column of the right type.
     * @param table Table to use
     */
    public static void boxPlot(final Table table) {
        //no column is given, so just pick a column with type ValueType.Number
        Column column = null;
        for (Column c : table.getColumns()) {
            if (c.getType() == ValueType.Number) {
                column = c;
                break;
            }
        }
        
        boxPlot(table, column.getName());
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
    public static void boxPlot(final Table table, final String column) {
        if (!(table.getColumn(column).getType() == ValueType.Number)) {
            System.out.println("Column must be of type Number");
            return;
        }
        
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
                    .println("Dataset is empty");
            return;
        }

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
        
        File file = new File("BoxTest");
        PrintFrameToPDF(frame, file);
        
    }

    /**
     * Creates a dataset object in the right format.
     *
     * @param table
     *            Table to use
     * @param numberColumn
     *            name of the column. 
     *            Must be of type ValueType.Number            
     * @return
     */
    private static BoxAndWhiskerCategoryDataset formatDataset(
            final Table table, final String numberColumn) {       
        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        final List<Double> list = new ArrayList<Double>();

        for (Chunk chunk : table) {
            for (Record record : chunk) {
                list.add((Double) record.getValue(numberColumn));
            }
        }
        String seriesName = numberColumn + " series";
        dataset.add(list, seriesName, "");
        return dataset;
    }
    
    /**
     * Creates a Renderer object to that can draw the BoxPlot.
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
     * @param plot
     * @return a ChartPanel containing the plot
     */
    private final static ChartPanel createChartPanel(CategoryPlot plot) {
        final JFreeChart chart = new JFreeChart("Boxplot", new Font(
                "SansSerif", Font.BOLD, 14), plot, true);
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        return chartPanel;
    }
    
    public static void PrintFrameToPDF(JFrame frame, File file) {
        try {
            com.itextpdf.text.Document d = new com.itextpdf.text.Document();
            PdfWriter writer = PdfWriter.getInstance( d, new FileOutputStream(file));
            d.open();

            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate template = cb.createTemplate(PageSize.A4.getWidth(),PageSize.A4.getHeight());
            cb.addTemplate(template, 0, 0);

            Graphics2D g2d = template.createGraphics(PageSize.A4.getWidth(),PageSize.A4.getHeight());
            g2d.scale(0.4, 0.4);

            for(int i=0; i< frame.getContentPane().getComponents().length; i++){
                Component c = frame.getContentPane().getComponent(i);
                if(c instanceof JLabel || c instanceof JScrollPane){
                    g2d.translate(c.getBounds().x,c.getBounds().y);
                    if(c instanceof JScrollPane){c.setBounds(0,0,(int)PageSize.A4.getWidth()*2,(int)PageSize.A4.getHeight()*2);}
                    c.paintAll(g2d);
                    c.addNotify();
                }
            }


            g2d.dispose();

            d.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
        }
    }
}
