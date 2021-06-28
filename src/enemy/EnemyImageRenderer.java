package enemy;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class EnemyImageRenderer implements ImageRenderer {
    protected Enemy enemy;

    public EnemyImageRenderer(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = enemy.getFace();
        Rectangle range = enemy.getRange();
        Rectangle body = enemy.getBody();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
         g.setColor(Color.RED);
         g.drawRect(body.x, body.y, body.width, body.height);
    }
}
