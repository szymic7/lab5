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
    private boolean gameWorking = true;


    public MyPanel() {

        this.setBackground(new Color(141, 218, 134));
        this.setBorder(new LineBorder(Color.BLACK, 5, true));
        this.setLayout(null);
        this.setFocusable(false);


        // JLabel - timeLabel
        timeLabel = new JLabel("Time: ");
        timeLabel.setBounds(4, 5, 40, 15);
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


        // Umieszczenie postaci w domyslnym miejscu - lewym dolnym rogu planszy
        character = new Character(2, 562);

        characterImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\character.png");
        // https://fonts.google.com/icons?selected=Material+Icons:man:

        appleImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\apple.png");
        // <a href="https://www.flaticon.com/free-icons/apple" title="apple icons">Apple icons created by Creative Stall Premium - Flaticon</a>

        waterImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\water-bottle.png");
        // <a href="https://www.flaticon.com/free-icons/water" title="water icons">Water icons created by srip - Flaticon</a>

        rockImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\rock.png");
        // https://www.iconarchive.com/show/fluentui-emoji-flat-icons-by-microsoft/Rock-Flat-icon.html


        timer = new Timer(0, this);
        timer.start();

        startTime = System.nanoTime();

        // Wywołanie metod rozpoczynających nowe wątki odpowiedzialne na obsługę zasobów i przeszkód
        startAppleThread();
        startWaterThread();
        startRocksThread();
        startRocksFallingThread();

    }


    public void startAppleThread() {

        Thread appleThread = new Thread(() -> { // Wątek obsługujący generowanie nowych jabłek na planszy

            while(gameWorking) {
                for (int i = 0; i < apples.length; i++) {

                    if(!gameWorking) break;

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

                        if (apples[i] != null) repaint(); // Jesli nie bedzie takie samo, jak zadne isteniejace juz jablko

                    }

                    try {
                        Thread.sleep(5000); // Nowe jakblka pojawiaja sie co 5 sekund
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        appleThread.start();

    }


    public void startWaterThread() {

        Thread waterThread = new Thread(() -> { // Wątek obsługujący generowanie nowych butelek wody na planszy

            while(gameWorking) {
                for (int i = 0; i < waterBottles.length; i++) {

                    if(!gameWorking) break;

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

                        if (waterBottles[i] != null) repaint(); // Jesli nie bedzie takie samo, jak zadna istniejaca juz woda

                    }

                    try {
                        Thread.sleep(7000); // Nowe butelki pojawiaja sie co 7 sekund
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        waterThread.start();

    }


    public void startRocksThread() {

        Thread rocksThread = new Thread(() -> { // Wątek obsługujący generowanie nowych kamieni

            while(gameWorking) {
                for (int i = 0; i < rocks.length; i++) {

                    if(!gameWorking) break;

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

                        if (rocks[i] != null) repaint(); // Jesli nie bedzie taki sam jak zaden istniejacy juz kamien

                    }

                    try {
                        Thread.sleep(200); // Nowe kamienie pojawiaja sie co 0,2 sekundy
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        rocksThread.start();
    }


    public void startRocksFallingThread() {

        Thread rocksFallingThread = new Thread(() -> { // Wątek obsługujący spadanie kamieni

            while(gameWorking) {


                for(int i = 0; i < rocks.length; i++) {

                    if(!gameWorking) break;

                    if(rocks[i] != null) {

                        rocks[i].setY(rocks[i].getY() + 10);

                        if(rocks[i].getY() > 600) {
                            rocks[i] = null;
                        }

                    }

                }
                repaint();

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });
        rocksFallingThread.start();

    }


    public Character getCharacter() {
        return character;
    }


    public boolean getGameWorking() {
        return this.gameWorking;
    }


    public void pickUpApple(int i) {
        apples[i] = null;
        this.scoreAchieved += 5;
        score.setText(String.valueOf(scoreAchieved));
    }


    public void pickUpWaterBottle(int i) {
        waterBottles[i] = null;
        this.scoreAchieved += 10;
        score.setText(String.valueOf(scoreAchieved));
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
            g2d.drawImage(characterImage, character.getX(), character.getY(), null);

            // Jablka
            for (Apple apple : apples) {
                if (apple != null) g2d.drawImage(appleImage, apple.getX(), apple.getY(), null);
            }

            // Butelki wody
            for (Water water : waterBottles) {
                if (water != null) g2d.drawImage(waterImage, water.getX(), water.getY(), null);
            }

            // Kamienie
            for(Rock rock: rocks) {
                if(rock != null) g2d.drawImage(rockImage, rock.getX(), rock.getY(), null);
            }

        } catch (Exception e) {}

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        // Obsługa zegara mierzącego czas gry
        currentTime = System.nanoTime();
        time.setText(String.valueOf((currentTime - startTime)/1000000000));

        // Dodanie +1 punktu do wyniku za każdą przetrwaną sekundę
        // Warunek spelniony bedzie tylko raz dla kazdej pelnej sekundy, nastepnie zmienna 'liczba' zwieksza sie o 1
        // i oczekuje na kolejna pelna sekunde
        if(Integer.valueOf(time.getText()) != liczba) {
            scoreAchieved += 1;
            score.setText(String.valueOf(scoreAchieved));
            liczba += 1;
        }

        // Sprawdzenie czy można podnieść któreś z jabłek
        for(int i = 0; i < apples.length; i++) {
            if(apples[i] != null) {
                if(character.getX()+2 == apples[i].getX() && character.getY()+2 == apples[i].getY()) {
                    pickUpApple(i);
                    break;
                }
            }
        }

        // Sprawdzenie czy można podnieść którąś z butelek wody
        for(int i = 0; i < waterBottles.length; i++) {
            if(waterBottles[i] != null) {
                if(character.getX()+2 == waterBottles[i].getX() && character.getY()+2 == waterBottles[i].getY()) {
                    pickUpWaterBottle(i);
                    break;
                }
            }
        }

        // Sprawdzenie czy postać nie natrafiła na kamień - koniec gry
        for(Rock rock: rocks) {
            if(rock != null) {
                if(character.getX()+2 == rock.getX() && character.getY()+30 > rock.getY() && character.getY()-30 < rock.getY()) {
                    gameWorking = false;
                    //rockTimer.stop();
                    timer.stop();
                    System.out.println("Przegrales");
                    break;
                }
            }
        }

        repaint();

    }

}
