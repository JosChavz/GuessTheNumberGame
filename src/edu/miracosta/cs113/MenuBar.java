package edu.miracosta.cs113;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuBar implements ActionListener {
    private final String HOW_TO_PLAY_DEFAULT = "First, choose 3 numbers and memorize them.\nSecondly, hit the \"Check\" button." +
            "\nTIP: The boxes will become blank and the wrong number will disappear.";
    private final String CREATED_BY_DEFAULT = "This \"game\" was created by Jose Manuel Chavez for fun.";

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
        else if(text.equals("how to play")) writeMessage( ( language == null ) ? HOW_TO_PLAY_DEFAULT : language.get(5) );
        else  writeMessage( ( language == null ) ? CREATED_BY_DEFAULT : language.get(6) );
    }

    private void writeMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
