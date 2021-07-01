package hero;

import java.awt.*;

public class Ninja extends Hero {
    public static final int HP = 1000;
    public static final int DAMAGE = 100;
    private static final Dimension size = new Dimension(117, 141);
    private static final Dimension bodyOffset = new Dimension(0, 0);
    private static final Dimension bodySize = new Dimension(117, 141);

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Ninja(Point location) {
        super(HP, "assets/ninja/", size, bodyOffset, bodySize);
        this.damage = DAMAGE;
        this.location = location;
    }
}