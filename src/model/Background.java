package model;

import javax.swing.ImageIcon;
import java.awt.*;

public class Background {
    private int x;
    private int y;
    private int size;
    private String path;

    public Background(int x, int y, int size, String path) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.path = path;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSize() {
        return 5 * size;
    }

    public void render(Graphics g) {
        ImageIcon object = new ImageIcon(path);
        Image bg = object.getImage();
        g.drawImage(bg ,x, y, 5 * size, 2 * size, null);
    }
}
