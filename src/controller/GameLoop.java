package controller;

import model.World;
import model.Homepage;
import model.RoleSelect;
import model.Pause;
import javax.swing.*;
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
    }

    public void start() {
        //new Thread(this::homeLoop).start();
        running = true;
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {        
        view.addPanel(getRoleSelect());
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
            while (world.isRunning() && running) {
                if(world.isPause()) {
                    //view.render(world);
                    view.render(pausepage);
                    delay(100);
                }
                else {
                    world.update();
                    view.render(world);
                    delay(15);    
                }
            }
            if(!running) {
                break;
            }
            restart();
        }
        System.out.println("exited");
    }

    protected abstract World getWorld();
    protected abstract Homepage getHome();
    protected abstract Pause getPause();
    protected abstract void restart();
    protected abstract RoleSelect getRoleSelect();

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
    }
}
