package hero;

import java.awt.*;

public class Santa extends Hero {
    public static final int HP = 500;
    public static final int DAMAGE = 100;
    private static final Dimension size = new Dimension(146, 176);
    private static final Dimension bodyOffset = new Dimension(31, 12);
    private static final Dimension bodySize = new Dimension(53, 146);

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Santa(Point location) {
        super(HP, "assets/santa/", size, bodyOffset, bodySize);
        this.damage = DAMAGE;
        this.location = location;
    }
}
