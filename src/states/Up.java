package states;

import java.awt.Dimension;
import java.util.List;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.StateMachine;
import model.Direction;
import model.HealthPointSprite;

public class Up extends CyclicSequence {
    private final HealthPointSprite sprite;
    private final StateMachine stateMachine;

    public Up(HealthPointSprite sprite, StateMachine stateMachine, List<ImageState> states) {
        super(states);
        this.stateMachine = stateMachine;
        this.sprite = sprite;
    }

    @Override
    public void update() {
        if (sprite.isAlive()) {
            super.update();
            if(currentPosition * 2 <= states.size() && currentPosition != 0) {
                sprite.getWorld().move(sprite, Direction.JUMPUP.translate());
            }
            for (Direction direction : sprite.getDirections()) {
                if(direction == Direction.LEFT) {
                    sprite.getWorld().move(sprite, Direction.BIGLEFT.translate());
                }
                else if(direction == Direction.RIGHT) {
                    sprite.getWorld().move(sprite, Direction.BIGRIGHT.translate());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Up";
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }
}
