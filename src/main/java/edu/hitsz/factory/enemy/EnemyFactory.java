package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.factory.prop.PropFactory;
import edu.hitsz.factory.prop.RandomPropFactory;

public interface EnemyFactory {
    PropFactory propFactory = new RandomPropFactory();
    AbstractEnemy createEnemy();
}
