package states;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;
import model.HealthPointSprite;
import model.Sprite;
import model.World;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Shooting extends Sequence {
    private final HealthPointSprite sprite;
    private final StateMachine stateMachine;
    private final Set<Integer> damagingStateNumbers = new HashSet<>(List.of(6));

    public Shooting(HealthPointSprite sprite, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.sprite = sprite;
        this.stateMachine = stateMachine;
    }

    @Override
    public void update() {
        if (sprite.isAlive()) {
            super.update();
            // if (damagingStateNumbers.contains(currentPosition)) {
            //     effectDamage();
            // }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        // Rectangle damageArea = damageArea();
        // g.setColor(Color.BLUE);
        // g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

    // private void effectDamage() {
    //     World world = sprite.getWorld();
    //     Rectangle damageArea = damageArea();
    //     var sprites = world.getSprites(damageArea);
    //     boolean hasClash = false;
    //     for (Sprite s : sprites) {
    //         if (s != sprite) {
    //             s.onDamaged(damageArea, sprite.getDamage());
    //             hasClash = true;
    //         }
    //     }
    //     if (hasClash) {
    //         AudioPlayer.playSounds(AUDIO_SWORD_CLASH_1);
    //     } else {
    //         AudioPlayer.playSounds(AUDIO_SWORD_CLASH_2);
    //     }
    // }

    // private Rectangle damageArea() {
    //     return sprite.getArea(new Dimension(87, 70),
    //             new Dimension(55, 88));
    // }

    @Override
    public String toString() {
        return "Shooting";
    }

    
    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }
}
