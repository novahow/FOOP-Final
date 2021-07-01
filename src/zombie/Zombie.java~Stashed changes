package zombie;

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
import states.Jumping;
import states.Up;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
//import static hero.Ninja.Event.*;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Zombie extends HealthPointSprite {
    protected final HealthPointSprite target;
    protected SpriteShape shape;
    protected final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage = 10;
    public static final String AUDIO_DIE = "Die";
    private boolean isDead = false;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP, DIE, UP
    }

    public Zombie(int hp, String pathPrefix, Dimension size, Dimension bodyOffset, Dimension bodySize, HealthPointSprite target) {
        super(hp);
        this.target = target;
        shape = new SpriteShape(size,bodyOffset, bodySize);
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new SpriteImageRenderer(this);
        State idle, walking, attacking, dying, jumping, up;
        
        idle = new WaitingPerFrame(4,
                new Idle(imageStatesFromFolder(pathPrefix + "idle", imageRenderer)));
        walking = new WaitingPerFrame(2,
                new Walking(this, imageStatesFromFolder(pathPrefix + "walk", imageRenderer)));
        attacking = new WaitingPerFrame(3,
                new Attacking(this, fsm, imageStatesFromFolder(pathPrefix + "attack", imageRenderer)));
        dying = new WaitingPerFrame(4, 
                new Dying(this, fsm, imageStatesFromFolder(pathPrefix + "dead", imageRenderer)));
        jumping = new WaitingPerFrame(4, 
                new Jumping(this, fsm, imageStatesFromFolder(pathPrefix + "walk", imageRenderer)));
        up = new WaitingPerFrame(4, 
                new Up(this, fsm, imageStatesFromFolder(pathPrefix + "walk", imageRenderer)));

        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(attacking).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(DIE).to(dying));
        fsm.addTransition(from(walking).when(DIE).to(dying));
        fsm.addTransition(from(attacking).when(DIE).to(dying));
        fsm.addTransition(from(attacking).when(STOP).to(idle));
        fsm.addTransition(from(walking).when(JUMP).to(jumping));
        fsm.addTransition(from(idle).when(JUMP).to(jumping));
        fsm.addTransition(from(attacking).when(JUMP).to(jumping));
        fsm.addTransition(from(idle).when(UP).to(up));
        fsm.addTransition(from(attacking).when(UP).to(up));
        fsm.addTransition(from(walking).when(UP).to(up));
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public FiniteStateMachine getFsm() {
        return fsm;
    }

    public void move(Direction direction) {
        directions.clear();
        if(direction == Direction.SLOW_LEFT || direction == Direction.LEFT) {
            face = Direction.LEFT;
        }
        if(direction == Direction.SLOW_RIGHT || direction == Direction.RIGHT) {
            face = Direction.RIGHT;
        }
        if (!directions.contains(direction)) {
            this.directions.add(direction);
        }
        fsm.trigger(WALK);
    }

    public void jump(boolean up) {
        if (up)
            fsm.trigger(UP);
        else
            fsm.trigger(JUMP);
    }

    public void stop(Direction direction) {
        directions.remove(direction);
        if (directions.isEmpty()) {
            fsm.trigger(STOP);
        }
    }

    public void die() {
        if (!isDead) {
            world.getBar().setF(10.0f);
            isDead = true;
        }
        fsm.trigger(DIE);
    }

    public void update() {
        fsm.update();
        // System.out.println(fsm.currentState());
    }
    private Rectangle damageArea() {
        return getArea(new Dimension(67, 70),
                new Dimension(55, 88));
    }
    protected void decideAction() {
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
            move(Direction.SLOW_RIGHT);
        else if (location.x > targetLocation.x + 50)
            move(Direction.SLOW_LEFT);
        else if (location.y > targetLocation.y + 50)
            jump(getWorld().obstacleAbove(location));
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        decideAction();
        fsm.render(g);
    }

    @Override
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
    public void onDamaged(Rectangle damageArea, int damage) {
        hpBar.onDamaged(damageArea, damage);
        if (hpBar.noHP()) {
            AudioPlayer.playSounds(AUDIO_DIE);
            die();
        }
    }

    @Override
    public String getName() {
        return "Zombie";
    }
}
