package edu.miracosta.cs113;

import exceptions.BoxChoiceException;
import model.AssistantJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Game extends JPanel implements ActionListener {

    /** The amount of highscore names there could be on board **/
    final int HIGHSCORE_MAX = 3;

    private AssistantJack brain;
    private JPanel comboBoxesWrapper;
    private JComboBox[] boxChoices;
    private ArrayList<String>[] currentArrays;
    private JPanel submit;
    private ArrayList<Player> highScores;
    private ArrayList<String> languageFile;
    private JPanel gameCover;
    private MenuBar bar;

    private void newGame() {
        // Creates a whole new set of answers
        brain = new AssistantJack(3);
        //DEBUG System.out.println(brain.getCorrectTheory().toString());
        if(gameCover != null ) remove(gameCover);

        gameCover = new JPanel(new BorderLayout());
        gameCover.setPreferredSize(new Dimension(600, 300));

        // Initializing arrays
        currentArrays = new ArrayList[3];
        boxChoices = new JComboBox[3];

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
        JButton submitButton = new JButton(languageFile.get(1));
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

        runHighScoreFile();
    }

    private void displayPlayAgain() {
        // Removes button container from JPanel
        gameCover.remove(submit);

        JButton playAgainButton = new JButton(languageFile.get(2));
        playAgainButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newGame();
                updateHighScore();
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

    // First run to print high score on screen
    private void runHighScoreFile() {
        // High score text
        JPanel highScorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints editor = new GridBagConstraints();
        editor.gridx = 1;

        JLabel highScoreText = new JLabel(languageFile.get(0));
        highScorePanel.add(highScoreText, editor);

        // Editing GridBag to fit current high score needs
        editor.insets = new Insets(5, 16, 5, 16);
        editor.gridy = 1;

        /**
         * Checks to see if there is a file that contains all high score names
         * If so, Loops to list them in #highScores;
         * Otherwise, jumps to loop and print "[Empty]" into JLabels
         * Within >finally, temporary JLabels are added to #highScorePanel
         */
        try {
            FileInputStream file = new FileInputStream("highscores.dat");
            ObjectInputStream fileReader = new ObjectInputStream(file);

            // Creates a new LinkedList, removing everything before
            highScores = new ArrayList<>();
            // A temp player that will be read in the do-while list
            highScores = (ArrayList<Player>) fileReader.readObject();

            fileReader.close();
        }
        catch (Exception ignored) { }
        finally {
            for(int i = 0; i < HIGHSCORE_MAX; i++) { // Doesn't want to add in to the actual Frame
                JLabel temp = new JLabel((i + 1) + ": " +
                        ((highScores != null && i < highScores.size())? highScores.get(i).toString() : "[Empty]") );
                editor.gridx = i;
                highScorePanel.add(temp, editor);
            }
        }


        // Adds high score panel to the screen
        gameCover.add(highScorePanel, BorderLayout.NORTH);
    }

    public void highScoreAdd(Player tempPlayer) {
        highScores.add(tempPlayer);

        if(highScores.size() == 0) return;

        BubbleSort.sort(this.highScores);

        // In case the ArrayList has a size of 4
        if(highScores.size() > HIGHSCORE_MAX) highScores.remove(highScores.size() - 1);
        printToFile(highScores);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Variables
        int[] responses;

        // Checks to see if input is valid
        try {
            responses = checkResponses(
                    (String) boxChoices[0].getSelectedItem(),
                    (String) boxChoices[1].getSelectedItem(),
                    (String) boxChoices[2].getSelectedItem(), languageFile
            );
        } catch (BoxChoiceException wrong) {
            // prints to user that input is wrong and skips all next code
            JOptionPane.showMessageDialog(null, wrong.getMessage());
            return;
        }

        int wrongAnswer = brain.checkAnswer(
                responses[0],
                responses[1],
                responses[2]
        );

        if(wrongAnswer != 0) {
            currentArrays[wrongAnswer - 1].remove(boxChoices[wrongAnswer - 1].getSelectedIndex());
            updateBoxes();
        }
        else {
            Player tempPlayer = new Player("", brain.getTimesAsked());
            if(highScores.isEmpty() || highScores.size() <= HIGHSCORE_MAX) {
                String name = JOptionPane.showInputDialog(languageFile.get(9));
                tempPlayer.setName(name); // Might be bad practice to do so
                highScoreAdd(tempPlayer);
            }
            else {
                for(int i = highScores.size() - 1; i >= 0; i--) {
                    int compareInt = highScores.get(i).compareTo(tempPlayer);
                    if(compareInt >= 0) {
                        String name = JOptionPane.showInputDialog(languageFile.get(9));
                        tempPlayer.setName(name);
                        highScoreAdd(tempPlayer);
                        break;
                    }
                }
            }
            displayPlayAgain();
        }
    }

    /** HELPING METHODS */
    private static int[] checkResponses(String a, String b, String c, ArrayList<String> languageFile) throws BoxChoiceException {
        if(a.matches("^\\d+$") && b.matches("^\\d+$") && c.matches("^\\d+$")) return new int[]{Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c)};
        else throw new BoxChoiceException(languageFile.get(8));
    }

    public void updateHighScore() {
        // High score text
        JPanel highScorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints editor = new GridBagConstraints();
        editor.gridx = 1;

        JLabel highScoreText = new JLabel(languageFile.get(0));
        highScorePanel.add(highScoreText, editor);

        // Editing GridBag to fit current high score needs
        editor.insets = new Insets(5, 16, 5, 16);
        editor.gridy = 1;

        for(int i = 0; i < HIGHSCORE_MAX; i++) {
            JLabel temp = new JLabel((i + 1) + ": " +
                    ((highScores != null && i < highScores.size())? highScores.get(i).toString() : "[Empty]") );
            editor.gridx = i;
            highScorePanel.add(temp, editor);
        }

        // Adds high score panel to the screen
        gameCover.add(highScorePanel, BorderLayout.NORTH);

        repaint();
        validate();
    }

    private static ArrayList<String> numberArray(int number) {
        ArrayList<String> stringArray = new ArrayList<>(number);
        stringArray.add("");

        for(int i = 0; i < number; i++) {
            stringArray.add(String.valueOf(i + 1));
        }

        return stringArray;
    }

    private static void printToFile(ArrayList<Player> data) {
        try {
            FileOutputStream file = new FileOutputStream("highscores.dat");
            ObjectOutputStream fileWriter = new ObjectOutputStream(file);

            fileWriter.writeObject(data);

            fileWriter.flush();
            fileWriter.close();
        }
        catch(Exception e) {
            System.out.println("Error!");
            System.exit(0);
        }
    }

}