package com.health.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class VButton extends JButton {
    public VButton(String text) {
        super(text);
        this.setPreferredSize(new Dimension(100,50));
        this.setBackground(new Color(200,200,200));
    }
}
