package zombie;

import java.awt.Dimension;
import java.awt.Point;

import model.HealthPointSprite;
import media.AudioPlayer;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteImageRenderer;
import model.SpriteShape;
import states.Attacking;
import states.Idle;
import states.Jumping;
import states.Walking;
import states.Dying;

import java.awt.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
//import static hero.Ninja.Event.*;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;
import java.util.Random;
import java.util.Iterator;
import javax.swing.ImageIcon;

public class Boss extends Zombie {
    private static final int hp = 3000;
    private static Dimension size = new Dimension(400, 550);
    private static final Dimension bodyOffset = new Dimension(30, 30);
    private static final Dimension bodySize = new Dimension(410, 470);
    private final int bullet_damage = 50;
    private ArrayList<bullet> bullets = new ArrayList<bullet>();
    private Random r1 = new Random(20);

    public Boss(Point location, HealthPointSprite target) {
        super(1500, "assets/boss/", size, bodyOffset, bodySize, target);
        this.location = location;
    }

    @Override
    public void attack() {
        super.attack();
        bullet bu = new bullet(getX() - 100, getY() + 150 * r1.nextInt(4), face, bullet_damage);
        bullets.add(bu);
    }

    @Override
    public void update() {
        super.update();
        if (fsm.toString() == "Attacking") {
            shape.setSize(550, 750);
            location.y = -23;
            location.x = 500;
        }
        else if (fsm.toString() == "Dying") {
            shape.setSize(550, 750);
            location.y = 7;
            location.x = 500;
        }
        else {
            shape.setSize(400, 550);
            location.y = 177;
            if (location.x <= 600)
                location.x = 600;
        }
        Iterator<bullet> it = bullets.iterator();
        while (it.hasNext()) {
            bullet b = it.next();
            int dx = -2;
            b.setX(b.getX() + dx);
            if (b.getX() + 150 <= 0) 
                it.remove();
            else {
                HealthPointSprite s = world.getHero();
                if (s.getX() < b.getX() && s.getX() + 90 > b.getX() 
                        && s.getY() < b.getY() && s.getY() + 120 > b.getY()) {
                        it.remove();
                        s.onDamaged(null, b.getDamage());
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        for (bullet b : bullets) {
            b.render(g);
        }
    }

    @Override
    protected void decideAction() {
        if (!target.isAlive()) {
            stop(face);
            return;
        }
        // Rectangle damageArea = damageArea();
        // if (world.getSprites(damageArea).contains(target)) {
        //     attack();
        //     return;
        // }
        if (location.x <= 600) {
            stop(Direction.SLOW_LEFT);
        }
        if (location.x <= 600 && r1.nextInt(100) == 7) {
            attack();
            return;
        }
        Point targetLocation = target.getLocation();
        if (location.x > targetLocation.x + 50 && location.x > 800){
            move(Direction.SLOW_LEFT);
        }
    }

    public class bullet{
        private int x;
        private int y;
        private int stage;
        private Direction dir;
        private int damage;
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

        public int getStage() {
            return stage;
        }

        public void setStage() {
            stage = (stage + 1) % 6;
        }

        public void render(Graphics g) {
            ImageIcon i = new ImageIcon("assets/boss/fireball/" + Integer.toString(stage) + ".png");
            setStage();
            Image b = i.getImage();
            g.drawImage(b, x, y, 300, 120, null);
        }
    }
    
}
