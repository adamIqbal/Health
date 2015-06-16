package com.health.visuals;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;

import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;

import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import com.health.operations.Code;


/**
 * Creates a State Transition Matrix.
 * 
 * @author Lizzy Scholten
 *
 */
public final class StateTransitionMatrix extends JFrame {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 1L;

    private StateTransitionMatrix() {
        // Does nothing
    }

    public static JTable createStateTrans(final EventList eList) {
        List<EventList> seqList = findEventSequences(eList);

        return createStateTrans(eList, seqList);
    }

    private static List<EventList> findEventSequences(final EventList eList) {
        String[] codes = getCodes(eList);

        List<EventList> seqList = new ArrayList<EventList>();

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes.length; j++) {
                EventSequence sequence = new EventSequence(new String[] {
                        codes[i],
                        codes[j]
                }, true);

                Code.fillEventSequence(sequence, eList);

                seqList.addAll(sequence.getSequences());
            }
        }

        return seqList;
    }

    private static String[] getCodes(final EventList eList) {
        Set<String> codeSet = new HashSet<String>();

        for (Event event : eList.getList()) {
            codeSet.add(event.getCode());
        }

        String[] codes = new String[codeSet.size()];
        codeSet.toArray(codes);

        return codes;
    }

    /**
     * Create State Transition Matrix.
     * 
     * @param eList
     *            list with possible events
     * @param seqList
     *            list with sequences
     */
    public static JTable createStateTrans(final EventList eList, final List<EventList> seqList) {
        // Create frame
        final int thousand = 1000;

        JFrame vidney = new JFrame();
        vidney.setVisible(true);

        vidney.setTitle("State Transition");
        vidney.setSize(thousand, thousand);
        vidney.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[][] matrix = setUp(eList);
        String[][] matrixUse = fillMatrix(matrix, seqList);
        String[][] outputM = new String[matrixUse.length - 1][matrixUse.length];

        for (int i = 1; i < matrixUse[0].length; i++) {
            for (int j = 0; j < matrixUse[1].length; j++) {
                outputM[i - 1][j] = matrixUse[i][j];
            }
        }

        JTable table = new JTable(outputM, matrixUse[0]);

        Container c = vidney.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new JScrollPane(table), BorderLayout.CENTER);

        return table;
    }

    /**
     * Set up matrix.
     * 
     * @param eList
     *            event list
     * @return matrix setup
     */
    private static String[][] setUp(final EventList eList) {

        ArrayList<String> eArr = new ArrayList<String>();

        for (Event e : eList.getList()) {
            if (!eArr.contains(e.getCode())) {
                eArr.add(e.getCode());
            }
        }

        for (String s : eArr) {
            System.out.println(s);
        }

        String[][] matrix = new String[eArr.size() + 1][eArr.size() + 1];

        matrix[0][0] = "Event types";

        for (int k = 1; k < eArr.size() + 1; k++) {
            matrix[0][k] = eArr.get(k - 1);
            matrix[k][0] = eArr.get(k - 1);
        }

        System.out.println(Arrays.deepToString(matrix));

        return matrix;
    }

    /**
     * Fill matrix.
     * 
     * @param m
     *            matrix
     * @param seqList
     *            list of sequences
     * @return matrix
     */
    private static String[][] fillMatrix(final String[][] m, final List<EventList> seqList) {
        // Example:
        // B A A
        // A B
        // A B A
        String[][] matrix = m;
        for (EventList eSeq : seqList) {
            String[] codePat = getCodePattern(eSeq);

            for (int c = 1; c < codePat.length; c++) {
                String from = codePat[c - 1];
                String to = codePat[c];

                for (int a = 1; a < matrix[0].length; a++) {
                    boolean found1 = false;

                    System.out.println("matrix a0 : " + matrix[a][0]);
                    System.out.println("from : " + from);

                    if (matrix[a][0].equals(from)) {
                        for (int b = 1; b < matrix[1].length; b++) {
                            System.out.println("matrix 0b : " + matrix[0][b]);
                            System.out.println("to : " + to);
                            if (matrix[0][b].equals(to)) {
                                if (matrix[a][b] == null) {
                                    matrix[a][b] = "1";
                                } else {
                                    int val = Integer.parseInt(matrix[a][b]);
                                    val = val + 1;
                                    matrix[a][b] = Integer.toString(val);
                                }
                                found1 = true;
                                break;
                            }
                        }
                    }
                    if (found1) {
                        break;
                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(matrix));
        return matrix;
    }

    private static String[] getCodePattern(final EventList eSeq) {
        List<Event> events = eSeq.getList();
        String[] codes = new String[events.size()];

        for (int i = 0; i < codes.length; i++) {
            codes[i] = events.get(i).getCode();
        }

        return codes;
    }
    
	// Misses table header...
	public static void saveFile(JTable table) {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4.rotate());
		try {
            int width = table.getWidth(); 
            int height = table.getHeight();
		    
		  	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("jTable.pdf"));
		  	document.open();

		    PdfContentByte cb = writer.getDirectContent();

		    cb.saveState();
		    Graphics2D g2 = cb.createGraphicsShapes(width, height);

		    Shape oldClip = g2.getClip();
		    g2.clipRect(0, 0, width, height);

		    table.print(g2);
		    g2.setClip(oldClip);

		    g2.dispose();
		    cb.restoreState();
		  } catch (Exception e) {
			  System.err.println(e.getMessage());
		  }
		document.close();
	}
}