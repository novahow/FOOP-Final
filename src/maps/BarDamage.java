package maps;

import java.awt.*;
import java.util.HashMap;
import model.GetSizedImage;

public abstract class BarDamage {
    private static int[] damamgeArr = {10, 20, 50, 100};
    public static HashMap<Integer, Image> damageMap = new HashMap<Integer, Image>();
    static{
        for(int i = 0; i < damamgeArr.length; i++){
            damageMap.put(damamgeArr[i], 
            new GetSizedImage(String.format("assets/background/-%d.png"
            , damamgeArr[i]), 100, 50).getImage());
        }
    }
}
