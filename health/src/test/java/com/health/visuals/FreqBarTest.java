package com.health.visuals;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;

/**
 *  Tests the FreqBar class.
 */
public class FreqBarTest {

	/**
	 * Tests FreqBar class with single array.
	 * @exception IOException if input/output exception of some sort has occurred
	 */
	@Test
	public final void test() throws IOException {
		double[] ar = {1, 2, 4, 3, 5, 6, 3, 2, 4, 3, 4, 4};
		String title = "Test1";
		URL url = FreqBar.makeBarChart(ar, title);
		showChart(url, title);
	}

	/**
	 * Tests FreqBar class with 2d array.
	 * @throws IOException if input/output exception of some sort has occurred
	 */
	@Test
	public final void test2() throws IOException {
		double[][] ar = {{1, 1}, {2, 2}, {4, 4}, {3, 3}, {5, 1}, {6, 3}};
		String title = "Test2";
		URL url = FreqBar.makeBarChart(ar, title);
		showChart(url, title);
	}

	/**
	 * Creates chart and saves it as file.
	 * @exception IOException if input/output exception of some sort has occurred
	 * @param url	an URL indicating the location of the image
	 * @param title	the name of the chart
	 */
	private static void showChart(final URL url, final String title) throws IOException {
		BufferedImage image = ImageIO.read(url);
		String t = title + ".jpeg";
		File file = new File(t);
		ImageIO.write(image,  "JPEG", file);
		JLabel label = new JLabel(new ImageIcon(image));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.getContentPane().add(label);
	    f.pack();
	    f.setLocation(200, 200);
	    f.setVisible(true);
	}
}
