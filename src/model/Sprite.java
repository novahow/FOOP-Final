package model;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class Sprite {
    protected World world;
    protected Point location = new Point();
    protected Direction face = Direction.RIGHT;
    protected int gt = 0;
    private Boolean died = false;

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract void onDamaged(Rectangle damageArea, int damage);

    public World getWorld() {
        return world;
    }

    public void setGT(int i) {
        this.gt = i; 
    }

    public int getGT() {
        return gt;
    }

    public void gravity() {
        int incre = gt / 20;
        setGT(getGT() + 1);
        int dy = 1 + incre;
        Dimension d = new Dimension(0, dy);
        world.move(this, d);
        // if (getY() + dy > 535) {
        //     dy = 535 - getY();
        //     setGT(0);
        // }
        // Point originalLocation = new Point(getLocation());
        // getLocation().translate(0, dy);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getX() {
        return getRange().x;
    }

    public int getY() {
        return getRange().y;
    }

    public abstract Rectangle getRange();

    public abstract Dimension getBodyOffset();

    public abstract Dimension getBodySize();

    public Direction getFace() {
        return face;
    }

    public void setFace(Direction face) {
        this.face = face;
    }

    public Rectangle getBody() {
        return getArea(getBodyOffset(), getBodySize());
    }

    public Rectangle getArea(Dimension offset, Dimension size) {
        if (face == Direction.LEFT) {
            Rectangle range = getRange();
            return new Rectangle(new Point(location.x + range.width - offset.width - size.width,
                    offset.height + location.y), size);
        }
        return new Rectangle(new Point(offset.width + location.x,
                offset.height + location.y), size);
    }

    public boolean isAlive() {
        return world != null;
    }

    public void finishDying() {
        died = true;
    }

    public Boolean isDead() {
        return died;
    }

    abstract public String getName();
    
}
