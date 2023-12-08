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
        this.setTitle("Gra");
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
                panel.getCharacter().setX(x + 1);
                System.out.println("Przesuwasz w prawo");
                break;
            case 37: // lewo
                panel.getCharacter().setX(x - 1);
                System.out.println("Przesuwasz w lewo");
                break;
            case 38: // gora
                panel.getCharacter().setY(y + 1);
                System.out.println("Przesuwasz w gore");
                break;
            case 40: // dol
                panel.getCharacter().setY(y - 1);
                System.out.println("Przesuwasz w dol");
                break;
            default:
                break;
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
