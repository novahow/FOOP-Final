package controller;

import model.World;
import model.Homepage;
import model.RoleSelect;
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
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {        
        while(true) {
            Homepage home = getHome();
            RoleSelect roleselect = getRoleSelect();
            System.out.println(home.isRunning());
            while(home.isRunning()) {
                //home.update();
                view.render(home);
                delay(10);
                home = getHome();
            } 

            while(roleselect.isRunning() && !home.isRunning()) {
                //home.update();
                view.render(roleselect);
                delay(10);
                roleselect = getRoleSelect();
            } 
            // home.nextRound is the round clicked by the user
            running = true;
            World world = getWorld();
            while (world.isRunning() && running) {
                // World world = getWorld();
                world.update();
                view.render(world);
                delay(15);
            }
            restart();
        }
    }

    protected abstract World getWorld();
    protected abstract Homepage getHome();
    protected abstract void restart();
    protected abstract RoleSelect getRoleSelect();

    public void stop() {
        running = false;
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
        void addPanel(JPanel roleselect);
        void render(RoleSelect roleselect);
    }
}
