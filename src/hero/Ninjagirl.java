package hero;

import java.awt.*;

public class Ninjagirl extends Hero {
    public static final int HP = 500;
    public static final int DAMAGE = 100;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Ninjagirl(Point location) {
        super(HP, "assets/ninjagirl/");
        this.damage = DAMAGE;
        this.location = location;
    }
}
