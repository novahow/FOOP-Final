package model;

import java.awt.*;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */


public class HealthPointBar extends Sprite {
    private final int maxHp;
    private Sprite owner;
    private int hp;
    private int damageCycle = -1, damageType = -1;
    private int[] damamgeArr = {10, 20, 50, 100};
    private HashMap<Integer, Image> damageMap = new HashMap<Integer, Image>();
    
    public HealthPointBar(int hp) {
        this.maxHp = this.hp = hp;
        for(int i = 0; i < damamgeArr.length; i++){
            damageMap.put(damamgeArr[i], 
            new GetSizedImage(String.format("assets/background/-%d.png"
            , damamgeArr[i]), 100, 50).getImage());
        }
        
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
            // System.out.printf("drawing damage\n");
            g.drawImage(damageMap.get(damageType), range.x, range.y - 50, null);
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
