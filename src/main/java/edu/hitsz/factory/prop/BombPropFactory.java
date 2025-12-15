package edu.hitsz.factory.prop;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;
public class BombPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        int speedY = 5;
        return new BombProp(x, y, 0, speedY);
    }

}
