package model;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;
import static java.util.Arrays.stream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import media.AudioPlayer;
import model.Card.MyLabel;

import java.util.Random;

public class WorldButton{
    private Dimension pos;
    private Dimension size;
    private ArrayList<Image> imgs = new ArrayList<>();
    private ArrayList<JButton> butts =  new ArrayList<>();
    private JLabel myLabel;
    private int seqNumber;
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

    public WorldButton(Dimension size, String... names){
        this.size = size;
        Image img = null, dimg;
        for(String s: names){
            String name = "./assets/gamebuttons/" + s + ".png";
            img = new ImageIcon(name).getImage();
            dimg = img.getScaledInstance((int)size.getWidth(), (int)size.getHeight(),
                Image.SCALE_SMOOTH);
            
            JButton button = new JButton();
            button.setMaximumSize(size);
            button.setIcon(new ImageIcon(dimg));
            button.setFocusable(false);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setLayout(new GridBagLayout());
            butts.add(button);
            // button.setBorder(BorderFactory.createLineBorder(Color.RED));
        }

        if(butts.size() > 1){
            myLabel = new JLabel();
            GridBagLayout gbl = new GridBagLayout();
            myLabel.setLayout(gbl);
            GridBagConstraints c0 = new GridBagConstraints();
            for(JButton b: butts){
                c0.gridx = 0;
                c0.gridy = 0;
                c0.gridwidth = 1;
                c0.gridheight = 1;
                c0.weightx = 0;
                c0.weighty = 0;
                c0.fill = GridBagConstraints.NONE;
                c0.anchor = GridBagConstraints.SOUTH;
                myLabel.add(b, c0);
                b.setVisible(false);
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
            }
        }
        butts.get(0).setVisible(true);

    }

    public Dimension getSize() {
        return size;
    }

    public Dimension getPos(){
        return pos;
    }

    public JLabel getLabel(){
        return myLabel;
    }

    public JButton getButt(){
        return butts.get(index);
    }

    public void render(Graphics g){
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

    public void addListener(MouseInputAdapter me){
        for(JButton b: butts){
            b.addMouseListener(me);
        }
    }

    public void trigger(){
        index = (index + 1) % butts.size();
        for(JButton b: butts){
            if(b == butts.get(index)){
                b.setVisible(true);
            }
            else{
                b.setVisible(false);
            }
            b.repaint();
        }
    }

    public void setMute(){
        boolean muted = (index == 0);
        AudioPlayer.setEnable(muted);
    }
}