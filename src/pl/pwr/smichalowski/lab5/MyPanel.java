package pl.pwr.smichalowski.lab5;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel /*implements ActionListener*/ {

    private Image characterImage, appleImage, waterImage;
    private Graphics2D g2d;
    private Character character;
    private Apple[] apples = new Apple[N];
    private Water[] waterBottles = new Water[N];
    private static final int N = 10;


    public MyPanel() {

        this.setBackground(new Color(141, 218, 134));
        this.setBorder(new LineBorder(Color.BLACK, 5, true));
        this.setLayout(null);
        this.setFocusable(false);

        character = new Character(2, 562);

        characterImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\character.png");
        appleImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\apple.png");
        waterImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\waterbottle.png");

        startAppleThread();

        startWaterThread();


    }

    public void startAppleThread() {

        Thread appleThread = new Thread(() -> {
            for (int i = 0; i < apples.length; i++) {
                if (apples[i] == null) {
                    apples[i] = new Apple();
                    for (int j = 0; j < apples.length; j++) {
                        if(i == j) {
                            if (apples[i].equals(waterBottles[j])) {
                                apples[i] = null;
                                i--;
                                break;
                            }
                        }
                        if(i != j) {
                            if ( apples[i].equals(apples[j]) || apples[i].equals(waterBottles[j]) ) {
                                apples[i] = null;
                                i--;
                                break;
                            }
                        }
                    }
                    if (apples[i] != null) repaint(); // Jesli nie bedzie takie samo, jak zadne isteniejace juz jablko
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        appleThread.start();

    }

    public void startWaterThread() {
        Thread waterThread = new Thread(() -> {
            for(int i = 0; i < waterBottles.length; i++) {
                if(waterBottles[i] == null) {
                    waterBottles[i] = new Water();
                    for (int j = 0; j < waterBottles.length; j++) {
                        if(i == j) {
                            if ( waterBottles[i].equals(apples[j]) ) {
                                waterBottles[i] = null;
                                i--;
                                break;
                            }
                        }
                        if(i != j) {
                            if ( waterBottles[i].equals(waterBottles[j]) || waterBottles[i].equals(apples[j]) ) {
                                waterBottles[i] = null;
                                i--;
                                break;
                            }
                        }
                    }
                    if (waterBottles[i] != null) repaint(); // Jesli nie bedzie takie samo, jak zadne isteniejace juz jablko
                }
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waterThread.start();
    }


    public Character getCharacter() {
        return character;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        try {

            // Linie siatki mapy
            g2d.setColor(new Color(123, 211, 115));
            for(int i = 1; i < 20; i++) {
                g2d.drawLine(i*40, 0, i*40, 600);
                if(i < 15) g2d.drawLine(0, i*40, 800, i*40);
            }

            // Postac
            g2d.setColor(Color.BLACK);
            g2d.drawImage(characterImage, character.getX(), character.getY(), null);

            // Jablka
            g2d.setColor(Color.RED);
            for (Apple apple : apples) {
                if (apple != null) {
                    g2d.drawImage(appleImage, apple.getX(), apple.getY(), null);
                    //g2d.fillOval(apple.getX(), apple.getY(), 36, 36);
                }
            }

            // Butelki wody
            g2d.setColor(Color.CYAN);
            for (Water water : waterBottles) {
                if (water != null) {
                    g2d.drawImage(waterImage, water.getX(), water.getY(), null);
                    //g2d.fillOval(water.getX(), water.getY(), 36, 36);
                }
            }

        } catch (Exception e) {}

    }


    /*@Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }*/

}
