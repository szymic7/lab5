package pl.pwr.smichalowski.lab5.klasy_elementow_graficznych;

import java.awt.*;

public class Character {

    private int x;
    private int y;
    private static final Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\pliki_png\\character.png");
    // https://fonts.google.com/icons?selected=Material+Icons:man:

    public Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Image getImage() {
        return image;
    }

}
