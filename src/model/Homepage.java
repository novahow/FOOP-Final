package model;
import java.util.ArrayList; 

import java.awt.*;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;

//import static java.util.Arrays.stream;
//import static java.util.stream.Collectors.toSet;

public class Homepage {
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
    Boolean running;
    int nextRound;
    ArrayList<button> buttons;
    
    public Homepage() {
        running = true;
        buttons = new ArrayList<button>();
        buttons.add(new button(Color.GRAY, Color.lightGray, 125, 250, 50, 50));
        buttons.add(new button(Color.GRAY, Color.lightGray, 225, 250, 50, 50));
        buttons.add(new button(Color.GRAY, Color.lightGray, 325, 250, 50, 50));
    }
    
    public void leave(int res) {
        running = false;
        nextRound = res;
    }
    public Boolean isRunning() {
        return running;
    }
    public int clickButton(int x, int y, int release) {
        if(release == 0) {
            for(button b: buttons) {
                if(b.x <= x && x <= b.x + b.dx && b.y <= y-30 && y-30 <= b.y + b.dy) {
                    b.state = 1;
                }
            }    
            return -1;
        }
        else {
            int res = -1;
            for(int i=0;i<buttons.size();i++) {
                button b = buttons.get(i);
                if(b.state == 1) {
                    b.state = 0;
                    res = i;
                }
            } 
            return res;
        }
        
    }

    public void render(Graphics g) {
        //System.out.println("rendering");
        g.setColor(Color.RED);
        g.fillRect(100, 100, 300, 100);
        for(button b: buttons) {
            if(b.state == 0) {
                g.setColor(b.origColor);
            }
            else {
                g.setColor(b.clickedColor);
            }
            g.fillRect(b.x, b.y, b.dx, b.dy);
        }
        return;
    }
}
