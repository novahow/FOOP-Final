package model;
import java.util.ArrayList; 

import java.awt.*;
import javax.swing.ImageIcon;

public class Pause {
    public class button {
        Color origColor, clickedColor;
        int x, y, dx, dy, state;
        public button(Color c, Color c2, int x, int y, int dx, int dy) {
            this.origColor = c;
            this.clickedColor = c2;
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.state = 0;
        }
    };

    public void render(Graphics g) {
        ImageIcon i = new ImageIcon("assets/pause/menu.png");
        Image bg = i.getImage();
        g.drawImage(bg, 350, 150, 500, 500, null);
    }
}
