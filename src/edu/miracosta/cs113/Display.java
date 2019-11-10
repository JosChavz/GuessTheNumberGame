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

        Display window = new Display();

        /**
         * FOR WRITING PURPOSES ONLY
         */
        /*try {
            Writer outputStream = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream("spanish.dat")
            , StandardCharsets.UTF_8));
            outputStream.write("Puntuaci\u00f3n M\u00e1s Alta:\r\n");
            outputStream.write("Comprobar\r\n");
            outputStream.write("Nuevo Juego\r\n");
            outputStream.close();
        } catch(IOException e) {
            System.out.println("File not found.");
            System.exit(0);
        }*/


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
