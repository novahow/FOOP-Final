package states;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;
import model.HealthPointSprite;

import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Walking extends CyclicSequence {
    public static final String AUDIO_STEP1 = "step1";
    public static final String AUDIO_STEP2 = "step2";
    private final HealthPointSprite sprite;

    public Walking(HealthPointSprite sprite, List<ImageState> states) {
        super(states);
        this.sprite = sprite;
    }

    @Override
    public void update() {
        if (sprite.isAlive()) {
            super.update();
            for (Direction direction : sprite.getDirections()) {
                sprite.getWorld().move(sprite, direction.translate());
            }
        }
    }

    @Override
    public String toString() {
        return "Walking";
    }
}
