package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Display extends JFrame {

    private static CardLayout sceneHandler;
    private static Container scenes;
    static JPanel startingMenu = new StartingMenu();

    public static void main(String[] args) {
        /** FOR TESTING PURPOSES */
        /*try {
            FileOutputStream file = new FileOutputStream("highscores.dat");
            Data fileWriter = new ObjectOutputStream(file);
            fileWriter.writeObject(new Game.Player("Robert", 50));
            fileWriter.writeObject(new Game.Player("Charles", 10));
            fileWriter.writeObject(new Game.Player("Even", 5));
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
        setSize(600, 400);

        sceneHandler = new CardLayout(40, 30);

        scenes = getContentPane();
        scenes.setLayout(sceneHandler);

        scenes.add(startingMenu);
    }

    public static void addSlide(JPanel comp) { scenes.add(comp); }

    public static void nextSlide() { sceneHandler.next(scenes); }
}
