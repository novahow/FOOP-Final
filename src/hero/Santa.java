package hero;

import java.awt.*;

public class Santa extends Hero {
    public static final int HP = 500;
    public static final int DAMAGE = 100;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Santa(Point location) {
        super(HP, "assets/santa/");
        this.damage = DAMAGE;
        this.location = location;
    }
}
