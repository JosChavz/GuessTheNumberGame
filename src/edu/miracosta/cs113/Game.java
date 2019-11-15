package edu.miracosta.cs113;

import model.AssistantJack;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Game  extends JPanel implements ActionListener {

    private class Player implements Serializable, Comparable {
        private String name;
        private int score;

        private Player (String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return this.name + " - " + this.score;
        }

        @Override
        public int compareTo(Object o) {

            return 0;
        }
    }

    private AssistantJack brain;
    private JPanel comboBoxesWrapper;
    private JComboBox[] boxChoices;
    private ArrayList<String>[] currentArrays;
    private JPanel submit;
    private LinkedList<Player> highScores;
    private int index = 0;
    private ArrayList<String> languageFile;
    private JPanel gameCover;

    private void newGame() {
        // Creates a whole new set of answers
        brain = new AssistantJack(3);
        if(gameCover != null ) remove(gameCover);

        gameCover = new JPanel(new BorderLayout());
        gameCover.setPreferredSize(new Dimension(600, 300));

        // Initializing arrays
        currentArrays = new ArrayList[3];
        boxChoices = new JComboBox[3];

        // High score text
        JPanel highScorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints editor = new GridBagConstraints();
        editor.gridx = 1;

        JLabel highScoreText = new JLabel(languageFile.get(index++));
        highScorePanel.add(highScoreText, editor);

        // Editing GridBag to fit current high score needs
        editor.insets = new Insets(5, 16, 5, 16);
        editor.gridy = 1;

        // Checks the people with high scores
        try {
            FileReader file = new FileReader("highscore.dat");
            System.out.println("found");
            Scanner sc = new Scanner(file);

            highScores = new LinkedList<>();
            int x = 0;
            while(sc.hasNext()) {
                //highScores[x++] = sc.nextLine();
                System.out.println(highScores.get(x - 1));
            }
        }
        catch (FileNotFoundException ignored) { }
        finally {
            for(int i = 0; i < 3; i++) { // Doesn't want to add in to the actual Frame
                JLabel temp = new JLabel((i + 1) + ": " +
                        ((highScores == null || i < highScores.size())? "[Empty]": highScores.get(i).toString()) );
                editor.gridx = i;
                highScorePanel.add(temp, editor);
            }
        }


        // Adds high score panel to the screen
        gameCover.add(highScorePanel, BorderLayout.NORTH);

        // Number Creation for Combo Boxes
        currentArrays[0] = numberArray(6);
        currentArrays[1] = numberArray(10);
        currentArrays[2] = numberArray(6);

        // Combo Box for Number Guessing
        for(int i = 0; i < currentArrays.length; i++) {
            boxChoices[i] = new JComboBox( currentArrays[i].toArray() );
        }

        // JPanel for Combination Box
        comboBoxesWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints comboBoxEditor = new GridBagConstraints();
        comboBoxEditor.insets = new Insets(0, 15, 0, 15);

        // Adding in the JComboBox
        for (JComboBox boxChoice : boxChoices) {
            comboBoxesWrapper.add(boxChoice, comboBoxEditor);
        }

        gameCover.add(comboBoxesWrapper, BorderLayout.CENTER);

        // Check Button
        JButton submitButton = new JButton(languageFile.get(index++));
        submitButton.addActionListener(this);
        double buttonSize = submitButton.getPreferredSize().getHeight() + 20; // padding of 10 pixels

        submit = new JPanel();
        submit.setBackground(Color.darkGray);
        submit.setLayout(new GridBagLayout());
        submit.setPreferredSize(new Dimension(10, (int) buttonSize));
        submit.add(submitButton, new GridBagConstraints()); // GridBagConstraints makes the button centered
        gameCover.add(submit, BorderLayout.SOUTH);

        // Adds gameCover to super
        add(gameCover);

        // Redo the canvas
        validate();
        repaint();
    }

    public Game(ArrayList<String> languageFile) {
        // Setting size of the window
        setSize(WIDTH, HEIGHT);

        // Assigning the ArrayList global to the class
        this.languageFile = languageFile;

        newGame();
    }

    private static ArrayList<String> numberArray(int number) {
        ArrayList<String> stringArray = new ArrayList<String>(number);

        for(int i = 0; i < number; i++) {
            stringArray.add(String.valueOf(i + 1));
        }

        return stringArray;
    }

    private void displayPlayAgain() {
        // Removes button container from JPanel
        gameCover.remove(submit);

        JButton playAgainButton = new JButton(languageFile.get(index));
        playAgainButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                index = 0;
                newGame();
            }
        });
        double buttonSize = playAgainButton.getPreferredSize().getHeight() + 20; // padding of 10 pixels

        submit = new JPanel(new GridBagLayout());
        submit.setBackground(Color.darkGray);
        submit.setPreferredSize(new Dimension(10, (int) buttonSize));
        submit.add(playAgainButton, new GridBagConstraints()); // GridBagConstraints makes the button centered
        gameCover.add(submit, BorderLayout.SOUTH);

        // Redo the canvas
        validate();
        repaint();
    }

    private void updateBoxes() {
        gameCover.remove(comboBoxesWrapper);

        comboBoxesWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints comboBoxEditor = new GridBagConstraints();
        comboBoxEditor.insets = new Insets(0, 15, 0, 15);

        for(int i = 0; i < boxChoices.length; i++) {
            boxChoices[i] = new JComboBox( currentArrays[i].toArray() );
            comboBoxesWrapper.add(boxChoices[i], comboBoxEditor);
        }

        gameCover.add(comboBoxesWrapper, BorderLayout.CENTER);
        validate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int tempFirstResponse = Integer.parseInt((String) boxChoices[0].getSelectedItem());
        int tempSecondResponse = Integer.parseInt((String) boxChoices[1].getSelectedItem());
        int tempThirdResponse = Integer.parseInt((String) boxChoices[2].getSelectedItem());

        int wrongAnswer = brain.checkAnswer(
                tempFirstResponse,
                tempSecondResponse,
                tempThirdResponse
        );

        if(wrongAnswer != 0) {
            currentArrays[wrongAnswer - 1].remove(boxChoices[wrongAnswer - 1].getSelectedIndex());
            updateBoxes();
        }
        else {
            Player tempPlayer = new Player("", brain.getTimesAsked());
            if(highScores != null) {
                for(int i = highScores.size() - 1; i >= 0; i--) {
                    int compareInt = highScores.get(i).compareTo(tempPlayer);
                }
            }
            displayPlayAgain();
        }
    }
}