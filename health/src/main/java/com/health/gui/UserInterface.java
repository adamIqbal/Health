package com.health.gui;

import java.awt.Color;

/**
 * The GUI for our application most adhere to this interface.
 * @author Bjorn van der Laan
 *
 */
public interface UserInterface {

    /**
     * PATH_TO_DATA is the path to the data folder.
     */
    String PATH_TO_DATA = "data/";
    /**
     * PATH_TO_CONFIG_XML is the path to all config xmls.
     */
    String PATH_TO_CONFIG_XML = PATH_TO_DATA
            + "configXmls/";
    /**
     * Main color of the GUI.
     */
    Color GUI_COLOR = new Color(137, 207, 240);

    /**
     * Initializes the GUI frame.
     */
    void init(String title, String laf);

}