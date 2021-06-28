package model;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;

import java.awt.image.BufferStrategy;

public class Homepage extends JPanel{
    private ArrayList<CircleButton> butts; 
    private Boolean running;
    private ActionListener mouseListener ;
    private int clickedNum;
    private int width = 1200, height = 760;
    GridBagLayout griadLayout;
    public Homepage(){
        butts = new ArrayList<>();
        

        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        griadLayout = new GridBagLayout();
        setLayout(griadLayout);
        setVisible(false);
        setOpaque(false);
        running = true;

        mouseListener =  new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.printf("fuck\n");
                int cnt = 0;
                for(CircleButton c: butts){
                    if(c == e.getSource()){
                        clickedNum = cnt;
                        leave();
                    }
                    cnt += 1;       
                }
            }
        };

        // addMouseListener(mouselisten);
        


        setPreferredSize(new Dimension(width, height));
        
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("./assets/gamebuttons/Welcome.png"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        // test.setMinimumSize(new Dimension(150, 100));
        

        GridBagConstraints c0 = new GridBagConstraints();
        Image dimg = img.getScaledInstance(600, 300,
            Image.SCALE_SMOOTH);
        JLabel test = new JLabel(new ImageIcon(dimg), JLabel.CENTER);
        c0.gridx = 0; 
        c0.gridy = 0;
        c0.gridwidth = 6;
        c0.gridheight = 1;
        // c0.weightx = 1;
        // c0.weighty = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.CENTER;
        add(test, c0);
        JLabel buttonContainer = new JLabel();
        GridBagLayout gbl = new GridBagLayout();
        // buttonContainer.setLayout((gbl));
        // test.setBorder(BorderFactory.createLineBorder(Color.RED));
        for(int i = 1; i <= 3; i++){
            Integer i1 = i;
            String name = "./assets/gamebuttons/" + i1.toString() + ".png";
            try {
                img = ImageIO.read(new File(name));
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            dimg = img.getScaledInstance(150, 150,
                Image.SCALE_SMOOTH);
            
            CircleButton button = new CircleButton(new ImageIcon(dimg));
            name = "./assets/gamebuttons/" + i1.toString() + "_1.png";
            try {
                img = ImageIO.read(new File(name));
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            dimg = img.getScaledInstance(150, 150,
                Image.SCALE_SMOOTH);
            
            button.addHover(new ImageIcon(dimg), dimg);
            butts.add(button);
        }

        for(CircleButton e: butts){
            e.setVisible(true);
            e.addActionListener(mouseListener);
        }

        for(int i = 0; i < butts.size(); i++){
            CircleButton e = butts.get(i);
            c0.gridx = 2 * i;
            c0.gridy = 1;
            c0.gridwidth = 1;
            c0.gridheight = 1;
            // c0.weightx = 0;
            // c0.weighty = 0.1;
            c0.fill = GridBagConstraints.NONE;
            c0.anchor = GridBagConstraints.CENTER;
            c0.insets = new Insets(70, 70, 70, 70);
            gbl.setConstraints(e, c0);
            e.setPreferredSize(new Dimension(138, 138));
            e.setBorder(BorderFactory.createLineBorder(Color.RED));
            // e.setHorizontalAlignment(SwingConstants.CENTER);
            e.setBorder(BorderFactory.createLineBorder(Color.RED));
            add(e, c0);
        }
        // add(buttonContainer);
        // super.setVisible(true);
    }
    public void restart() {
        for(CircleButton e: butts){
            e.setVisible(true);
        }
        setVisible(true);
        running = true;
    }

    public void leave(){
        for(CircleButton e: butts){
            e.setVisible(false);
        }
        setVisible(false);
        running = false;

    }

    public Boolean isRunning() {
        return running;
    }

    public void paintComponent(Graphics g) {
        // System.out.printf("paintme?");
        super.paintComponent(g);
        // g.drawImage(new ImageIcon("./assets/Sprites/Sel.jpg").getImage(), 0, 0, width, height, null);
    }

    public int getIndex(){
        return clickedNum;
    }
}

