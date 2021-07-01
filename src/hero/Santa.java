package hero;

import java.awt.*;

public class Santa extends Hero {
    public static final int HP = 1000;
    public static final int DAMAGE = 100;
    private static final Dimension size = new Dimension(146, 159);
    private static final Dimension bodyOffset = new Dimension(31, 0);
    private static final Dimension bodySize = new Dimension(53, 141);

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Santa(Point location) {
        super(HP, "assets/santa/", size, bodyOffset, bodySize);
        this.damage = DAMAGE;
        this.location = location;
    }
}
