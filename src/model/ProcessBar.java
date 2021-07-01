package model;

import javax.swing.ImageIcon;
import java.awt.*;

public class ProcessBar {
    private int length;
    private float f;
    private boolean end;

    public ProcessBar(int len) {
        this.length = len;
        this.f = 0.0f;
        this.end = false;
    }

    public void setF(float x) {
        // System.out.println(x);
        f += x / length;
        if (f <= 0) {
            f = 0;
        }
        if (f >= 1) 
            end = true;
    }

    public boolean isEnd() {
        return end; 
    }
    
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(1050, 20, length, 20);
        g.setColor(Color.BLUE);
        float c = 1050 + f * 100;
        int xbar = Math.round(c);
        g.fillOval(xbar-10, 20, 20, 20);
        ImageIcon i = new ImageIcon("assets/head-removebg-preview.png");
        Image bg = i.getImage();
        g.drawImage(bg, xbar-10, 20, 20, 20, null);
    }
}
