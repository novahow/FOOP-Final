package model;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class SpriteCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        // if (to instanceof Obstacle) {
            
        // }
        Sprite toMove = (to instanceof Obstacle)? from: to;
        Rectangle body = from.getBody();
        int offsetLeft = to.getX() - body.x;
        int offsetRight = body.x + body.width - to.getX();
        if (offsetLeft < 0) {
            toMove.setLocation(new Point(toMove.getX() - (toMove.getRange().width + offsetLeft) / 3, toMove.getY()));
        } else {
            toMove.setLocation(new Point(toMove.getX() + offsetRight / 3, toMove.getY()));
        }
    }
}
