package edu.hitsz.factory.prop;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        int speedY = 5;
        return new BloodProp(x, y, 0, speedY);
    }

}
