package pl.pwr.smichalowski.lab5;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {

    private JLabel timeLabel, time, scoreLabel, score;
    private int timePassed = 0, scoreAchieved = 0;
    private Image characterImage, appleImage, waterImage, rockImage;
    private Graphics2D g2d;
    private Character character;
    private Apple[] apples = new Apple[N];
    private Water[] waterBottles = new Water[N];
    private Rock[] rocks = new Rock[M];
    private Timer timer, rockTimer;
    private long startTime = 0L, currentTime = 0L;
    private static final int N = 10, M = 15;
    public int liczba = 0;


    public MyPanel() {

        this.setBackground(new Color(141, 218, 134));
        this.setBorder(new LineBorder(Color.BLACK, 5, true));
        this.setLayout(null);
        this.setFocusable(false);


        // JLabel - timeLabel
        timeLabel = new JLabel("Time: ");
        timeLabel.setBounds(3, 5, 40, 15);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFocusable(false);
        this.add(timeLabel);


        // JLabel - time (czas od poczatku gry)
        time = new JLabel(String.valueOf(timePassed));
        time.setBounds(43, 5, 40, 15);
        time.setForeground(Color.WHITE);
        time.setFocusable(false);
        this.add(time);


        // JLabel - scoreLabel
        scoreLabel = new JLabel("Score: ");
        scoreLabel.setBounds(720, 5, 45, 15);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFocusable(false);
        this.add(scoreLabel);


        // JLabel - score (uzyskany wynik)
        score = new JLabel(String.valueOf(scoreAchieved));
        score.setBounds(763, 5, 40, 15);
        score.setForeground(Color.WHITE);
        score.setFocusable(false);
        this.add(score);


        // Umieszczenie postaci w domyslnym miejscu
        character = new Character(2, 562);

        // Wczytanie plikow png reprezentujacych postac, jablko i butelke wody
        characterImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\character.png");
        appleImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\apple.png");
        waterImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\waterbottle.png");
        rockImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\rock.png");


        timer = new Timer(0, this);
        timer.start();

        rockTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < rocks.length; i++) {

                    if(rocks[i] != null) {

                        rocks[i].setY(rocks[i].getY() + 10);

                        if(rocks[i].getY() > 564) {
                            rocks[i] = null;
                        }

                    }

                }
                repaint();
            }
        });
        rockTimer.start();

        startTime = System.nanoTime();

        startAppleThread();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startWaterThread(); // rozpoczecie (z opoznieniem) watku odpowiedzialnego za generowanie butelek wody
            }
        });

        startRocksThread();

    }


    public void startAppleThread() { // Wątek obsługujący generowanie nowych jabłek na planszy

        Thread appleThread = new Thread(() -> {

            while(true) {
                for (int i = 0; i < apples.length; i++) {

                    if (apples[i] == null) {
                        apples[i] = new Apple();

                        for (int j = 0; j < apples.length; j++) {
                            if (i == j) {
                                if (apples[i].equals(waterBottles[j])) {
                                    apples[i] = null;
                                    i--;
                                    break;
                                }
                            }
                            if (i != j) {
                                if (apples[i].equals(apples[j]) || apples[i].equals(waterBottles[j])) {
                                    apples[i] = null;
                                    i--;
                                    break;
                                }
                            }
                        }

                        if (apples[i] != null)
                            repaint(); // Jesli nie bedzie takie samo, jak zadne isteniejace juz jablko

                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        appleThread.start();

    }

    public void startWaterThread() { // Wątek obsługujący generowanie nowych butelek wody na planszy
        Thread waterThread = new Thread(() -> {

            while(true) {
                for (int i = 0; i < waterBottles.length; i++) {

                    if (waterBottles[i] == null) {
                        waterBottles[i] = new Water();

                        for (int j = 0; j < waterBottles.length; j++) {
                            if (i == j) {
                                if (waterBottles[i].equals(apples[j])) {
                                    waterBottles[i] = null;
                                    i--;
                                    break;
                                }
                            }
                            if (i != j) {
                                if (waterBottles[i].equals(waterBottles[j]) || waterBottles[i].equals(apples[j])) {
                                    waterBottles[i] = null;
                                    i--;
                                    break;
                                }
                            }
                        }

                        if (waterBottles[i] != null)
                            repaint(); // Jesli nie bedzie takie samo, jak zadna istniejaca juz woda

                    }

                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        waterThread.start();

    }


    public void startRocksThread() { // Wątek obsługujący spadające kamienie, których trzeba unikać
        Thread rocksThread = new Thread(() -> {

            while(true) {
                for (int i = 0; i < rocks.length; i++) {

                    if (rocks[i] == null) {
                        rocks[i] = new Rock();

                        for (int j = 0; j < rocks.length; j++) {
                            if (i != j) {
                                if (rocks[i].equals(rocks[j])) {
                                    rocks[i] = null;
                                    i--;
                                    break;
                                }
                            }
                        }

                        if (rocks[i] != null)
                            repaint(); // Jesli nie bedzie takie samo, jak zadne isteniejace juz jablko

                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        rocksThread.start();
    }


    public Character getCharacter() {
        return character;
    }

    public Apple[] getApples() {
        return this.apples;
    }

    public void pickUpApple(int i) {
        apples[i] = null;
        // appleCount -= 1;
        this.scoreAchieved += 5;
        score.setText(String.valueOf(scoreAchieved));
    }

    public void pickUpWaterBottle(int i) {
        waterBottles[i] = null;
        // waterCount -= 1;
        this.scoreAchieved += 10;
        score.setText(String.valueOf(scoreAchieved));
    }

    public Water[] getWaterBottles() {
        return this.waterBottles;
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

            // Kamienie
            g2d.setColor(Color.GRAY);
            for(Rock rock: rocks) {
                if(rock != null) {
                    g2d.drawImage(rockImage, rock.getX(), rock.getY(), null);
                }
            }

        } catch (Exception e) {}

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        currentTime = System.nanoTime();
        time.setText(String.valueOf((currentTime - startTime)/1000000000));

        // Warunek spelniony bedzie tylko raz dla kazdej pelnej sekundy, nastepnie zmienna 'liczba' zwieksza sie o 1
        // i oczekuje na kolejna pelna sekunde
        if(Integer.valueOf(time.getText()) != liczba) {
            scoreAchieved += 1; // kazda przezyta sekunda to +1 punkt do wyniku
            score.setText(String.valueOf(scoreAchieved));
            liczba += 1;
        }

        for(int i = 0; i < apples.length; i++) {
            if(apples[i] != null) {
                if(character.getX()+2 == apples[i].getX() && character.getY()+2 == apples[i].getY()) {
                    pickUpApple(i);
                    break;
                }
            }
        }

        for(int i = 0; i < waterBottles.length; i++) {
            if(waterBottles[i] != null) {
                if(character.getX()+2 == waterBottles[i].getX() && character.getY()+2 == waterBottles[i].getY()) {
                    pickUpWaterBottle(i);
                    break;
                }
            }
        }

        repaint();
    }

}
