package com.health.visuals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

public class testSetup {
    public static void main(String[] args){
        String filePath = "/home/bjorn/Documents/Context/Health/health/data/data_use/txtData.txt";
        String configPath = "/home/bjorn/Documents/Context/Health/health/data/configXmls/admireTxtConfig.xml";
        
        try {
            Table table = Input.readTable(filePath, configPath);
            //method to test
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            System.out.println("Error!");
        }
        
    }
}
