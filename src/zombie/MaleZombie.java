package zombie;

import java.awt.Dimension;
import java.awt.Point;

public class MaleZombie extends Zombie {
    private static final Dimension bodyOffset = new Dimension(26, 29);
    private static final Dimension bodySize = new Dimension(89, 143);

    public MaleZombie(Point location) {
        super("assets/male_zombie/", bodyOffset, bodySize);
        this.location = location;
    }
    
}
