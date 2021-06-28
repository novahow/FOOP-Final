package hero;

import java.awt.*;

public class Cowboy extends Hero {
    public static final int HP = 500;
    public static final int DAMAGE = 100;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Cowboy(Point location) {
        super(HP, "assets/cowboy/");
        this.damage = DAMAGE;
        this.location = location;
    }
}