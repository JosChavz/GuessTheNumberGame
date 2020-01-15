package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StartingMenu extends JPanel implements ActionListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;

    public StartingMenu() {
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        /** Welcome text [in English ]*/
        JLabel welcomeText = new JLabel("Welcome to \"Guess the Number\"!");

        /** Creates a wrapper for languages */
        // Creating a new JPanel serving as a wrapper
        JPanel languageWrapper = new JPanel();
        languageWrapper.setLayout(new GridBagLayout());

        // Creating BagConstraints which serves as a align tool
        GridBagConstraints alignment = new GridBagConstraints();

        // Creating the label and buttons
        JLabel languageText = new JLabel("Please choose your language: ");
        JButton english = new JButton("English");
        JButton spanish = new JButton("Espa\u00f1ol");
        JButton japanese = new JButton("\u65e5\u672c\u8a9e");

        // Assigning their actionPerformed after click
        english.addActionListener(this);
        spanish.addActionListener(this);
        japanese.addActionListener(this);

        // Adding label and buttons to JPanel
        alignment.insets = new Insets(0,0,10,0); // Works as margins
        alignment.gridy = 1;
        languageWrapper.add(languageText, alignment);
        alignment.gridy = 2;
        languageWrapper.add(english, alignment);
        alignment.gridy = 3;
        languageWrapper.add(spanish, alignment);
        alignment.gridy = 4;
        languageWrapper.add(japanese, alignment);

        /** Adding all elements to the screen */
        add(welcomeText, BorderLayout.NORTH);
        add(languageWrapper, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /** Variable that stores the button clicked */
        String encodedString = actionEvent.getActionCommand();

        /** ArrayList for language and pass in Game constructor to change the game's language */
        ArrayList<String> languageData = new ArrayList<>();

        /** Updates the game language to user's needs */
        try {
            // Variable that will be assigned in the if-statements
            String languageFile = "";

            if (encodedString.equals(encode("English"))) languageFile = "english.dat";
            else if (encodedString.equals("Espa\u00f1ol")) languageFile = "spanish.dat";
            else if (encodedString.equals("\u65e5\u672c\u8a9e")) languageFile = "japanese.dat";

            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(languageFile), StandardCharsets.UTF_8)
            );

            String str;

            while( ( str = inputStream.readLine() ) != null ) {
                languageData.add(str);
            }
            inputStream.close();
        } catch(IOException e) {
            System.out.println("Something went wrong...");
            System.exit(0);
        }

        // Creates a new game with the right language
        MenuBar.language = languageData;
        MenuBar.refreshBar(languageData);
        Game game = new Game(languageData);
        Display.addSlide(game);

        /** Switches cards */
        Display.nextSlide();
    }

    public static String encode(String s) {
        return new String(s.getBytes(), StandardCharsets.UTF_8);
    }
}
