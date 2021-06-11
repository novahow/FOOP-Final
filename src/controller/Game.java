package controller;

import knight.Knight;
import model.Direction;
import model.Homepage;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game extends GameLoop {
    private final Knight p1;
    private final Knight p2;
    private final World world;
    private Homepage homepage;

    public Game(World world, Knight p1, Knight p2) {
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
