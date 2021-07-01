package controller;

import model.World;
import model.WorldButton;
import model.Homepage;
import model.RoleSelect;
import model.Pause;
import model.EndButton;
import javax.swing.*;
import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class GameLoop {
    private boolean running;
    private View view;

    public void setView(View view) {
        this.view = view;
        this.view.addPanel(getHome());
        this.view.addPanel(getRoleSelect());
        this.view.addPanel(getPause());
        this.view.addButton(getEnd());

    }

    public void start() {
        //new Thread(this::homeLoop).start();
        running = true;
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {        
        while(running) {
            Homepage home = getHome();
            RoleSelect roleselect = getRoleSelect();
            home.restart();
            while(home.isRunning()) {
                view.render(home);
                delay(10);
                home = getHome();
            }
            roleselect.restart();
            while(roleselect.isRunning() && !home.isRunning()) {
                view.render(roleselect);
                delay(10);
                roleselect = getRoleSelect();
            } 

            // home.nextRound is the round clicked by the user
            running = true;
            World world = getWorld();
            Pause pausepage = getPause();
            pausepage.restart();
            while (world.isRunning() && running) {
                if(world.isPause() && pausepage.getPause()) {
                    view.render(pausepage);
                    delay(100);
                }
                else {
                    world.update();
                    view.render(world);
                    delay(15);    
                }
            }

            EndButton endbtn = getEnd();
            if(!world.isRunning() && world.isEnd()){
                endbtn.setVisible(true);
            }

            while(endbtn.isVisible()){
                view.render(world);
                if(!endbtn.isVisible()){
                    break;
                }
            }


            if(!running) {
                break;
            }

            home = getHome();
            pausepage = getPause();
            roleselect = getRoleSelect();
            home.setVisible(false);
            pausepage.setVisible(false);
            roleselect.setVisible(false);
            restart();
        }

    }

    protected abstract World getWorld();
    protected abstract Homepage getHome();
    protected abstract Pause getPause();
    protected abstract EndButton getEnd();
    protected abstract void restart();
    protected abstract void setChoose(int c);
    protected abstract RoleSelect getRoleSelect();
    protected abstract void addButton(WorldButton b);

    public void stop() {
        running = false;
    }

    public boolean isExit() {
        return !running;
    }

    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public interface View {
        void render(World world);
        void render(Homepage home);
        void render(Pause pausepage);
        void addPanel(JPanel roleselect);
        void render(RoleSelect roleselect);
        void addGame(Game game);
        void disposeParentFrame();
        void addButton(JButton btn);
    }
}
