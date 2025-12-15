package edu.hitsz.factory.prop;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.FireProp;
public class FirePropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        int speedY = 5;
        return new FireProp(x, y, 0, speedY);
    }

}