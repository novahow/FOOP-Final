package model;
import java.awt.*;
import javax.swing.*;


public class TutorPage extends JPanel{
    private Image img;
    public TutorPage(){
        img = new GetSizedImage("assets/gamebuttons/tutorpage.png", 600, 600).getImage();
        setVisible(false);
        setOpaque(false);
        setPreferredSize(new Dimension(600, 600));
        setAlignmentX(0.5f);
        setLayout(new GridBagLayout());
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, 600, 600, null);
        // g.drawImage(new ImageIcon("./assets/Sprites/Sel.jpg").getImage(), 0, 0, width, height, null);
    }

}

