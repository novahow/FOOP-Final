package controller;

import knight.Knight;
import model.Direction;
import model.Homepage;
import model.World;

import controller.Game;
import knight.Attacking;
import knight.Knight;
import knight.KnightCollisionHandler;
import knight.Walking;
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
    private Knight p1;
    private Knight p2;
    private World world;
    private Homepage homepage;

    public Game(World world, Knight p1, Knight p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.world = world;
        this.homepage = new Homepage();
    }
    @Override
    protected void restart() {
        Knight p1 = new Knight(100, new Point(0, 0));
        Knight p2 = new Knight(150, new Point(300, 300));
        World world = new World(new KnightCollisionHandler(), p1, p2);
        this.p1 = p1;
        this.p2 = p2;
        this.world = world;
        this.homepage = new Homepage();
    }

    public void moveKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).move(direction);
    }

    public void stopKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).stop(direction);
    }

    public void attack(int playerNumber) {
        getPlayer(playerNumber).attack();
    }

    public void jump(int playerNumber) {
        getPlayer(playerNumber).jump();
    }

    public Knight getPlayer(int playerNumber) {
        return playerNumber == 1 ? p1 : p2;
    }

    @Override
    protected World getWorld() {
        return world;
    }

    @Override 
    protected Homepage getHome() {
        return homepage;
    }

    public void clickButton(int x, int y, int release) {
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
}
