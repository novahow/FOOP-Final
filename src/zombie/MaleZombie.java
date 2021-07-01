package zombie;

import java.awt.Dimension;
import java.awt.Point;

import model.HealthPointSprite;

public class MaleZombie extends Zombie {
    private static final int hp = 500;
    private static final Dimension size = new Dimension(146, 176);
    private static final Dimension bodyOffset = new Dimension(22, 29);
    private static final Dimension bodySize = new Dimension(100, 143);

    public MaleZombie(Point location, HealthPointSprite target) {
        super(hp, "assets/male_zombie/", size, bodyOffset, bodySize, target);
        this.location = location;
    }
    
}
