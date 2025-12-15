package edu.hitsz.factory.prop;
import edu.hitsz.prop.BaseProp;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPropFactory implements PropFactory{
    private final PropFactory[] factories = {new BloodPropFactory(), new BombPropFactory(), new FirePropFactory(), new FirePlusPropFactory()};

    @Override
    public BaseProp createProp(int x, int y) {
        int idx = ThreadLocalRandom.current().nextInt(factories.length + 4);
        if(idx>=factories.length) return null;
        else return factories[idx].createProp(x,y);
    }
}
