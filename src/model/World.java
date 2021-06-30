package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import javax.swing.ImageIcon;

import maps.Tiles;
import media.AudioPlayer;
import zombie.*;
import java.util.Iterator;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.Random;
/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private Sprite player;
    private final List<WorldButton> buttons = new ArrayList<>();
    private final CollisionHandler collisionHandler;
    private List<Obstacle> ob = new ArrayList<Obstacle>();
    private final ProcessBar bar = new ProcessBar(100);
    private List<Background> backs = new ArrayList<Background>();
    Random r1 = new Random(10);
    Random p = new Random(5);
    private boolean end = false;
    private boolean isPause = false;
    private boolean isStop = false;
    private boolean muted = false;
    private int last_x = 0;
    private int topObstacle;
    private int bottomObstacle;
    private HealthPointSprite hero;
    private int floorY = 685;
    
    private Random random_zombie_appear_time = new Random();
    private Random random_zombie_sex = new Random();
    private int elapsed_time = 0, interval;


    public World(CollisionHandler collisionHandler, Sprite... sprites) {
        this.collisionHandler = collisionHandler;
        addSprites(sprites);
        for(int i = 0; i < 3; i++){
            Integer i1 = i;
            Tiles.addTiles("./assets/obstacles/", i1.toString() + ".png");
        }
        for (int i = 0; i < 10; i ++) {
            int y = r1.nextInt(150);
            int x = r1.nextInt(120);
            Background b = new Background(1200 - 10 * x, y, 40, "assets/background/cloud.png");
            backs.add(b);
        }
        for (int i = 0; i < 12; i ++) {
            Background b = new Background(1200 - 120 * i, 565, 60, "assets/background/grass_bg.png");
            backs.add(b);
        }
    }

    public void setPlayer(Sprite s) {
        player = s;
    }

    public Sprite getPlayer() {
        return player;
    }

    public void update() {
        elapsed_time += 1;
        if(elapsed_time > interval*67) {
            // around 67 ticks = 1 second
            interval = random_zombie_appear_time.nextInt(15);
            elapsed_time = 0;
            if(random_zombie_sex.nextInt()%2 == 0) {
                addSprite(new MaleZombie(new Point(1100, 500)));
            }
            else {
                addSprite(new FemaleZombie(new Point(1100, 500)));
            }
        }
        for (Sprite s : sprites) {
            if (s.getY() + s.getBodyOffset().height + s.getBodySize().height < floorY)
                gravity(s);
                s.update();
        }
    }


    public void setHero(HealthPointSprite hero) {
        this.hero = hero;
    }

    public void gravity(Sprite from) {
        for (Sprite to : sprites)
            if (to != from && from.getBody().intersects(to.getBody()))
                if (to.getBody().getY() > from.getBody().getY())
                    return;
        from.gravity();
        // int dy = 1 + incre;
        // if (from.getY() + dy > 535) {
        //     dy = 535 - from.getY();
        // }
        // Point originalLocation = new Point(from.getLocation());
        // from.getLocation().translate(0, dy);
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

    public void addButton(WorldButton... buttons){
        stream(buttons).forEach(this::addButton);
    }

    public void addButton(WorldButton button){
        buttons.add(button);
    }

    public void stop() {
        isStop = true;
    }

    public boolean isRunning() {
        if(isStop)
            return false;
        end = bar.isEnd();
        return (sprites.size() > 0 && !end);
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause() {
        isPause = !isPause;
    }

    private boolean collisionBlock(Sprite from, Sprite to, Dimension offset) {
        return offset.width * (to.getBody().getX() - from.getBody().getX()) > 0 ||
                (offset.height > 0 && to.getBody().getY() > from.getBody().getY());
    }
    
    public void move(Sprite from, Dimension offset) {
        // for (Sprite to : sprites)
        //     if (to != from && from.getBody().intersects(to.getBody()))
        //         if (collisionBlock(from, to, offset))
        //             return;
        // float f = (float)(offset.width) / (float)(20);
        // bar.setF(f);
        // System.out.println(end);
        topObstacle = bottomObstacle = 0;
        for (Obstacle o : ob) {
            if (from.getX() >= 600 && offset.width > 0) {
                // move(o, new Dimension(-offset.width, 0));
                o.setX(o.getX() - offset.width);
            }
            if (o.getX() < 0) {
                removeSprite(o);
            }
            Rectangle body = o.getBody();
            if (body.y == 400) {
                topObstacle = Math.max(topObstacle, body.x + body.width);
            } else {
                bottomObstacle = Math.max(bottomObstacle, body.x + body.width);
            }
        }
        Iterator<Background> it = backs.iterator();
        while (it.hasNext()) {
            Background b = it.next();
            if (from.getX() >= 600 && offset.width > 0) {
                // move(o, new Dimension(-offset.width, 0));
                b.setX(b.getX() - offset.width);
            }
            if (b.getX() + b.getSize() < 0) {
                it.remove();
            }
        }
        int x = getSprites().get(0).getX();

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
        if (from.getY() + from.getBodyOffset().height + from.getBodySize().height + dy > floorY) {
            dy = floorY - (from.getY() + from.getBodyOffset().height + from.getBodySize().height);
        }

        if (p.nextInt(3) < 1 && offset.width > 0 && dx == 0) {
            int y = r1.nextInt(500);
            if (y > 450 && y < 480) {
                Background b = new Background(1200, 5*(500 - y), 40, "assets/background/cloud.png");
                backs.add(b);
            }
            if (y > 350) {
                y = 400;
            }
            else {
                y = 200;
            }
            last_x += offset.width;
            if (last_x == 60) {
                Background b = new Background(1200, 565, 60, "assets/background/grass_bg.png");
                backs.add(b); 
                last_x = 0;
            }
            
            if (y == 400 && topObstacle <= 1000 || y == 200 && bottomObstacle <= 1000) {
                Obstacle o = new Obstacle(1200, y, r1.nextInt(150) + 150);
                ob.add(o);
                addSprite(o);
            }
        }
        // System.out.printf("%d %d\n", dx, dy);
        Point originalLocation = new Point(from.getLocation());
        from.getLocation().translate(dx, dy);
        // System.out.printf("%d %d\n", from.getX(), from.getY());
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

    public List<WorldButton> getButtons(){
        return buttons;
    }
    public static final Color lightblue = new Color(51, 153, 255);
    public static final Color darkgreen = new Color(0, 102, 0);
    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gradient=new GradientPaint(0, 0, Color.BLUE,0,665,lightblue);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, 1200, 665);
        gradient=new GradientPaint(0, 0, Color.GREEN,0,965,darkgreen);
        g2.setPaint(gradient);
        g2.fillRect(0, 665, 1200, 300);

        for (Background b : backs) {
            b.render(g);
        }

        gradient=new GradientPaint(70,70,Color.orange,150,150,Color.yellow);
        g2.setPaint(gradient);
        g2.fillOval(70, 70, 100, 100);bar.render(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Press esc to pause.", 10, 30);
        for (Sprite sprite : sprites) {
            if(sprite.isDead()) {
                removeSprite(sprite);
            }
            else {
                sprite.render(g);
            }
        }
        // System.out.printf("l = %d\n", buttons.size());
        for(WorldButton btn: buttons){
            btn.render(g);
        }
    }

    public void clickButton(int x, int y){
        if(isRunning()){
            muted = (buttons.get(0).clickbutton(x, y) == 0);
        }
        AudioPlayer.setEnable(muted);
    }

    public boolean isMuted(){
        return muted;
    }
}
