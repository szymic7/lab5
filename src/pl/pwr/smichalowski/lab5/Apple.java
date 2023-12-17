package pl.pwr.smichalowski.lab5;

import java.awt.*;

public class Apple extends Supply {

    private static final Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\szymo\\IdeaProjects\\lab5\\src\\pl\\pwr\\smichalowski\\lab5\\apple.png");
    // <a href="https://www.flaticon.com/free-icons/apple" title="apple icons">Apple icons created by Creative Stall Premium - Flaticon</a>

    public Apple() {
        super();
    }

    public static Image getImage() {
        return image;
    }

}
