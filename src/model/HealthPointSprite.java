package model;

import media.AudioPlayer;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fsm.FiniteStateMachine;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class HealthPointSprite extends Sprite {
    public static final String AUDIO_DIE = "Die";
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    protected HealthPointBar hpBar;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, JUMP
    }

    public HealthPointSprite(int hp) {
        this.hpBar = new HealthPointBar(hp);
        hpBar.setOwner(this);
    }

    public void jump(boolean back) {
    
    }
    public void clearB() {
        
    }
    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        hpBar.onDamaged(damageArea, damage);
        if (hpBar.noHP()) {
            world.removeSprite(this);
            AudioPlayer.playSounds(AUDIO_DIE);
        }
    }

    @Override
    public void render(Graphics g) {
        hpBar.render(g);
    }

    public abstract int getDamage();

    public Set<Direction> getDirections() {
        return directions;
    }
}
