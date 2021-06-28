package hero;

import java.awt.*;

public class Cowgirl extends Hero {
    public static final int HP = 500;
    public static final int DAMAGE = 100;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Cowgirl(Point location) {
        super(HP, "assets/cowgirl/");
        this.damage = DAMAGE;
        this.location = location;
    }
}
