package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class BombProp extends BaseProp {
    private final LinkedList<AbstractEnemy> enemyList = new LinkedList<>();
    private final LinkedList<BaseBullet> enemyBulletList = new LinkedList<>();
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public void addEnemy(List<AbstractEnemy> enemy) {
        enemyList.addAll(enemy);
    }
    public void addEnemyBullet(List<BaseBullet> enemyBullet) {
        enemyBulletList.addAll(enemyBullet);
    }
    public void removeEnemyBullet(List<BaseBullet> enemyBullet){
        enemyBulletList.removeAll(enemyBullet);
    }
    public void removeEnemy(List<AbstractEnemy> enemy){
        enemyList.removeAll(enemy);
    }
    @Override
    public boolean isBomb(){
        return true;
    }

    @Override
    public void apply(HeroAircraft hero) {
        for(AbstractEnemy enemy: enemyList){
            enemy.bombSupplyActive();
        }
        for(BaseBullet enemyBullet: enemyBulletList){
            enemyBullet.bombSupplyActive();
        }
        this.vanish();
        System.out.println("BombSupply active!");
    }
}
