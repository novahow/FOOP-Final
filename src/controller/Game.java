package controller;

// import knight.Knight;
import model.Direction;
import model.World;

public class Game extends GameLoop {
    // private final Knight p1;
    // private final Knight p2;
    private final World world;

    public Game(World world) {
        // this.p1 = p1;
        // this.p2 = p2;
        this.world = world;
    }

    // public void moveKnight(int playerNumber, Direction direction) {
    //     getPlayer(playerNumber).move(direction);
    // }

    // public void stopKnight(int playerNumber, Direction direction) {
    //     getPlayer(playerNumber).stop(direction);
    // }

    // public void attack(int playerNumber) {
    //     getPlayer(playerNumber).attack();
    // }

    // public Knight getPlayer(int playerNumber) {
    //     return playerNumber == 1 ? p1 : p2;
    // }

    @Override
    protected World getWorld() {
        return world;
    }
}