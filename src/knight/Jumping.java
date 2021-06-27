package knight;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

import java.util.List;

public class Jumping extends CyclicSequence {
    private final Knight knight;
    private final StateMachine stateMachine;

    public Jumping(Knight knight, StateMachine stateMachine, List<ImageState> states) {
        super(states);
        this.stateMachine = stateMachine;
        this.knight = knight;
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            super.update();
            if(currentPosition*2 < states.size()) {
                knight.getWorld().move(knight, Direction.JUMPUP.translate());
            }
            else {
                knight.getWorld().move(knight, Direction.JUMPDOWN.translate());
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