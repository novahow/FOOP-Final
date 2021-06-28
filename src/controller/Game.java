package controller;

import model.Direction;
import model.Homepage;
import model.World;
import states.Attacking;
import states.Walking;
import model.RoleSelect;
import model.SpriteCollisionHandler;
import controller.Game;
import hero.*;
import model.HealthPointSprite;
import model.World;
import model.Pause;
import views.GameView;

import java.awt.*;
import java.io.File;

import static media.AudioPlayer.addAudioByFilePath;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game extends GameLoop {
    private Hero p1;
    // private Knight p2;
    private World world;
    private Homepage homepage;
    private RoleSelect roleselect;
    private Pause pausepage;

    public Game(World world, Hero p1) {
        this.p1 = p1;
        // this.p2 = p2;
        this.world = world;
        this.homepage = new Homepage();
        this.roleselect = new RoleSelect();
        this.pausepage = new Pause();
    }

    @Override
    protected void restart() {
        this.p1 = new Ninja(new Point(0, 535));
        this.world = new World(new SpriteCollisionHandler(), p1);
    }

    public void moveKnight(int playerNumber, Direction direction) {
        world.setjump(false);
        getPlayer(playerNumber).move(direction);
    }

    public void stopKnight(int playerNumber, Direction direction) {
        world.setjump(false);
        getPlayer(playerNumber).stop(direction);
    }

    public void attack(int playerNumber) {
        world.setjump(false);
        getPlayer(playerNumber).attack();
    }

    public void shoot(int playerNumber) {
        world.setjump(false);
        getPlayer(playerNumber).shoot();
    }

    public void jump(int playerNumber) {
        world.setjump(true);
        getPlayer(playerNumber).jump();
    }

    public Hero getPlayer(int playerNumber) {
        // return playerNumber == 1 ? p1 : p2;
        return p1;
    }

    public void pause() {
        if(world.isRunning()) {
            world.setPause();
        }
    }

    @Override
    protected World getWorld() {
        return world;
    }

    @Override 
    protected Homepage getHome() {
        return homepage;
    }

    @Override 
    protected RoleSelect getRoleSelect() {
        return roleselect;
    }

    @Override
    protected Pause getPause() {
        return pausepage;
    }

    public void clickButton(int x, int y, int release) {
        /*if(homepage.isRunning()){
            int res = homepage.clickButton(x, y, release);
            if(release == 1) {
                if(res == -1) {
                    System.out.printf("Clicked on nowhere\n");
                }
                else {
                    System.out.printf("Clicked on box %d\n", res);
                    homepage.leave(res);
                }
            }
        }
        else */if(world.isPause()) {
            if(release == 1) {
                int res = pausepage.clickButton(x, y);
                if(res == 0) {
                    world.setPause();
                }
                else if(res == 1) {
                    world.stop();
                }
                else if(res == 3) {
                    stop();
                }
            }            
        }
    }
}
