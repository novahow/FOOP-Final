package model;

import java.awt.*;
import maps.Tiles;

public class Obstacle extends Sprite {
    private final SpriteShape shape;
    private Image img;
    public Obstacle(int x, int y, int size, int world) {
        setLocation(new Point(x, y));
        shape = new SpriteShape(new Dimension(size - (size % 50), 50),
                new Dimension(0, 0), new Dimension(size - (size % 50), 50));
        int tileidx = (int) (Math.random() * 3);
        img = Tiles.tiles.get(world).get(tileidx);
        
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    public int getWidth() {
        return (int)shape.size.getWidth();
    }

    @Override
    public Dimension getBodySize() {
        return shape.bodySize;
    }

    @Override
    public void render(Graphics g) {
        // g.setColor(Color.BLACK);
        // g.fillRect(getX(), getY(), (int)shape.size.getWidth(), 50);
        int tileNum = (int)shape.size.getWidth() / 50;
        for(int i = 0; i < tileNum; i++){
            g.drawImage(img, getX() + i * 50, getY(), null);
        }
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

    @Override
    public String getName() {
        return "Obstacle";
    }
}
