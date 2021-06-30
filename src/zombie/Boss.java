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
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static zombie.Zombie.Event.*;
//import static hero.Ninja.Event.*;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;
import java.util.Random;

public class Boss extends Zombie {
    private static final Dimension size = new Dimension(400, 550);
    private static final Dimension bodyOffset = new Dimension(30, 30);
    private static final Dimension bodySize = new Dimension(410, 520);
    private Random r1 = new Random(20);

    public Boss(Point location, HealthPointSprite target) {
        super("assets/boss/", size, bodyOffset, bodySize, target);
        this.location = location;
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
        if (location.x <= 800 && r1.nextInt(100) == 7) {
            stop(Direction.SLOW_LEFT);
            attack();
            return;
        }
        Point targetLocation = target.getLocation();
        // if (location.x < targetLocation.x - 50 && location.x > 600)
        //     move(Direction.SLOW_RIGHT);
        if (location.x > targetLocation.x + 50 && location.x > 800){
            System.out.println(location.x);
            move(Direction.SLOW_LEFT);
        }
        // if (location.y > targetLocation.y + 50)
        //     jump();
    }
    
}
