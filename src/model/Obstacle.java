package model;

import java.awt.*;

public class Obstacle extends Sprite {
    private final SpriteShape shape;

    public Obstacle(int x, int y, int size) {
        setLocation(new Point(x, y));
        shape = new SpriteShape(new Dimension(size, 50),
                new Dimension(0, 0), new Dimension(size, 50));
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        return shape.bodySize;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), (int)shape.size.getWidth(), 50);
    }

    @Override
    public void gravity() {
        return ;
    }

    public void update() {}

    public void setX(int x) {
        Point location = getLocation();
        int y = (int)location.getY();
        location.move(x, y);
        setLocation(location);
    }

    public void onDamaged(Rectangle damageArea, int damage) {}
}
