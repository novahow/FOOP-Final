package zombie;

import java.awt.Dimension;
import java.awt.Point;

import model.HealthPointSprite;

public class FemaleZombie extends Zombie {
    private static final int hp = 500;
    private static final Dimension size = new Dimension(146, 146);
    private static final Dimension bodyOffset = new Dimension(42, 5);
    private static final Dimension bodySize = new Dimension(85, 136);

    public FemaleZombie(Point location, HealthPointSprite target) {
        super(hp, "assets/female_zombie/", size, bodyOffset, bodySize, target);
        this.location = location;
    }
    
}
