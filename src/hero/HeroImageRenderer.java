package hero;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class HeroImageRenderer implements ImageRenderer {
    protected Hero hero;

    public HeroImageRenderer(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = hero.getFace();
        Rectangle range = hero.getRange();
        Rectangle body = hero.getBody();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
        // g.setColor(Color.RED);
        // g.drawRect(body.x, body.y, body.width, body.height);
        // g.drawRect(range.x, range.y, range.width, range.height);
    }
}
