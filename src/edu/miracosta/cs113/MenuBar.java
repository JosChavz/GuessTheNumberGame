package edu.miracosta.cs113;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuBar implements ActionListener {
    static JMenuBar menuBar;
    static JMenuItem howTo, reset, about;
    static JFrame reference;
    static ArrayList<String> language;

    public MenuBar(JFrame reference) {
        // Initializing the menu bar
        menuBar = new JMenuBar();

        // Initializing the menu
        howTo = new JMenuItem("How To Play"); // Will differ due to language
        reset = new JMenuItem("Reset Game");
        about = new JMenuItem("About Game");

        // Adding actionListener
        howTo.addActionListener(this);
        reset.addActionListener(this);
        about.addActionListener(this);

        menuBar.add(howTo);
        menuBar.add(reset);
        menuBar.add(about);

        this.reference = reference;
        reference.setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem source = (JMenuItem) actionEvent.getSource();
        String text = source.getText().toLowerCase();

        if(text.equals("reset game")) Display.resetGame();
        else if(text.equals("how to play")) howToDisplay();
        else  JOptionPane.showMessageDialog(null, "Created by Jose M Chavez. Finally.");
    }

    private static void howToDisplay() {
        String string = "This is how to play! Bruh.";
        JOptionPane.showMessageDialog(null, string);
    }
}
