package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import javax.swing.ImageIcon;
import javax.swing.plaf.DimensionUIResource;

import maps.Tiles;

import java.util.Random;

public class WorldButton{
    private Dimension pos;
    private Dimension size;
    private ArrayList<Image> imgs;
    private int index = 0;

    public WorldButton(Dimension pos, Dimension size,
            String... img){
            this.pos = pos;
            this.size = size;
            imgs = new ArrayList<>();
            for(String s: img){
                Image image = new ImageIcon("./assets/gamebuttons/" + s + ".png").getImage();
                imgs.add(image);
            }
    }

    public Dimension getSize() {
        return size;
    }

    public Dimension getPos(){
        return pos;
    }

    public void render(Graphics g){
        // System.out.printf("pos, size = \n");
        g.drawImage(imgs.get(index), (int)pos.getWidth(), (int)pos.getHeight(), (int)size.getWidth(), (int)size.getHeight(), null);
    }

    public boolean isClicked(int x, int y){
        return (pos.getWidth() < x && 
                x < pos.getWidth() + size.getWidth() && 
                 pos.getHeight() < y && 
                    y < pos.getHeight() + size.getHeight());
    }

    public int clickbutton(int x, int y){
        if(isClicked(x, y)){
            index = 1 - index;
        }
        return index;
    }
}