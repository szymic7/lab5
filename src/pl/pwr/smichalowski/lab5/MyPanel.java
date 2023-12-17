package pl.pwr.smichalowski.lab5;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel implements ActionListener {

    private JLabel timeLabel, time, scoreLabel, score;
    private int scoreAchieved = 0, liczba = 0;
    private BufferedImage bufferImage;
    private Character character;
    private Apple[] apples = new Apple[N];
    private Water[] waterBottles = new Water[N];
    private Rock[] rocks = new Rock[N];
    private Timer timer;
    private long startTime, currentTime = 0L;
    private boolean gameWorking;
    private static final int N = 10;
    public static final LineBorder border = new LineBorder(Color.BLACK, 5, true);


    public MyPanel() {

        this.setBackground(new Color(141, 218, 134));
        this.setBorder(border);
        this.setSize(new Dimension(800, 600));
        this.setLayout(null);
        this.setFocusable(false);


        // JLabel - timeLabel
        timeLabel = new JLabel("Time: ");
        timeLabel.setBounds(4, 5, 40, 15);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFocusable(false);
        this.add(timeLabel);


        // JLabel - time (czas od poczatku gry)
        time = new JLabel(String.valueOf(/*timePassed*/0));
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


        // bufferedImage - bufor
        this.bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);


        // Umieszczenie postaci w domyslnym miejscu - lewym dolnym rogu planszy
        character = new Character(2, 562);


        // Timer odpowiedzialny za aktualizację stanu gry
        timer = new Timer(0, this);
        timer.start();


        // Rozpoczęcie zliczania czasu gry
        startTime = System.nanoTime();


        // Ustawienie zmiennej warunkującej działanie wątków na wartość true
        gameWorking = true;


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

                        // Jesli nie bedzie w tym samym miejscu co zadne isteniejace juz jablko ani woda
                        if (apples[i] != null) this.repaint();

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

                        // Jesli nie bedzie w tym samym miejscu co zadna istniejaca juz woda ani jablko
                        if (waterBottles[i] != null) repaint();

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

                        // Jesli nie bedzie taki sam jak zaden istniejacy juz kamien
                        if (rocks[i] != null) repaint();

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


    public boolean isGameWorking() {
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
        Graphics2D g2dBuffer = (Graphics2D) bufferImage.getGraphics();

        try {

            g2dBuffer.setColor(getBackground()); // Ustawienie koloru na kolor tła panelu
            g2dBuffer.fillRect(0, 0, getWidth(), getHeight());

            // Linie siatki mapy
            g2dBuffer.setColor(new Color(123, 211, 115));
            for(int i = 1; i < 20; i++) {
                g2dBuffer.drawLine(i*40, 0, i*40, 600);
                if(i < 15) g2dBuffer.drawLine(0, i*40, 800, i*40);
            }

            // Postac
            g2dBuffer.drawImage(Character.getImage(), character.getX(), character.getY(), null);

            // Jablka
            for (Apple apple : apples) {
                if (apple != null) g2dBuffer.drawImage(Apple.getImage(), apple.getX(), apple.getY(), null);
            }

            // Butelki wody
            for (Water water : waterBottles) {
                if (water != null) g2dBuffer.drawImage(Water.getImage(), water.getX(), water.getY(), null);
            }

            // Kamienie
            for(Rock rock: rocks) {
                if(rock != null) g2dBuffer.drawImage(Rock.getImage(), rock.getX(), rock.getY(), null);
            }

            // Przerysowanie bufora na ekran
            g.drawImage(bufferImage, 0, 0, null);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            g2dBuffer.dispose();

        }

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

                    gameWorking = false; // Zakończenie działających wątków
                    timer.stop(); // Zatrzymanie timera odpowiedzialnego za aktualizowanie stanu gry

                    // JTextPane - komunikat o zakończeniu gry i uzyskanym wyniku
                    JTextPane gameOver = new JTextPane();
                    gameOver.setText("\n\n\n\nKoniec gry\n\nUzyskany wynik: " + score.getText());
                    gameOver.setFont(new Font("Arial", Font.BOLD, 24));
                    gameOver.setBorder(border);
                    gameOver.setBackground(new Color(171, 206, 255));
                    gameOver.setOpaque(true);
                    gameOver.setBounds(200, 150, 400, 300);
                    gameOver.setFocusable(false);
                    this.add(gameOver);

                    // Wyśrodkowanie informacji o zakończeniu gry na TextPane'ie
                    StyledDocument documentStyle = gameOver.getStyledDocument();
                    SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
                    StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
                    documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

                    break;
                }
            }
        }

        repaint();

    }

}
