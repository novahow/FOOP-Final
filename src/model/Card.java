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

    public Card(String filename, String s, String skill, int charNum){
        name = s;
        this.filename = "./assets/Sprites/" + filename;
        this.skill = skill;
        this.charNum = charNum;

        System.out.printf("%s\n", this.filename);
        JLabel container = new JLabel();
        container.setLayout(new GridLayout(1, 2));
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(this.filename));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        // test.setMinimumSize(new Dimension(150, 100));
        Image dimg = img.getScaledInstance(150, 300,
        Image.SCALE_SMOOTH);
        JLabel test = new JLabel(new ImageIcon(dimg), JLabel.RIGHT);
        test.setHorizontalTextPosition(JLabel.RIGHT);
        
        container.add(test);
        JLabel box2 = new JLabel();
        // box2.setBorder(BorderFactory.createLineBorder(Color.black));
        box2.add(new JLabel("<html>Name: " + name + "<br>Skill: " + skill + "</html>"));
        box2.setBackground(Color.lightGray);
        box2.setOpaque(true);
        box2.setLayout(new GridBagLayout());
        box2.setHorizontalTextPosition(JLabel.CENTER);
        box2.setVerticalTextPosition(JLabel.CENTER);
        container.add((box2));
        container.setBorder(BorderFactory.createLineBorder(Color.blue));
        cardBox = container;
        // cardBox.setAlignmentX((float)0.5);
        // cardBox.setAlignmentY((float)0.5);
        cardBox.setVisible(true);
        /*cardBox.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
                System.out.printf("char = %d\n", charNum);
                // return charNum;
            }  
        }); */
        // cardBox.setPreferredSize(new Dimension(330, 220));
        // super.add(box);
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
}
