package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Display extends JFrame {

    private static CardLayout sceneHandler;
    private static Container scenes;
    static JPanel startingMenu = new StartingMenu();
    private static Display window;

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
       newWindow();
    }

    private static void newWindow() {
        window = new Display("Guess the Number", 400, 600);

        try {
            window.setIconImage(ImageIO.read(new File("src/edu/miracosta/cs113/logo.png")));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        window.setVisible(true);
    }

    public Display(String message, int w, int h) {
        super(message);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(w, h);

        sceneHandler = new CardLayout(40, 30);

        scenes = getContentPane();
        scenes.setLayout(sceneHandler);

        scenes.add(startingMenu);

        MenuBar tester = new MenuBar(this);
    }

    public static void addSlide(JPanel comp) { scenes.add(comp); }

    public static void nextSlide() { sceneHandler.next(scenes); }

    public static void resetGame() {
        window.setVisible(false);
        newWindow();
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream( new FileOutputStream("highscores.dat") );
            fileWriter.reset();
            fileWriter.close();
        }
        catch(Exception e) {
            System.out.println("Error!");
            System.exit(0);
        }
    }
}
