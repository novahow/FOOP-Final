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

    public Game(World world, Hero p1) {
        this.p1 = p1;
        // this.p2 = p2;
        this.world = world;
        this.homepage = new Homepage();
        this.roleselect = new RoleSelect();
    }
    @Override
    protected void restart() {
        Hero p1 = new Ninja(new Point(0, 535));
        // Ninja p2 = new Ninja(150, new Point(300, 0));
        World world = new World(new SpriteCollisionHandler(), p1);
        this.p1 = p1;
        // this.p2 = p2;
        this.world = world;
        this.homepage = new Homepage();
        this.roleselect = new RoleSelect();
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

    public void clickButton(int x, int y, int release) {
        int res = homepage.clickButton(x, y, release);
        if(homepage.isRunning()){
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
    }
}
