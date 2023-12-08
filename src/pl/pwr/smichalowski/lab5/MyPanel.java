package pl.pwr.smichalowski.lab5;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MyPanel extends JPanel {

    Image image;
    Graphics2D g2d;
    Character character;

    public MyPanel() {

        this.setBackground(new Color(141, 218, 134));
        this.setBorder(new LineBorder(Color.BLACK, 5, true));
        this.setLayout(null);
        this.setFocusable(false);

        character = new Character(100, 100);

        image = Toolkit.getDefaultToolkit().getImage("character.png");
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        try {
            g2d.setColor(Color.BLACK);
            g2d.drawImage(image, character.getX(), character.getY(), this);
        } finally {
            g2d.dispose();
        }

    }
}
