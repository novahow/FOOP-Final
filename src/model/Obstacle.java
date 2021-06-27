package model;

import java.awt.*;

public class Obstacle {
    private int x;
    private int y;
    private int size;

    public Obstacle(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, size, 50);
    }
}
