package model;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT, JUMPUP, JUMPDOWN, BIGLEFT, BIGRIGHT, SLOW_RIGHT, SLOW_LEFT;

    public Dimension translate() {
        switch (this) {
            case UP:
                return new Dimension(0, -6);
            case DOWN:
                return new Dimension(0, 6);
            case LEFT:
                return new Dimension(-6, 0);
            case RIGHT:
                return new Dimension(6, 0);
            case JUMPUP:
                return new Dimension(0, -55);
            case JUMPDOWN:
                return new Dimension(0, 65);
            case BIGLEFT:
                return new Dimension(-12, 0);
            case BIGRIGHT:
                return new Dimension(12, 0);
            case SLOW_LEFT:
                return new Dimension(-2, 0);
            case SLOW_RIGHT: 
                return new Dimension(2, 0);
            default:
                throw new IllegalStateException("Impossible");
        }
    }
}
