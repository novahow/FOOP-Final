package model;
import java.util.ArrayList; 
import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.event.MouseInputAdapter;
import controller.*;

import java.awt.event.*;

public class Pause extends JPanel{
    private ArrayList<CircleButton> butts = new ArrayList<>(); 
    private ArrayList<WorldButton> worldButts  = new ArrayList<>();
    private Boolean running;
    private MouseInputAdapter mouseListener ;
    private int clickedNum = -1;
    private boolean paused = false;
    private int width = 600, height = 600;
    GridLayout griadLayout;
    Game game;
    public Pause(){
        setBorder(BorderFactory.createEmptyBorder(80, 140, 100, 140));
        griadLayout = new GridLayout(4, 1, 30, 20);
        setLayout(griadLayout);
        setVisible(false);
        setOpaque(false);
        running = true;
        setName("pausepage");
        worldButts.add(new WorldButton(new Dimension(100, 100), "mute", "unmute"));
        worldButts.add(new WorldButton(new Dimension(350, 100), "continue"));
        worldButts.add(new WorldButton(new Dimension(350, 100), "newgame"));
        worldButts.add(new WorldButton(new Dimension(350, 100), "exitgame"));
        JLabel soundLabel  = worldButts.get(0).getLabel();

        mouseListener =  new MouseInputAdapter(){
            
            @Override
            public void mouseReleased(MouseEvent e){
                int cnt = 0;
                for(WorldButton c: worldButts){
                    if(c.getButt() == e.getSource()){
                        clickedNum = cnt;
                        Component src = (Component) e.getSource();
                        while(src.getParent() != null){
                            src = src.getParent();
                            System.out.printf("%s\n", src.getName());
                            if(src.getName() != null && src.getName().equals("canvas")){
                                break;
                            }
                        }

                        src.dispatchEvent(e);
                        c.trigger();
                        if(cnt == 0){
                            c.setMute();
                        }
                        else{
                            leave();
                        }
                        break;
                    }
                    cnt += 1;       
                }
                // dispatchEvent(e);
            }
        };

        addMouseListener(mouseListener);
        // setBorder(BorderFactory.createLineBorder(Color.RED));

        for(WorldButton b: worldButts){
            b.addListener(mouseListener);
        }
        setPreferredSize(new Dimension(width, height));
        soundLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        add(soundLabel);
        for(int i = 1; i <= 3; i++){
            JButton btn = worldButts.get(i).getButt();
            btn.setPreferredSize(worldButts.get(i).getSize());
            btn.setVerticalAlignment(SwingConstants.TOP);
            add(btn);
        }
    }

    public void addListener(MouseInputAdapter m){
        addMouseListener(m);
    }

    public void setPause(){
        paused = !paused;
        if(!paused){
            setVisible(false);
        }

        else{
            setVisible(true);
        }
    }

    public boolean getPause(){
        return paused;
    }

    public void restart() {
        for(CircleButton e: butts){
            e.setVisible(true);
        }
        setVisible(false);
        paused = false;
        worldButts.get(0).setMute();
    }

    public void leave(){

        setVisible(false);
        setPause();

    }

    public Boolean isRunning() {
        return running;
    }

    public void paintComponent(Graphics g) {
        // System.out.printf("paintme?\n");
        super.paintComponent(g);
        // repaint();
        g.drawImage(new ImageIcon("./assets/gamebuttons/woodPause.png").getImage(), 0, 0, width, height, null);
        // setVisible(true);
        // g.drawImage(new ImageIcon("./assets/Sprites/Sel.jpg").getImage(), 0, 0, width, height, null);
    }

    public int getClicked(){
        int tmp = clickedNum;
        clickedNum = -1;
        return tmp;
    }
}
