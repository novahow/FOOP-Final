package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import javax.swing.ImageIcon;

import hero.Hero;
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
    private HealthPointSprite hero = null;
    private boolean end = false;
    private boolean isPause = false;
    private boolean isStop = false;
    private boolean muted = false;
    private int last_x = 0;
    private int[] obstacleY = {145, 420};
    private int floorY = 677;
    private boolean win = false; 
    private int worldNum;
    private int bushidx = 0;
    private Random randomZombieAppearTime = new Random();
    private Random randomZombieSex = new Random();
    private int elapsedTime = 0, interval;
    private Image floor;
    private Sprite boss;
    private int setforboss = 0;

    public World(CollisionHandler collisionHandler, Sprite... sprites) {
        this.collisionHandler = collisionHandler;
        addSprites(sprites);
        Tiles.setUp();
        for (int i = 0; i < 10; i ++) {
            int y = r1.nextInt(150);
            int x = r1.nextInt(120);
            Background b = new Background(1200 - 10 * x, y, 150 - y / 5, 80, "assets/background/cloud.png");
            backs.add(b);
        }

        
    }

    public void setPlayer(Sprite s) {
        player = s;
    }

    public Sprite getPlayer() {
        return player;
    }

    public HealthPointSprite getHero() {
        return hero;
    }

    public void update() {
        win = bar.isEnd();
        // bar.setF(-100.0f);
        if (win) {
            if (setforboss < 10) {
                try {
                    hero.clearB();
                } catch (Exception e) {}
                sprites.clear();
                ob.clear();
                backs.clear();
                Obstacle top = new Obstacle(100, obstacleY[0], 250, worldNum);
                Obstacle bottom = new Obstacle(100, obstacleY[1], 250, worldNum);
                addSprite(top);
                addSprite(bottom);
                ob.add(top);
                ob.add(bottom);
                // Boss = new Boss(new Point(1200, 100), hero);
                setforboss += 1;
            }
            if (setforboss == 10) {
                boss = new Boss(new Point(1200, 100), hero);
                addSprite(boss);
                setforboss += 1;
            }
            if (setforboss == 11) {
                if (!boss.isAlive())
                    end = true;
            }
            // if (sprites.isEmpty()) {
            //     addSprite(Boss);
            // }
            try {
                for (Sprite s : sprites) {
                    if (s.getY() + s.getBodyOffset().height + s.getBodySize().height < floorY)
                        gravity(s);
                    s.update();
                } 
            }
            catch (Exception e) {}
            gravity(hero);
            hero.update();

            return;
        }
        elapsedTime += 1;
        if (elapsedTime > interval * 67) {
            // around 67 ticks = 1 second
            interval = randomZombieAppearTime.nextInt(13) + 2;
            elapsedTime = 0;
            if (randomZombieSex.nextInt() % 2 == 0) {
                addSprite(new MaleZombie(new Point(1100, floorY - 176), hero));
            } else {
                addSprite(new FemaleZombie(new Point(1100, floorY - 176), hero));
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
        int feet = from.getY() + from.getBodyOffset().height + from.getBodySize().height;
        int x = from.getX() + (from.getBodySize().width) / 2;
        for (Sprite to : sprites)
            if (to != from && to.getX() <= x && x <= to.getX() + to.getBodySize().width)
                if (to.getBody().getY() > feet)
                    return;
        from.gravity();
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

    public boolean isEnd(){
        return end;
    }

    public boolean isRunning() {
        if (isStop)
            return false;
        // end = bar.isEnd();
        if (hero != null && !hero.isAlive())
            end = true;
        return (!end);
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause() {
        isPause = !isPause;
    }

    public ProcessBar getBar() {
        return bar;
    }

    private boolean collisionBlock(Sprite from, Sprite to, Dimension offset) {
        return offset.width * (to.getBody().getX() - from.getBody().getX()) > 0 ||
                (offset.height > 0 && to.getBody().getY() > from.getBody().getY());
    }

    private int getHeight(Sprite sprite) {
        return sprite.getBodyOffset().height + sprite.getBodySize().height;
    }

    private boolean inside(int left, int test, int offset) {
        return left <= test && test <= left + offset;
    }
    
    public void move(Sprite from, Dimension offset) {
        int[] leftmostObstacle = new int[2];
        if (!win) {
            for (Obstacle o : ob) {
                if (from.getX() >= 600 && offset.width > 0) {
                    // move(o, new Dimension(-offset.width, 0));
                    o.setX(o.getX() - offset.width);
                }
                if (o.getX() + o.getBodySize().width < 0) {
                    removeSprite(o);
                }
                Rectangle body = o.getBody();
                int level = getObstacleLevel(body.y);
                leftmostObstacle[level] = Math.max(leftmostObstacle[level], body.x + body.width);
            }
        }

        for (Obstacle o : ob) {
            if (inside(o.getX(), from.getX(), o.getBodySize().width))
                if (inside(from.getY() + getHeight(from), o.getY(), 10))
                    if (collisionBlock(from, o, offset) && offset.height != 0)
                        if (!(from instanceof Obstacle)) {
                            return;
                        }
        }
         

        int dx = offset.width, dy = offset.height;
        if (from.getX() + dx < 0) {
            dx = -from.getX();
        }
        
        if (from.getName() != "Zombie" && from.getX() + dx > 600) {
            dx = 600 - from.getX();
        }
        if (from.getY() + dy < 0) {
            dy = -from.getY();
        }
        if (from.getY() + getHeight(from) + dy > floorY) {
            dy = floorY - (from.getY() + getHeight(from));
        }
        if (win) {
            Point originalLocation = new Point(from.getLocation());
            from.getLocation().translate(dx, dy);
            return;
        }

        if (p.nextInt(3) < 1 && offset.width > 0 && dx == 0 && from instanceof Hero) {
            int y = r1.nextInt(500);
            if (y > 450 && y < 480) {
                Background b = new Background(1200, 5*(500 - y), 150 - y / 9, 80, "assets/background/cloud.png");
                backs.add(b);
            }
            if ((10 < y && y < 13) || (19 < y && y < 24)) {
                Background b = new Background(1200, 565, 120, 150, String.format("assets/background/grave%d.png", worldNum));
                backs.add(b);
            }
            
            last_x += offset.width;
            if (last_x == 30) {
                Background b = new Background(1200 - 30, 565, 120, 120, 
                    String.format("assets/background/%d/bush%d.png", worldNum, bushidx % 3));
                backs.add(b);
                last_x = 0;
                bushidx = (bushidx + 1) % 3;
            }
            int level = getObstacleLevel(y);
            y = obstacleY[level];
            if (leftmostObstacle[level] <= 1000) {
                Obstacle o = new Obstacle(1200, y, r1.nextInt(150) + 500, worldNum);
                ob.add(o);
                addSprite(o);
            }
        }
        
        Iterator<Background> it = backs.iterator();
        while (it.hasNext()) {
            Background b = it.next();
            if (from.getX() >= 600 && offset.width > 0) {
                b.setX(b.getX() - offset.width);
            }
            if (b.getX() + b.getSize() < 0) {
                it.remove();
            }
        }
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

    public List<WorldButton> getButtons(){
        return buttons;
    }
    public static final Color lightblue = new Color(51, 153, 255);
    public static final Color darkgreen = new Color(0, 102, 0);
    public static final Color lightgreen = new Color(0, 204, 0);
    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gradient=new GradientPaint(0, 0, Color.BLUE,0,679,lightblue);
        if (win) {
            floor = new GetSizedImage("assets/background/fireground.png", 200, 200).getImage();
            if (setforboss < 10) {
                ImageIcon object = new ImageIcon("assets/background/warning.gif");
                Image bg = object.getImage();
                g.drawImage(bg ,0, 0, 1200, 800, null);
            } else {
                ImageIcon object = new ImageIcon("assets/background/bosslevel.gif");
                Image bg = object.getImage();
                g.drawImage(bg ,0, 0, 1200, 800, null);
            }
            // ImageIcon object = new ImageIcon("assets/background/firework.gif");
            // Image bg = object.getImage();
            // g.drawImage(bg ,0, 0, 1200, 800, null);
            // if (hero.getY() + hero.getBodyOffset().height + hero.getBodySize().height >= floorY)
                // hero.jump();

            for(int i = 0; i < 10; i++){
                g2.drawImage(floor, 120 * i, 580, null);
            }
            hero.render(g);
            if (end) {
                System.out.println("win");
            }
            if (end) 
                hero.jump(obstacleAbove(hero.getLocation()));

            for (Sprite sprite : sprites) {
                if(sprite.isDead()) {
                    removeSprite(sprite);
                }
                else {
                    sprite.render(g);
                }
            }
            // int y = r1.nextInt(100);
            // if (y == 69)
            //     end = true;
            return;
        }
        g2.setPaint(gradient);
        g2.fillRect(0, 0, 1200, 679);

        for (Background b : backs) {
            b.render(g);
        }
        gradient=new GradientPaint(0, 679, lightgreen,0,965,darkgreen);
        g2.setPaint(gradient);

        for(int i = 0; i < 10; i++){
            g2.drawImage(floor, 120 * i, 679, null);
        }

        gradient =new GradientPaint(70,70,Color.orange,150,150,Color.yellow);
        g2.setPaint(gradient);
        g2.fillOval(70, 70, 100, 100);bar.render(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        for (Sprite sprite : sprites) {
            if(sprite.isDead()) {
                removeSprite(sprite);
            }
            else {
                sprite.render(g);
            }
        }
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

    private int getObstacleLevel(int y) {
        if (y < (obstacleY[0] + obstacleY[1]) / 2)
            return 0;
        return 1;
    }

    public void setWorldNum(int num){
        worldNum = num;
        floor = new GetSizedImage("assets/background/floor" + 
            Integer.toString(num) + ".png", 120, 120).getImage();

        for (int i = 0; i < 12; i ++) {
            Background b = new Background(1200 - 120 * i, 565, 120, 120, 
                String.format("assets/background/%d/bush%d.png", num, i % 3));
            backs.add(b);
        }
    }

    public boolean obstacleAbove(Point location) {
        for (Obstacle o : ob) {
            int left = o.getX();
            int right = o.getX() + o.getBodySize().width;
            int x = location.x;
            int y = location.y;
            if (left <= x && x <= right && y <= o.getY() + 50)
                return true;
        }
        return false;
    }
}
