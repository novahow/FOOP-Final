package states;

import java.util.List;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.StateMachine;
import model.Direction;
import model.HealthPointSprite;

public class Dying extends CyclicSequence {
    private final HealthPointSprite sprite;
    private final StateMachine stateMachine;

    public Dying (HealthPointSprite sprite, StateMachine stateMachine, List<ImageState> states) {
        super(states);
        this.stateMachine = stateMachine;
        this.sprite = sprite;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public String toString() {
        return "Dying";
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        sprite.finishDying();
        stateMachine.reset();
    }
}
