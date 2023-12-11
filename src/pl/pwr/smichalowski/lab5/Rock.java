package pl.pwr.smichalowski.lab5;

import java.util.Objects;
import java.util.Random;

public class Rock {

    private int x;
    private int y;

    public Rock() {
        Random random = new Random();
        this.x = (random.nextInt(19) * 40) + 4;
        this.y = 4;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rock rock = (Rock) o;
        return x == rock.x && y == rock.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
