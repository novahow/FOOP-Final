package model;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;

public class Card{
    private String filename;
    private String skill;
    private String name;
    private JLabel cardBox;
    private int charNum;
    private String shoot;

    public Card(String filename, String s, String skill, int charNum, String shoot){
        name = s;
        this.filename = "./assets/Sprites/" + filename;
        this.skill = skill;
        this.charNum = charNum;
        this.shoot = shoot;
        System.out.printf("%s\n", this.filename);
        ImageIcon imageIcon = new ImageIcon("./assets/Sprites/cardB.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        // imageIcon = new ImageIcon(newimg);  // transform it back
        MyLabel container = new MyLabel(new ImageIcon(newimg));
        container.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        GridLayout temp = new GridLayout(2, 1);
        temp.setVgap(20);
        container.setLayout(temp);

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(this.filename));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        // test.setMinimumSize(new Dimension(150, 100));
        Image dimg = img.getScaledInstance(150, 100,
            Image.SCALE_SMOOTH);
        JLabel test = new JLabel(new ImageIcon(dimg), JLabel.CENTER);
        // test.setHorizontalTextPosition(JLabel.CENTER);
        // test.setBorder(BorderFactory.createLineBorder(Color.black));
        test.setVerticalAlignment(SwingConstants.TOP);
        container.add(test);
        JLabel box2 = new JLabel();
        JLabel textLabel = new JLabel();
        GridLayout temp2 = new GridLayout(1, 1); 
        temp2.setVgap(10);
        // box2.setBorder(BorderFactory.createLineBorder(Color.black));
        textLabel.setFont(textLabel.getFont ().deriveFont (20.0f));
        if(this.shoot != null){
            textLabel.setText("<html>Skill1: " + skill + "<br>Skill2: " + shoot + "</html>");
        }
        else{
            textLabel.setText("<html>Skill1: " + skill + "</html>");
        }
        
        box2.setBackground(Color.lightGray);
        // box2.setOpaque(true);
        textLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // textLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        box2.setLayout(temp2);
        box2.add(textLabel);
        container.add((box2));
        cardBox = container;
        cardBox.setVisible(true);
        
    }

    public JLabel getCard(){
        return cardBox;

    }

    public void setVisible(boolean target){
        cardBox.setVisible(target);
    }

    public void addListener(MouseInputAdapter m){
        cardBox.addMouseListener(m);
    }

    public int getNum(){
        return charNum;
    }

    public class MyLabel extends JLabel{
        public MyLabel(ImageIcon img){
            super(img);
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString(name, 140 + 25 - (3 * name.length()), 175);
        }
    }
}
