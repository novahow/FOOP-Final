package model;
import java.awt.*;
import java.util.ArrayList;
import views.GameView;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;

public class RoleSelect extends JPanel {
    private ArrayList<Card> cards; 
    private Boolean running;
    private MouseInputAdapter mouselisten;
    private int clickedNum;
    private int width = 1200, height = 760;
    GridLayout griadLayout;
    public RoleSelect(){
        cards = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            Integer i1 = i, i2 = i + 1;
            cards.add(new Card(i1.toString() + ".png", 
                "zelda" + (i2).toString(), "S" + (i2).toString(),
            i));
        }

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
                
                System.out.printf("clicked at %d %d\n", e.getX(), e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // game.clickButton(e.getX(), e.getY(), 1);
                int cnt = 0;
                for(Card c: cards){
                    if(c.getCard() == e.getSource()){
                        System.out.printf("released %d %d %d\n",
                         cnt, e.getX(), e.getY());
                        clickedNum = cnt;
                        leave();
                    }
                    cnt += 1;       
                }
            }
        };

        // addMouseListener(mouselisten);

        for(Card e: cards){
            e.setVisible(true);
            e.addListener(mouselisten);
        }
        setPreferredSize(new Dimension(width, height));
        // super.setVisible(true);
    }


    public void leave(){
        for(Card e: cards){
            e.setVisible(false);
        }
        setVisible(false);
        System.out.printf("nowChar = %d\n", clickedNum);
        running = false;

    }

    public Boolean isRunning() {
        return running;
    }

    public void restart() {
        for(Card e: cards){
            e.setVisible(true);
        }
        setVisible(true);
        running = true;        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getIndex(){
        return clickedNum;
    }
}
