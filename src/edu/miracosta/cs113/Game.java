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
    private JPanel comboBoxes;
    private JComboBox firstResponse;
    private JComboBox secondResponse;
    private JComboBox thirdResponse;
    private JPanel submit;
    private ArrayList<String> firstArray;
    private ArrayList<String> secondArray;
    private ArrayList<String> thirdArray;
    private boolean gameComplete;
    private String[] highScores;
    private int index = 0;
    private ArrayList<String> languageFile;

    public Game(ArrayList<String> languageFile) {
        // Setting size of the window
        setSize(WIDTH, HEIGHT);

        // Creating the first border layout
        setLayout(new BorderLayout());

        // Assigning the ArrayList global to the class
        this.languageFile = languageFile;

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
        firstArray = numberArray(6);
        secondArray = numberArray(10);
        thirdArray = numberArray(6);

        // Combo Box for Number Guessing
        firstResponse = new JComboBox(firstArray.toArray());
        secondResponse = new JComboBox(secondArray.toArray());
        thirdResponse = new JComboBox(thirdArray.toArray());

        // JPanel for Combination Box
        comboBoxes = new JPanel(new GridBagLayout());
        GridBagConstraints comboBoxEditor = new GridBagConstraints();
        comboBoxEditor.insets = new Insets(0, 15, 0, 15);
        comboBoxes.add(firstResponse, comboBoxEditor);
        comboBoxes.add(secondResponse, comboBoxEditor);
        comboBoxes.add(thirdResponse, comboBoxEditor);

        add(comboBoxes, BorderLayout.CENTER);

        // Check Button
        JButton submitButton = new JButton(languageFile.get(index++));
        submitButton.addActionListener(this::actionPerformed);
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

    private void updateBoxes(int wrongAnswer, String clickedResponse) {
        remove(comboBoxes);

        comboBoxes = new JPanel(new GridBagLayout());
        GridBagConstraints comboBoxEditor = new GridBagConstraints();
        comboBoxEditor.insets = new Insets(0, 15, 0, 15);

        switch (wrongAnswer) {
            case 1:
                firstArray.remove(clickedResponse);
                break;
            case 2:
                secondArray.remove(clickedResponse);
                break;
            case 3:
                thirdArray.remove(clickedResponse);
                break;
        }

        firstResponse = new JComboBox(firstArray.toArray());
        secondResponse = new JComboBox(secondArray.toArray());
        thirdResponse = new JComboBox(thirdArray.toArray());
        comboBoxes.add(firstResponse, comboBoxEditor);
        comboBoxes.add(secondResponse, comboBoxEditor);
        comboBoxes.add(thirdResponse, comboBoxEditor);

        add(comboBoxes, BorderLayout.CENTER);
        validate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] responses = {
                (String)firstResponse.getSelectedItem(),
                (String)secondResponse.getSelectedItem(),
                (String)thirdResponse.getSelectedItem()
        };
        int wrongAnswer = (!gameComplete)? brain.checkAnswer(
                Integer.parseInt(responses[0]),
                Integer.parseInt(responses[1]),
                Integer.parseInt(responses[2])
        ): 0;



        if(wrongAnswer == 0) {
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
        }
        else updateBoxes(wrongAnswer, responses[wrongAnswer - 1]);
    }
}
