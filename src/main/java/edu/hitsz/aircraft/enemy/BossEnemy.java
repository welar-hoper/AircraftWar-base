package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.EnemyBulletCreator;
import edu.hitsz.factory.prop.PropFactory;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.strategy.shoot.CirclingShoot;

import java.util.ArrayList;
import java.util.List;

public class BossEnemy extends AbstractEnemy {

    /*攻击方式 */

    /**
     * 子弹一次发射数量
     */
    //private int shootNum = 20;

    /**
     * 子弹伤害
     */
    private int power = 20;
    private int direction = 1;
    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */

    private PropFactory propFactory;


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, PropFactory propFactory) {
        super(locationX, locationY, speedX, speedY, hp);
        this.propFactory = propFactory;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot(){
        bulletCreator = new EnemyBulletCreator();
        shootContext.setShootStrategy(new CirclingShoot());
        return shootContext.executeShoot(this.getLocationX(), this.getLocationY(), direction, this.getspeedX(), this.getSpeedY(), power, bulletCreator);
    }

    @Override
    public int getScore(){
        return 30;
    }

    @Override
    public List<BaseProp> drop() {
        List<BaseProp> props = new ArrayList<>(3);
        int x = getLocationX();
        int y = getLocationY();
        props.add(propFactory.createProp(x-30, y-10));
        props.add(propFactory.createProp(x+30, y-10));
        props.add(propFactory.createProp(x, y));
        return props;
    }

    @Override
    public void bombSupplyActive() {}
}