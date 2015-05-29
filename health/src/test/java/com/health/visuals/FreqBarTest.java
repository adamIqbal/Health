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

import com.health.visuals.FreqBar;

public class FreqBarTest {

	
	@Test
	public void test() throws IOException {
		double[] ar = {1,2,4,3,5,6,3,2,4,3,4,4};
		String title = "Test1";
		URL url = FreqBar.makeBarChart(ar, title);
		showChart(url, title);
	}
	
	@Test
	public void test2() throws IOException {
		double[][] ar = {{1,1},{2,2},{4,4},{3,3},{5,1},{6,3}};
		String title = "Test2";
		URL url = FreqBar.makeBarChart(ar, title);
		showChart(url, title);
	}

	private static void showChart(URL url, String title) throws IOException {
		BufferedImage image = ImageIO.read(url);
		String t = title + ".jpeg";
		File file = new File(t);
		ImageIO.write(image,  "JPEG", file);
		JLabel label = new JLabel(new ImageIcon(image));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.getContentPane().add(label);
	    f.pack();
	    f.setLocation(200,200);
	    f.setVisible(true);
	}	
	
}
