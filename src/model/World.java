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
    private final ProcessBar bar = new ProcessBar(100);
    Random r1 = new Random(10);
    Random p = new Random(5);
    private boolean end = false;
    private boolean isjump = false;
    private int gt = 0;
    public World(CollisionHandler collisionHandler, Sprite... sprites) {
        this.collisionHandler = collisionHandler;
        addSprites(sprites);
    }

    public void update() {
        if (!isjump) {
            int incre = gt / 20;
            gravity(sprites.get(0),incre);
            gravity(sprites.get(1), incre);
            gt += 1;
        }
        else
            gt = 0;
        for (Sprite sprite : sprites) {
            sprite.update();
        }
    }

    public void setjump(boolean tmp) {
        isjump = tmp;
    }

    public void gravity(Sprite from, int incre) {
        for (Sprite to : sprites)
            if (to != from && from.getBody().intersects(to.getBody()))
                if (to.getBody().getY() > from.getBody().getY())
                    return;
        int dy = 1 + incre;
        if (from.getY() + dy > 535) {
            dy = 535 - from.getY();
        }
        Point originalLocation = new Point(from.getLocation());
            from.getLocation().translate(0, dy);
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
        end = bar.isEnd();
        return (sprites.size() > 0 && !end);
    }

    private boolean collisionBlock(Sprite from, Sprite to, Dimension offset) {
        return offset.width * (to.getBody().getX() - from.getBody().getX()) > 0 ||
                (offset.height > 0 && to.getBody().getY() > from.getBody().getY());
    }
    
    public void move(Sprite from, Dimension offset) {
        for (Sprite to : sprites)
            if (to != from && from.getBody().intersects(to.getBody()))
                if (collisionBlock(from, to, offset))
                    return;
        // float f = (float)(offset.width) / (float)(20);
        // bar.setF(f);
        // System.out.println(end);
        int x = getSprites().get(0).getX();
        if (x == 600 && p.nextGaussian() < 0.001 && offset.width > 0) {
            int y = r1.nextInt(500);
            if (y > 350) {
                y = 400;
            }
            else {
                y = 200;
            }
            Obstacle o = new Obstacle(1200, y, r1.nextInt(50) + 50);
            ob.add(o);
            addSprite(o);
        }
        for (Obstacle o : ob) {
            if (from.getX() >= 600 && offset.width > 0) {
                // move(o, new Dimension(-offset.width, 0));
                o.setX(o.getX() - offset.width);
            }
            if (o.getX() < 0) {
                removeSprite(o);
            }
        }
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
        // System.out.printf("%d %d\n", dx, dy);
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
        bar.render(g);
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }
}
