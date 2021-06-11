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
        Homepage home = getHome();
        while(home.isRunning()) {
            //home.update();
            view.render(home);
            delay(10);
            home = getHome();
        }
        running = true;
        while (running) {
            World world = getWorld();
            world.update();
            view.render(world);
            delay(15);
        }
    }

    protected abstract World getWorld();
    protected abstract Homepage getHome();

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
