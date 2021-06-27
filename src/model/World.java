package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import javax.swing.ImageIcon;
import java.util.Random;
/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;
    private List<Obstacle> ob = new ArrayList<Obstacle>();
    Random r1 = new Random(10);
    Random p = new Random(5);
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
        List<Integer> A = new ArrayList<Integer>();
        int cnt = 0;
        int x = getSprites().get(0).getX();
        if (x == 600 && p.nextGaussian() > 0.5 && offset.width > 0) {
            int y = r1.nextInt(500);
            if (y > 350) {
                y = 400;
            }
            else if (y > 150) {
                y = 200;
            }
            else {
                y = 0;
            }
            Obstacle o = new Obstacle(1200, y, 50);
            ob.add(o);
        }
        for (Obstacle o : ob) {
            if (from.getX() >= 600 && offset.width > 0) {
                o.setX(o.getX() - offset.width);
            }
            if (o.getX() < 0) {
                A.add(cnt);
            }
            cnt += 1;
        }
        List<Obstacle> nb = new ArrayList<Obstacle>();
        for (int i = 0; i < ob.size(); i ++) {
            if (!A.contains(i))
                nb.add(ob.get(i));
        }
        ob = nb;
        int dx = offset.width, dy = offset.height;
        if (from.getX() + dx < 0) {
            dx = -from.getX();
        }
        if (from.getX()+ dx > 600) {
            dx = 600 - from.getX();
        }
        if (from.getY() + dy < 0) {
            dy = -from.getY();
        }
        if (from.getY() + dy > 535) {
            dy = 535 - from.getY();
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
        ImageIcon i = new ImageIcon("assets/level1.gif");
        Image bg = i.getImage();
        int x = sprites.get(0).getX();
        g.drawImage(bg, 0, 0, 1200, 800, null);
        for (Obstacle o : ob) {
            o.render(g);
        }
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }
}
