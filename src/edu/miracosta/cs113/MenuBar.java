package edu.miracosta.cs113;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuBar implements ActionListener {
    /** FINAL VARIABLES */
    private final String HOW_TO_PLAY_DEFAULT = "First, choose 3 numbers and memorize them.\nSecondly, hit the \"Check\" button." +
            "\nTIP: The boxes will become blank and the wrong number will disappear.";
    private final String CREATED_BY_DEFAULT = "This \"game\" was created by Jose Manuel Chavez for fun.";
    private final String TEXT_1_DEFAULT = "How to Play";
    private final String TEXT_2_DEFAULT = "Reset Game";
    private final String TEXT_3_DEFAULT = "About Game";

    /** Variables */
    static JMenuBar menuBar;
    static JMenuItem howTo, reset, about;
    static JFrame reference;
    static ArrayList<String> language;

    public MenuBar(JFrame reference) {
        // Initializing the menu bar
        menuBar = new JMenuBar();

        // Initializing the menu
        howTo = new JMenuItem(TEXT_1_DEFAULT);
        reset = new JMenuItem(TEXT_2_DEFAULT);
        about = new JMenuItem(TEXT_3_DEFAULT);

        // Adding actionListener
        howTo.addActionListener(this);
        reset.addActionListener(this);
        about.addActionListener(this);

        menuBar.add(howTo);
        menuBar.add(reset);
        menuBar.add(about);

        reference.setJMenuBar(menuBar);

        this.reference = reference;
    }

    public static void refreshBar(ArrayList<String> language) {
        // Changes the text of the buttons
        howTo.setText(language.get(5));
        reset.setText(language.get(6));
        about.setText(language.get(7));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem source = (JMenuItem) actionEvent.getSource();
        String text = source.getText().toLowerCase();

        if(text.equals("reset game")) Display.resetGame();
        else if(text.equals("how to play")) writeMessage( ( language == null ) ? HOW_TO_PLAY_DEFAULT : language.get(3).replaceAll("#", "\n") );
        else  writeMessage( ( language == null ) ? CREATED_BY_DEFAULT : language.get(4) );
    }

    private void writeMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
