package pl.pwr.smichalowski.lab5.klasy_elementow_graficznych;

import java.awt.*;

public class Water extends Supply {

    private static final Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\pliki_png\\water-bottle.png");
    // <a href="https://www.flaticon.com/free-icons/water" title="water icons">Water icons created by srip - Flaticon</a>

    public Water() {
        super();
    }

    public static Image getImage() {
        return image;
    }

}
