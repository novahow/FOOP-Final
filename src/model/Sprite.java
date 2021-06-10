package model;

import java.awt.*;

public abstract class Sprite {
    protected Point location = new Point();
    protected Direction face = Direction.RIGHT;

    public abstract void update();

    public abstract void render(Graphics g);

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
        switch (face) {
            case Direction.LEFT :
                Rectangle range = getRange();
                return new Rectangle();
            case Direction.RIGHT :
                Rectangle range = getRange();
                return new Rectangle();
        }

    }
}