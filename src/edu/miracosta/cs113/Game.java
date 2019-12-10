package edu.miracosta.cs113;

import exceptions.BoxChoiceException;
import model.AssistantJack;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Game extends JPanel implements ActionListener {

    /** PLAYER INNER CLASS */
    public static class Player implements Serializable, Comparable {
        /** PLAYER VARIABLE */
        private String name;
        private int score;

        Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return this.name + " - " + this.score;
        }

        @Override
        public int compareTo(Object o) {
            // In the case that o is not a Player
            if( !(o instanceof Player) ) throw new ClassCastException();

            // This object's score is greater than o's score.
            if( this.score > ((Player) o).score) return 1;
                // This object's score is less than o's score.
            else if( this.score < ((Player) o).score ) return -1;

            // Both scores are the same
            return 0;
        }
    }

    /** GAME VARIABLES */
    private AssistantJack brain;
    private JPanel comboBoxesWrapper;
    private JComboBox[] boxChoices;
    private ArrayList<String>[] currentArrays;
    private JPanel submit;
    private LinkedList<Player> highScores;
    private int index = 0;
    private ArrayList<String> languageFile;
    private JPanel gameCover;

    /** CONSTRUCTOR */
    public Game(ArrayList<String> languageFile) {
        // Setting size of the window
        setSize(WIDTH, HEIGHT);

        // Assigning the ArrayList global to the class
        this.languageFile = languageFile;

        newGame();

        highScoreInitialization();
    }

    /** METHODS */
    private void newGame() {
        // Creates a whole new set of answers
        brain = new AssistantJack(3);
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

    /**
     * Takes in a number and outputs an ArrayList of String with elements from [0 - number]
     * Where number is the input
     * @param number The maximum number that will be added in the Array List
     * @return An Array List of String which are integers converted to String
     */
    private static ArrayList<String> numberArray(int number) {
        ArrayList<String> stringArray = new ArrayList<>(number);
        stringArray.add("");

        for(int i = 0; i < number; i++) {
            stringArray.add(String.valueOf(i + 1));
        }

        return stringArray;
    }

    private void displayPlayAgain() {
        // Removes button container from JPanel
        gameCover.remove(submit);

        JButton playAgainButton = new JButton(languageFile.get(2));
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

    private void highScoreInitialization() {
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
            highScores = new LinkedList<>();
            // A temp player that will be read in the do-while list
            Player tempPlayer;
            do {
                // Assigns the next object in the file to tempPlayer
                tempPlayer = (Player)fileReader.readObject();
                // Checks to see if the tempPlayer is null
                if(tempPlayer == null) continue;
                // Adds in if it wasn't null
                highScores.add(tempPlayer);
            }
            while( tempPlayer != null );
        }
        catch (Exception ignored) { }
        finally {
            for(int i = 0; i < 3; i++) { // Doesn't want to add in to the actual Frame
                JLabel temp = new JLabel((i + 1) + ": " +
                        ((highScores != null && i < highScores.size())? highScores.get(i).toString() : "[Empty]") );
                editor.gridx = i;
                highScorePanel.add(temp, editor);
            }
        }


        // Adds high score panel to the screen
        gameCover.add(highScorePanel, BorderLayout.NORTH);
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
                    (String) boxChoices[2].getSelectedItem()
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
            final int SCORE = brain.getTimesAsked();

            if(!highScores.isEmpty()) {
                final int IS_VALID = highScores.get(highScores.size() - 1).compareTo(new Player("", SCORE));

            }

            //updateHighScore(tempPlayer);
            //System.out.println(highScores);
            displayPlayAgain();
        }
    }

    public void updateHighScore(Player tempPlayer) {
        final int HIGHSCORE_SIZE = (highScores.isEmpty())? 0: highScores.size() ;
        if(HIGHSCORE_SIZE == 0) highScores.add(tempPlayer);

        for(int i = 0; i < HIGHSCORE_SIZE; i++) {
            switch( highScores.get(i).compareTo(tempPlayer) ) {
                case -1: // tempPlayer comes after i
                    if(i == 0) highScores.addLast(tempPlayer);
                    if(i == highScores.size() - 1) highScores.add(1, tempPlayer);
                    break;
                case 0: // tempPlayer has the same high score as i
                    if(i == 0) highScores.add(i + 1, tempPlayer);
                    if(i == highScores.size() - 1) highScores.addLast(tempPlayer);
                    break;
                case 1: // tempPlayer comes before i
                    if (i == highScores.size() - 1) highScores.addFirst(tempPlayer);
                    if(i == highScores.size() - 1) highScores.addLast(tempPlayer);
                    break;
            }
        }
    }

    /** HELPING METHODS */
    private static int[] checkResponses(String a, String b, String c) throws BoxChoiceException {
        if(a.matches("^\\d+$") && b.matches("^\\d+$") && c.matches("^\\d+$")) return new int[]{Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c)};
        else throw new BoxChoiceException("Please choose a valid input");
    }
}