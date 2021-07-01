package model;

import java.awt.*;
import java.util.HashMap;
import javax.swing.ImageIcon;
import maps.BarDamage;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */


public class HealthPointBar extends Sprite {
    private final int maxHp;
    private Sprite owner;
    private int hp;
    private int damageCycle = -1, damageType = -1;
    public HealthPointBar(int hp) {
        this.maxHp = this.hp = hp;
    }

    public void setOwner(Sprite owner) {
        this.owner = owner;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * owner.getRange().getWidth() / maxHp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, (int) owner.getRange().getWidth(), range.height);
        g.setColor(Color.GREEN);
        g.fillRect(range.x, range.y, width, range.height);
        g.setColor(Color.BLACK);
        g.drawRect(range.x, range.y, (int) owner.getRange().getWidth(), range.height);
        if(damageCycle >= 0){
            damageCycle += 1;
        }
        if(damageCycle >= 20){
            damageCycle = -1;
        }
        else if(damageCycle >= 1){
            g.drawImage(BarDamage.damageMap.get(damageType), range.x, range.y - 50, null);
        }
    }

    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        this.hp = Math.max(hp - damage, 0);
        damageCycle = 0;
        damageType = damage;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(owner.getX(), owner.getY() - 15, (int) owner.getRange().getWidth(), 10);
    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension();
    }

    @Override
    public Dimension getBodySize() {
        return new Dimension();
    }

    public boolean noHP() {
        return hp <= 0;
    }

    @Override
    public String getName() {
        return "HealthPointBar";
    }
}
