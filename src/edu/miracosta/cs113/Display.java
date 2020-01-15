package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Display extends JFrame {

    private static CardLayout sceneHandler;
    private static Container scenes;
    static JPanel startingMenu = new StartingMenu();
    private static Display window;

    public static void main(String[] args) {
       newWindow();
       //writeOnFile();
    }

    private static void newWindow() {
        window = new Display("Guess the Number", 400, 450);

        /*try {
            window.setIconImage(ImageIO.read(new File("src/java/edu/miracosta/cs113/logo.png")));
        } catch(Exception e) {
            System.out.println("Cannot read image file!");
            System.exit(0);
        }*/
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
        MenuBar.language = null;
    }

    private static void writeOnFile() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(
                    new FileWriter("spanish.dat", true)
            );

            fileWriter.newLine();
            fileWriter.write("Escoje 3 n\u00fameros por favor.");
            fileWriter.newLine();
            fileWriter.write("Escribe tu nombre");

            fileWriter.close();
        } catch(Exception e) {
            System.exit(0);
        }
    }
}
