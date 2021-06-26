package controller;

import model.World;
import model.Homepage;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class GameLoop {
    private boolean running;
    private View view;

    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        //new Thread(this::homeLoop).start();
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {        
        // Homepage home = getHome();
        while(true) {
        Homepage home = getHome();
        System.out.println(home.isRunning());
        while(home.isRunning()) {
            //home.update();
            view.render(home);
            delay(10);
            home = getHome();
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
    }
}
