package model;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class CircleButton extends JButton{
	
	private boolean mouseOver = false;
	private boolean mousePressed = false;
    private ImageIcon hovered;
    private ImageIcon original;
    private Image hoverImg;
     
    public CircleButton(ImageIcon label) {
        super(label);
        original = label;
        setBackground(Color.lightGray);
        setFocusable(false);
    
        /*
        These statements enlarge the button so that it 
        becomes a circle rather than an oval.
        */
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
    
        /*
        This call causes the JButton not to paint the background.
        This allows us to paint a round background.
        */
        setContentAreaFilled(false);
            MouseAdapter mouseListener = new MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setIcon(hovered);
                repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setIcon(original); 
                repaint();           
            }

		};
		
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);		

    }

    
 
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
        g.setColor(Color.gray);
        } else {
        g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
    
        super.paintComponent(g);
    }
 
    protected void paintBorder(Graphics g) {
        g.setColor(Color.darkGray);
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }
 
  // Hit detection.
    Shape shape;

    @Override
    public void setPreferredSize(Dimension arg0) {
        // TODO Auto-generated method stub
        super.setPreferredSize(arg0);
    }

    private int getDiameter(){
        int diameter = Math.min(getWidth(), getHeight());
        // System.out.printf("wh = %d %d\n", getWidth(), getHeight());
        return diameter;
    }
    
    public void addHover(ImageIcon img, Image hov){
        hovered = img;
        hoverImg = hov;
    }
    
    @Override
    public boolean contains(int x, int y){
        int radius = getDiameter()/2;
        return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
    }
}