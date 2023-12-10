package pl.pwr.smichalowski.lab5;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener {

    private MyPanel panel;
    private final int mapLenght = 20; // 1 pole - 40 pikseli
    private final int mapHeight = 15; // 1 pole - 40 pikseli
    public int x, y;

    public MainFrame() {

        // JFrame
        this.setTitle("Unikaj głazów i zbieraj zasoby ani uzyskać jak najlepszy wynik");
        this.setSize(835, 657);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // MyPanel - panel
        panel = new MyPanel();
        panel.setBounds(10, 10, 800, 600);
        this.add(panel);


        // KeyListener - sterowanie postacia
        this.addKeyListener(this);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        x = panel.getCharacter().getX();
        y = panel.getCharacter().getY();

        switch(e.getKeyCode()) {
            case 39: // prawo
                if(x < 760) panel.getCharacter().setX(x + 40);
                break;
            case 37: // lewo
                if(x > 40) panel.getCharacter().setX(x - 40);
                break;
            case 38: // gora
                if(y > 40) panel.getCharacter().setY(y - 40);
                break;
            case 40: // dol
                if(y < 560) panel.getCharacter().setY(y + 40);
                break;
            default:
                break;
        }

        panel.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
