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
		//fail("Not yet implemented");
		//double[] ar1 = {1,2,4,3,5,6,3,2,4,5};
		//double[] ar2 = {34,2,43,54,5,4,43,43,43,6,5};
		double[] ar = {1,2,4,3,5,6,3,2,4,3,4,4};
		URL url = FreqBar.makeBarChart(ar);
		System.out.println(url);
		showChar(url);
	}
	
	@Test
	public void test2() throws IOException {
		//fail("Not yet implemented");
		//double[] ar1 = {1,2,4,3,5,6,3,2,4,5};
		//double[] ar2 = {34,2,43,54,5,4,43,43,43,6,5};
		double[][] ar = {{1,1},{2,2},{4,4},{3,3},{5,1},{6,3}};//2,4,3,5,6,3,2,4,3,4,4};
		URL url = FreqBar.makeBarChart(ar);
		System.out.println(url);
		showChar(url);
	}

	private static void showChar(URL url) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage image = ImageIO.read(url);
		File file = new File("Chart1.jpeg");
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
