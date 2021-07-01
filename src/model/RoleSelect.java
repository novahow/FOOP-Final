package model;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class RoleSelect extends JPanel {
    private ArrayList<Card> cards; 
    private Boolean running;
    private MouseInputAdapter mouselisten;
    private int clickedNum = -1;
    private int width = 1200, height = 760;
    GridLayout griadLayout;
    
    public RoleSelect(){
        setName("role");
        cards = new ArrayList<>();
        cards.add(new Card("0.png", "cowgirl", "牛刀小試", 0, "火球潘"));
        cards.add(new Card("1.png", "ninjagirl", "女力覺醒", 1, null)); 
        cards.add(new Card("2.png", "robot", "刀光劍影", 2, "機槍上膛")); 
        cards.add(new Card("3.png", "santa", "鏟球高手", 3, null)); 
        cards.add(new Card("4.png", "cowboy", "白金右腳", 4, null)); 
        cards.add(new Card("5.png", "ninja", "倚天屠龍", 5, null)); 

        for(int i = 0; i < cards.size(); i++){
            this.add(cards.get(i).getCard());
        }
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        griadLayout = new GridLayout(2, 3, 40, 40);
        setLayout(griadLayout);
        setVisible(false);
        running = true;

        mouselisten = new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // game.clickButton(e.getX(), e.getY(), 1);
                for(Component p: getComponents()){
                    // p.dispatchEvent(e);
                }
                int cnt = 0;
                for(Card c: cards){
                    if(c.getCard() == e.getSource()){
                        Component src = (Component) e.getSource();
                        while(src.getParent() != null){
                            src = src.getParent();
                            if(src.getName() != null && src.getName().equals("canvas")){
                                break;
                            }
                        }
                        clickedNum = cnt;
                        src.dispatchEvent(e);
                        break;
                    }
                    cnt += 1;       
                }
            }
        };

        addMouseListener(mouselisten);

        for(Card e: cards){
            e.setVisible(true);
            e.addListener(mouselisten);
        }
        setPreferredSize(new Dimension(width, height));
        // super.setVisible(true);
    }

    public int getClicked() {
        int tmp = clickedNum;
        clickedNum = -1;
        return tmp;
    }

    public void leave(){
        for(Card e: cards){
            e.setVisible(false);
        }
        setVisible(false);
        // System.out.printf("nowChar = %d\n", clickedNum);
        running = false;

    }

    public Boolean isRunning() {
        return running;
    }

    public void restart() {
        for(Card e: cards){
            e.setVisible(true);
        }
        setVisible(false);
        running = true;        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getIndex(){
        int tmp = clickedNum;
        clickedNum = -1;
        return tmp;
    }
}
