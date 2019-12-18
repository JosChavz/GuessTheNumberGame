package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Display extends JFrame implements ActionListener {

    private static CardLayout sceneHandler;
    private static Container scenes;
    static JPanel startingMenu = new StartingMenu();

    public static void main(String[] args) {
        /** FOR TESTING PURPOSES */
        /*try {
            FileOutputStream file = new FileOutputStream("highscores.dat");
            ObjectOutputStream fileWriter = new ObjectOutputStream(file);
        }
        catch(Exception e) {
            System.out.println("Error!");
            System.exit(0);
        }*/

        Display window = new Display();

        // Change the Icon for the program
        try {
            window.setIconImage(ImageIO.read(new File("src/edu/miracosta/cs113/logo.png")));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        window.setVisible(true);
    }

    public Display() {
        super("Guess the Number");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);

        sceneHandler = new CardLayout(40, 30);

        scenes = getContentPane();
        scenes.setLayout(sceneHandler);

        scenes.add(startingMenu);

        // Initializing the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Initializing the menu
        JMenuItem howTo = new JMenuItem("How To Play"); // Will differ due to language
        JMenuItem reset = new JMenuItem("Reset Game");
        JMenuItem about = new JMenuItem("About Game");

        // Adding actionListener
        howTo.addActionListener(this);
        reset.addActionListener(this);
        about.addActionListener(this);

        menuBar.add(howTo);
        menuBar.add(reset);
        menuBar.add(about);

        this.setJMenuBar(menuBar);
    }

    public static void addSlide(JPanel comp) { scenes.add(comp); }

    public static void nextSlide() { sceneHandler.next(scenes); }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int type = actionEvent.getActionCommand().hashCode();
        if(type < 0) type = Math.abs(type);

        switch (type) {
            case 822225463:
                JOptionPane.showMessageDialog(null, "Guess the right combination and memorize your every move!");
                break;
            case 257517667:
                resetGame();
                break;
            case 930440987:
                System.out.println('c');
                break;
        }
    }

    // Resets the Game
    private void resetGame() {

    }
}
