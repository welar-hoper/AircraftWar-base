package edu.hitsz.aircraft.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.BaseProp;

import java.util.List;

public abstract class AbstractEnemy extends AbstractAircraft {
    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public abstract boolean isBoss();

    public abstract int getScore();

    public abstract List<BaseProp> drop();

    public abstract void bombSupplyActive();
}
