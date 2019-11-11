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
import java.util.ArrayList;
import java.util.Scanner;

public class Game  extends JPanel implements ActionListener {

    private AssistantJack brain = new AssistantJack(3);
    private JPanel comboBoxesWrapper;
    private JComboBox[] boxChoices;
    private ArrayList<String>[] currentArrays;
    private JPanel submit;
    private boolean gameComplete;
    private String[] highScores;
    private int index = 0;
    private ArrayList<String> languageFile;
    private String[] responses;
    private JPanel gameCover;

    private void newGame() {
        if(gameCover != null ) remove(gameCover);

        gameCover = new JPanel(new BorderLayout());

        // Initializing arrays
        currentArrays = new ArrayList[3];
        boxChoices = new JComboBox[3];

    }

    public Game(ArrayList<String> languageFile) {
        // Setting size of the window
        setSize(WIDTH, HEIGHT);

        // Assigning the ArrayList global to the class
        this.languageFile = languageFile;

        newGame();

        // High score text
        JPanel highScorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints editor = new GridBagConstraints();
        editor.gridx = 1;

        JLabel highScoreText = new JLabel(languageFile.get(index++));
        highScorePanel.add(highScoreText, editor);

        // Checks the people with high scores
        try {
            File file = new File("src\\edu\\miracosta\\cs113\\highscore.txt");
            Scanner sc = new Scanner(file);

            highScores = new String[3];
            int x = 0;
            while(sc.hasNext()) {
                highScores[x++] = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            highScores = new String[]{"[Empty]", "[Empty]", "[Empty]"};
        }

        // Editing GridBag to fit current high score needs
        editor.insets = new Insets(5, 3, 5, 3);
        editor.gridy = 1;
        for(int i = 0; i < highScores.length; i++) { // Doesn't want to add in to the actual Frame
            JLabel temp = new JLabel((i + 1) + ": " + highScores[i]);
            editor.gridx = i;
            highScorePanel.add(temp, editor);
        }
        add(highScorePanel, BorderLayout.NORTH);

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

        add(comboBoxesWrapper, BorderLayout.CENTER);

        // Check Button
        JButton submitButton = new JButton(languageFile.get(index++));
        submitButton.addActionListener(this);
        double buttonSize = submitButton.getPreferredSize().getHeight() + 20; // padding of 10 pixels

        submit = new JPanel();
        submit.setBackground(Color.darkGray);
        submit.setLayout(new GridBagLayout());
        submit.setPreferredSize(new Dimension(10, (int) buttonSize));
        submit.add(submitButton, new GridBagConstraints()); // GridBagConstraints makes the button centered
        add(submit, BorderLayout.SOUTH);
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
        remove(submit);

        JButton playAgainButton = new JButton(languageFile.get(index));
        //playAgainButton.addActionListener(this::actionPerformed);
        double buttonSize = playAgainButton.getPreferredSize().getHeight() + 20; // padding of 10 pixels

        submit = new JPanel(new GridBagLayout());
        submit.setBackground(Color.darkGray);
        submit.setPreferredSize(new Dimension(10, (int) buttonSize));
        submit.add(playAgainButton, new GridBagConstraints()); // GridBagConstraints makes the button centered
        add(submit, BorderLayout.SOUTH);

        // Redo the canvas
        validate();
        repaint();
    }

    private void updateBoxes() {
        remove(comboBoxesWrapper);

        comboBoxesWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints comboBoxEditor = new GridBagConstraints();
        comboBoxEditor.insets = new Insets(0, 15, 0, 15);

        for(int i = 0; i < boxChoices.length; i++) {
            boxChoices[i] = new JComboBox( currentArrays[i].toArray() );
            comboBoxesWrapper.add(boxChoices[i], comboBoxEditor);
        }

        add(comboBoxesWrapper, BorderLayout.CENTER);
        validate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int tempFirstResponse = Integer.parseInt((String) boxChoices[0].getSelectedItem());
        int tempSecondResponse = Integer.parseInt((String) boxChoices[1].getSelectedItem());
        int tempThirdResponse = Integer.parseInt((String) boxChoices[2].getSelectedItem());

        int wrongAnswer = (!gameComplete)? brain.checkAnswer(
                tempFirstResponse,
                tempSecondResponse,
                tempThirdResponse
        ): 0;

        if(wrongAnswer != 0) {
            currentArrays[wrongAnswer - 1].remove(boxChoices[wrongAnswer - 1].getSelectedIndex());
            updateBoxes();
        }
        else {
            displayPlayAgain();
        }
    }
}
