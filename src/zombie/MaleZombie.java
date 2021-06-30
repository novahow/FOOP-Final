package zombie;

import java.awt.Dimension;
import java.awt.Point;

import model.HealthPointSprite;

public class MaleZombie extends Zombie {
    private static final Dimension bodyOffset = new Dimension(26, 29);
    private static final Dimension bodySize = new Dimension(89, 143);

    public MaleZombie(Point location, HealthPointSprite target) {
        super("assets/male_zombie/", bodyOffset, bodySize, target);
        this.location = location;
    }
    
}
