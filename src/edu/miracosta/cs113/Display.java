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
    private static Display window;

    public static void main(String[] args) {
       newWindow();
       writeOnFile();
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

    private static void writeOnFile() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(
                    new FileWriter("english.dat", true)
            );

            /*fileWriter.write("High Scores:");
            fileWriter.newLine();
            fileWriter.write("Check");
            fileWriter.newLine();
            fileWriter.write("New Game");*/
            fileWriter.newLine();
            fileWriter.write(" Primero, escoje 3 numeros y recuerda los.\nDespues, preciona el boton llamado \"Comprobar\"" +
                    "TIP: Las cajas se van a quedar en blanco, pero el numero  incorrecto se va a borrar.");
            /*fileWriter.write("First, choose 3 numbers and memorize them.\nSecondly, hit the \"Check\" button." +
                    "\nTIP: The boxes will become blank and the wrong number will disappear.");*/
            fileWriter.newLine();
            fileWriter.write("Este \"juego\" fue creado por Jose Manuel Chavez. El \"juego\" fue creado por diversion.");
            /*fileWriter.write("This \"game\" was created by Jose Manuel Chavez for fun.");*/

            fileWriter.close();
        } catch(Exception e) {
            System.exit(0);
        }
    }
}
