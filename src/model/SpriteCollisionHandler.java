package model;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class SpriteCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        Rectangle body = from.getBody();
        int offsetLeft = to.getX() - body.x;
        int offsetRight = body.x + body.width - to.getX();
        if (offsetLeft < 0) {
            from.setLocation(new Point(from.getX() - (from.getRange().width + offsetLeft) / 3, from.getY()));
        } else {
            from.setLocation(new Point(from.getX() + offsetRight / 3, from.getY()));
        }
    }
}
