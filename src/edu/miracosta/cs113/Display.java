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

        Display window = new Display("Guess the Number", 400, 600);

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
