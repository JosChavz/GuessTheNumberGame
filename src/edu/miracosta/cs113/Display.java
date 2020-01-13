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
    }

    private static void writeOnFile() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(
                    new FileWriter("japanese.dat", false)
            );

            fileWriter.write("\u9ad8\u5f97\u70b9");
            fileWriter.newLine();
            fileWriter.write("Check");
            fileWriter.newLine();
            fileWriter.write("\u65b0\u3057\u3044\u30b2\u30fc\u30e0");
            fileWriter.newLine();
            fileWriter.write("First, choose 3 numbers and memorize them.#Secondly, hit the \"Check\" button." +
                    "#TIP: The boxes will become blank and the wrong number will disappear.");
            fileWriter.newLine();
            fileWriter.write("This \"game\" was created by Jose Manuel Chavez for fun.");
            fileWriter.newLine();
            fileWriter.write("How to Play");
            fileWriter.newLine();
            fileWriter.write("Reset Game");
            fileWriter.newLine();
            fileWriter.write("About Game");

            fileWriter.close();
        } catch(Exception e) {
            System.exit(0);
        }
    }
}
