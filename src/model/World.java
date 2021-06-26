package model;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;

    public World(CollisionHandler collisionHandler, Sprite... sprites) {
        this.collisionHandler = collisionHandler;
        addSprites(sprites);
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
    }

    public void addSprites(Sprite... sprites) {
        stream(sprites).forEach(this::addSprite);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        sprite.setWorld(this);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        sprite.setWorld(null);
    }

    public boolean isRunning() {
        return (sprites.size() == 2);
    }

    public void move(Sprite from, Dimension offset) {
        int dx = offset.width, dy = offset.height;
        if (from.getX() + dx < 0) {
            dx = -from.getX();
        }
        if (from.getX() + from.getBody().width + dx > 1200) {
            dx = 1200 - from.getX() - from.getBody().width;
        }
        if (from.getY() + dy < 0) {
            dy = -from.getY();
        }
        if (from.getY() + from.getBody().height + dy > 800) {
            dy = 800 - from.getY() - from.getBody().height;
        }
        System.out.printf("%d %d\n", dx, dy);
        Point originalLocation = new Point(from.getLocation());
        from.getLocation().translate(dx, dy);

        Rectangle body = from.getBody();
        // collision detection
        for (Sprite to : sprites) {
            if (to != from && body.intersects(to.getBody())) {
                collisionHandler.handle(originalLocation, from, to);
            }
        }
    }

    public Collection<Sprite> getSprites(Rectangle area) {
        return sprites.stream()
                .filter(s -> area.intersects(s.getBody()))
                .collect(toSet());
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }
}
