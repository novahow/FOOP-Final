package hero;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteShape;
import states.Idle;
import states.Attacking;
import states.Jumping;
import states.Walking;
import states.Shooting;
import states.Up;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static hero.Hero.Event.*;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Hero extends HealthPointSprite {
    private SpriteShape shape;
    private FiniteStateMachine fsm;
    private Set<Direction> directions = new CopyOnWriteArraySet<>();
    protected int damage;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP, SHOOT, DIE, UP
    }

    public Hero(int hp, String pathPrefix, Dimension size, Dimension bodyOffset, Dimension bodySize) {
        super(hp);
        shape = new SpriteShape(size, bodyOffset, bodySize);
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new HeroImageRenderer(this);
        State idle = new WaitingPerFrame(4,
                new Idle(imageStatesFromFolder(pathPrefix + "idle", imageRenderer)));
        State walking = new WaitingPerFrame(2,
                new Walking(this, imageStatesFromFolder(pathPrefix + "walking", imageRenderer)));
        State attacking = new WaitingPerFrame(3,
                new Attacking(this, fsm, imageStatesFromFolder(pathPrefix + "attack", imageRenderer)));
        State jumping = new WaitingPerFrame(4, 
                new Jumping(this, fsm, imageStatesFromFolder(pathPrefix + "jumping", imageRenderer)));
        State up = new WaitingPerFrame(4, 
                new Up(this, fsm, imageStatesFromFolder(pathPrefix + "jumping", imageRenderer)));        
        try {
            State shooting = new WaitingPerFrame(5, 
                new Shooting(this, fsm, imageStatesFromFolder(pathPrefix + "shooting", imageRenderer)));
            fsm.addTransition(from(idle).when(SHOOT).to(shooting));
            fsm.addTransition(from(walking).when(SHOOT).to(shooting));
        } catch (Exception e) {}
        
        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(JUMP).to(jumping));
        fsm.addTransition(from(walking).when(JUMP).to(jumping));
        fsm.addTransition(from(jumping).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(UP).to(up));
        fsm.addTransition(from(walking).when(UP).to(up));
    }

    public FiniteStateMachine getFsm() {
        return fsm;
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    public void shoot() {
        fsm.trigger(SHOOT);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public void move(Direction direction) {
        if (direction == LEFT || direction == Direction.RIGHT) {
            face = direction;
        }
        if (!directions.contains(direction)) {
            this.directions.add(direction);
        }
        fsm.trigger(WALK);
    }
    @Override
    public void jump(boolean up) {
        if (up)
            fsm.trigger(UP);
        else
            fsm.trigger(JUMP);
        setGT(0);
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

    @Override
    public void render(Graphics g) {
        super.render(g);
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
        return "Hero";
    }
}
