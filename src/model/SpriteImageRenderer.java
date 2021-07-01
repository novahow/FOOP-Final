package model;

import fsm.ImageRenderer;

import java.awt.*;
/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class SpriteImageRenderer implements ImageRenderer {
    protected Sprite sprite;

    public SpriteImageRenderer(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = sprite.getFace();
        Rectangle range = sprite.getRange();
        Rectangle body = sprite.getBody();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
        g.setColor(Color.RED);
        // g.drawRect(body.x, body.y, body.width, body.height);
        // g.drawRect(range.x, range.y, range.width, range.height);
    }
}