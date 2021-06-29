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
    public static final int ZOMBIE_HP = 500;
    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    public static final String AUDIO_DIE = "Die";

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP, DIE
    }

    public Zombie(int sex, int damage, Point location) {
        super(ZOMBIE_HP);
        this.damage = damage;
        this.location = location;
        shape = new SpriteShape(new Dimension(146, 176),
                new Dimension(33, 38), new Dimension(66, 105));
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new SpriteImageRenderer(this);
        State idle, walking, attacking, dying;
        if(sex == 0) {
            idle = new WaitingPerFrame(4,
                    new Idle(imageStatesFromFolder("assets/male_zombie/idle", imageRenderer)));
            walking = new WaitingPerFrame(2,
                    new Walking(this, imageStatesFromFolder("assets/male_zombie/walk", imageRenderer)));
            attacking = new WaitingPerFrame(3,
                    new Attacking(this, fsm, imageStatesFromFolder("assets/male_zombie/attack", imageRenderer)));
            dying = new WaitingPerFrame(4, 
                    new Dying(this, fsm, imageStatesFromFolder("assets/male_zombie/dead", imageRenderer)));
        }
        else {
            idle = new WaitingPerFrame(4,
                    new Idle(imageStatesFromFolder("assets/female_zombie/idle", imageRenderer)));
            walking = new WaitingPerFrame(2,
                    new Walking(this, imageStatesFromFolder("assets/female_zombie/walk", imageRenderer)));
            attacking = new WaitingPerFrame(3,
                    new Attacking(this, fsm, imageStatesFromFolder("assets/female_zombie/attack", imageRenderer)));
            dying = new WaitingPerFrame(4, 
                    new Dying(this, fsm, imageStatesFromFolder("assets/female_zombie/dead", imageRenderer)));
        }

        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(DIE).to(dying));
        fsm.addTransition(from(walking).when(DIE).to(dying));
        fsm.addTransition(from(attacking).when(DIE).to(dying));
    }

    public void attack() {
        fsm.trigger(ATTACK);
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

    public void die() {
        fsm.trigger(DIE);
    }

    public void update() {
        fsm.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
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
            //world.removeSprite(this);
            AudioPlayer.playSounds(AUDIO_DIE);
            System.out.println("Dying");
            die();
        }
    }
}
