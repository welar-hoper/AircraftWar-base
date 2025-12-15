package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;

public interface PropFactory {
    BaseProp createProp(int x, int y);
}
