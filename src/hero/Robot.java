package hero;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import model.Direction;
import states.Shooting;
import model.Sprite;
import static hero.Robot.Event.*;
import java.util.List;
import java.util.Iterator;

public class Robot extends Hero {
    public static final int HP = 1000;
    public static final int DAMAGE = 100;
    private final int bulletDamage = 20;
    private ArrayList<bullet> bullets = new ArrayList<bullet>();
    private static final Dimension size = new Dimension(146, 153);
    private static final Dimension bodyOffset = new Dimension(33, 0);
    private static final Dimension bodySize = new Dimension(68, 141);
    private int period = 0;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP, SHOOT
    }

    public Robot(Point location) {
        super(HP, "assets/robot/", size, bodyOffset, bodySize);
        this.damage = DAMAGE;
        this.location = location;
    }

    @Override
    public void shoot() {
        super.shoot();
        if (period < 75)
            return;
        period = 0;
        bullet bu = new bullet(getX() + 90, getY() + 50, face, bulletDamage);
        bullets.add(bu);
    }

    private boolean inside(int left, int middle, int offset) {
        int right = left + offset;
        return left <= middle && middle <= right;
    }

    @Override
    public void update() {
        super.update();
        period += 1;
        Iterator<bullet> it = bullets.iterator();
        while (it.hasNext()) {
            bullet b = it.next();
            if (b.getHit()) {
                if(b.getStage() != 9)
                    continue;
                else {
                    it.remove();
                }
            }
            else {
                int dx = (b.getDir() == Direction.RIGHT) ? 2 : -2;
                b.setX(b.getX() + dx);
                if (b.getX() <= 0 || b.getX() > 1200) 
                    it.remove();
                else {
                    List<Sprite> sprites = world.getSprites();
                    for (Sprite s : sprites) {
                        if (s != this) {
                            int width = s.getBodySize().width;
                            int height = s.getBodySize().height;
                            if (inside(s.getX(), b.getX(), width) && inside(s.getY(), b.getY(), height)) {
                                b.setHit();
                                s.onDamaged(null, b.getDamage());
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void clearB() {
        bullets.clear();
    }
    @Override
    public void render(Graphics g) {
        super.render(g);
        getFsm().render(g);
        for (bullet b : bullets) {
            b.render(g);
        }
    }

    public class bullet{
        private int x;
        private int y;
        private int stage;
        private Direction dir;
        private int damage;
        private boolean hit = false;
        private int cnt = 0;

        public bullet(int x, int y, Direction dir, int damage) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.damage = damage;
            this.stage = 0;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Direction getDir() {
            return dir;
        }

        public int getDamage() {
            return damage;
        }

        public void setHit() {
            hit = true;
            stage = 4;
        }

        public int getStage() {
            return stage;
        }

        public boolean getHit() {
            return hit;
        }

        public void setStage() {
            if (!hit)
                stage = (stage + 1) % 5;
            else {
                if (cnt > 3) {
                    stage = (stage + 1) % 5;
                    stage += 5;
                    cnt = 0;
                }
                else {
                    cnt += 1;
                }
            }
        }

        public void render(Graphics g) {
            ImageIcon i = new ImageIcon("assets/robot/Objects/" + Integer.toString(stage) + ".png");
            setStage();
            Image b = i.getImage();
            if (getDir() == Direction.RIGHT)
                g.drawImage(b, x, y, 50, 50, null);
            else
                g.drawImage(b, x + 50, y, -50, 50, null);
        }
    }
}
