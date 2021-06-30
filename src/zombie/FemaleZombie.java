package zombie;

import java.awt.Dimension;
import java.awt.Point;

import model.HealthPointSprite;

public class FemaleZombie extends Zombie {
    private static final Dimension bodyOffset = new Dimension(42, 35);
    private static final Dimension bodySize = new Dimension(85, 136);

    public FemaleZombie(Point location, HealthPointSprite target) {
        super("assets/female_zombie/", bodyOffset, bodySize, target);
        this.location = location;
    }
    
}
