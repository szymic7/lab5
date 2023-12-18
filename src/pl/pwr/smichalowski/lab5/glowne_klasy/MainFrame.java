package pl.pwr.smichalowski.lab5.glowne_klasy;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener {

    private MyPanel panel;
    private int x, y;

    public MainFrame() {

        // JFrame
        this.setTitle("GRA PLATFORMOWA");
        this.setSize(835, 657);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // MyPanel - panel
        panel = new MyPanel();
        panel.setBounds(10, 10, panel.getWidth(), panel.getHeight());
        this.add(panel);


        // KeyListener - sterowanie postacią
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

        if(this.panel.isGameWorking()) {

            x = panel.getCharacter().getX();
            y = panel.getCharacter().getY();

            switch (e.getKeyCode()) {
                case 39: // prawo
                    if (x < 760) panel.getCharacter().setX(x + 40);
                    panel.repaint();
                    break;
                case 37: // lewo
                    if (x > 40) panel.getCharacter().setX(x - 40);
                    panel.repaint();
                    break;
                case 38: // gora
                    if (y > 40) panel.getCharacter().setY(y - 40);
                    panel.repaint();
                    break;
                case 40: // dol
                    if (y < 560) panel.getCharacter().setY(y + 40);
                    panel.repaint();
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
