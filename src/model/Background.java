package model;

import javax.swing.ImageIcon;
import java.awt.*;

public class Background {
    private int x;
    private int y;
    private int width;
    private int height;
    private String path;

    public Background(int x, int y, int width, int height, String path) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.path = path;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSize() {
        return width;
    }

    public void render(Graphics g) {
        ImageIcon object = new ImageIcon(path);
        Image bg = object.getImage();
        g.drawImage(bg ,x, y, width, height, null);
    }
}
