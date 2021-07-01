package model;

import java.awt.*;

import hero.Hero;
import zombie.Zombie;

import static hero.Hero.Event.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class SpriteCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        if (from instanceof Zombie && to instanceof Zombie)
            return;
        if (to instanceof Obstacle) {
            int toY = to.getY();
            int fromY = from.getY() + from.getBodyOffset().height + from.getBodySize().height;
            from.setLocation(new Point(from.getX(), from.getY() - 10));
            if (from instanceof Hero) {
                Hero hero = (Hero)from;
                if (fromY <= toY) {
                    hero.getFsm().trigger(STOP);
                }
            } else if (from instanceof Zombie) {
                Zombie zombie = (Zombie)from;
                if (fromY <= toY)
                    zombie.getFsm().trigger(STOP);
            }
            return;
        }
        
        // Sprite toMove = (to instanceof Obstacle)? from: to;
        // Rectangle body = from.getBody();
        // int offsetLeft = to.getX() - body.x;
        // int offsetRight = body.x + body.width - to.getX();
        // if (offsetLeft < 0) {
        //     toMove.setLocation(new Point(toMove.getX() - (toMove.getRange().width + offsetLeft) / 3, toMove.getY()));
        // } else {
        //     toMove.setLocation(new Point(toMove.getX() + offsetRight / 3, toMove.getY()));
        // }
    }
}
