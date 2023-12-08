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

        character = new Character(2, 562);

        image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\character.png");

    }

    public Character getCharacter() {
        return character;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        try {
            g2d.setColor(new Color(104, 176, 96));
            for(int i = 1; i < 20; i++) {
                g2d.drawLine(i*40, 0, i*40, 600);
                if(i < 15) g2d.drawLine(0, i*40, 800, i*40);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawImage(image, character.getX(), character.getY(), null);

        } catch (Exception e) {}

    }
}
