package model;

import java.awt.*;

import hero.Hero;
import static hero.Hero.Event.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class SpriteCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        if (from.getClass().equals(to.getClass()))
            return;
        if (from instanceof Hero && to instanceof Obstacle) {
            int toY = to.getY();
            int fromY = from.getY() + from.getBodyOffset().height + from.getBodySize().height;
            Hero hero = (Hero)from;
            if (fromY <= toY) {
                hero.setLocation(new Point(hero.getX(), hero.getY() + 10));
                hero.getFsm().trigger(STOP);
            }
        }
        
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
