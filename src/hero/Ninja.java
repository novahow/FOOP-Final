package hero;

import java.awt.*;

public class Ninja extends Hero {
    public static final int HP = 500;
    public static final int DAMAGE = 100;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Ninja(Point location) {
        super(HP, "assets/ninja/");
        this.damage = DAMAGE;
        this.location = location;
    }
}