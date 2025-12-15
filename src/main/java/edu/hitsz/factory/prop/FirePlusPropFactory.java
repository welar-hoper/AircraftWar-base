package edu.hitsz.factory.prop;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.FirePlusProp;
public class FirePlusPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        int speedY = 5;
        return new FirePlusProp(x, y, 0, speedY);
    }

}
