package states;

import java.util.List;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.StateMachine;
import model.Direction;
import model.HealthPointSprite;

public class Jumping extends CyclicSequence {
    private final HealthPointSprite sprite;
    private final StateMachine stateMachine;

    public Jumping(HealthPointSprite sprite, StateMachine stateMachine, List<ImageState> states) {
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
            } else {
                sprite.getWorld().move(sprite, Direction.JUMPDOWN.translate());
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
        return "Jumping";
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }
}
