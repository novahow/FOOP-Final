package model;
import java.util.ArrayList; 

import java.awt.*;
import javax.swing.ImageIcon;

public class Pause {
    public class button {
        int x, y, dx, dy;
        public button(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }
    };

    ArrayList<button> buttons;

    public Pause() {
        buttons = new ArrayList<button>();
        buttons.add(new button(440, 245, 325, 85));
        buttons.add(new button(440, 357, 325, 85));
        buttons.add(new button(440, 465, 325, 85));
        buttons.add(new button(440, 576, 325, 85));
    }

    public int clickButton(int x, int y) {
        System.out.println(x+" "+y);
        for(int i=0;i<buttons.size();i++) {
            button b = buttons.get(i);
            if(b.x <= x && x <= b.x + b.dx && b.y <= y-30 && y-30 <= b.y + b.dy) {
                return i;
            }
        } 
        return -1;
    }


    public void render(Graphics g) {
        ImageIcon i = new ImageIcon("assets/pause/menu.png");
        Image bg = i.getImage();
        g.drawImage(bg, 350, 150, 500, 500, null);
    }
}
