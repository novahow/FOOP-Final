package enemy;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.HealthPointSprite;
import model.SpriteShape;
import model.Direction;
import states.Idle;
import states.Attacking;
import states.Jumping;
import states.Walking;

import static fsm.FiniteStateMachine.Transition.from;
import static enemy.Enemy.Event.*;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class Enemy extends HealthPointSprite {

    public static final int ENEMY_HP = 100;
    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    private HealthPointSprite target;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public Enemy(int damage, Point location) {
        super(ENEMY_HP);
        this.damage = damage;
        this.location = location;
        shape = new SpriteShape(new Dimension(146, 176),
                new Dimension(33, 38), new Dimension(66, 105));
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new EnemyImageRenderer(this);
        State idle = new WaitingPerFrame(4,
                new Idle(imageStatesFromFolder("assets/idle", imageRenderer)));
        State walking = new WaitingPerFrame(2,
                new Walking(this, imageStatesFromFolder("assets/walking", imageRenderer)));
        State attacking = new WaitingPerFrame(3,
                new Attacking(this, fsm, imageStatesFromFolder("assets/attack", imageRenderer)));
        State jumping = new WaitingPerFrame(4, 
                new Jumping(this, fsm, imageStatesFromFolder("assets/jumping", imageRenderer)));

        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(JUMP).to(jumping));
        fsm.addTransition(from(walking).when(JUMP).to(jumping));
    }

    public void setTarget(HealthPointSprite target) {
        this.target = target;
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public void move(Direction direction) {
        if (face != direction) {
            stop(face);
        }
        if (direction == LEFT || direction == Direction.RIGHT) {
            face = direction;
        }
        if (!directions.contains(direction)) {
            this.directions.add(direction);
            fsm.trigger(WALK);
        }
    }

    public void jump() {
        fsm.trigger(JUMP);
    }

    public void stop(Direction direction) {
        directions.remove(direction);
        if (directions.isEmpty()) {
            fsm.trigger(STOP);
        }
    }

    public void update() {
        fsm.update();
    }

    private Rectangle damageArea() {
        return getArea(new Dimension(87, 70),
                new Dimension(55, 88));
    }

    private void decideAction() {
        if (!target.isAlive()) {
            stop(face);
            return;
        }
        Rectangle damageArea = damageArea();
        if (world.getSprites(damageArea).contains(target)) {
            attack();
            return;
        }
        Point targetLocation = target.getLocation();
        if (location.x < targetLocation.x - 50)
            move(Direction.RIGHT);
        else if (location.x > targetLocation.x + 50)
            move(LEFT);
        if (location.y > targetLocation.y + 50)
            jump();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        decideAction();
        fsm.render(g);
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        return shape.bodySize;
    }

    @Override
    public String getName() {
        return "Enemy";
    }
}
