package pl.pwr.smichalowski.lab5;

import java.util.Objects;
import java.util.Random;

public abstract class Supply {
    private int x;
    private int y;

    public Supply() {
        Random random = new Random();
        this.x = (random.nextInt(19) * 40) + 4;
        this.y = (random.nextInt(14) * 40) + 4;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Supply supply = (Supply) o;
        return x == supply.x && y == supply.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
