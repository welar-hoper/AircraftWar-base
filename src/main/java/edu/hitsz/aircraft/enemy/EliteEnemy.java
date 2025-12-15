package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.EnemyBulletCreator;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.factory.prop.*;

import java.util.ArrayList;
import java.util.List;

public class EliteEnemy extends AbstractEnemy {

    /*攻击方式 */

    /**
     * 子弹一次发射数量
     */
    //private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 20;

    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = 1;

    private PropFactory propFactory;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
                      PropFactory propFactory) {
        super(locationX, locationY, speedX, speedY, hp);
        this.propFactory = propFactory;// 默认掉落概率
    }

    @Override
    public boolean isBoss() {
        return false;
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
    /*
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        bulletCreator = new EnemyBulletCreator();
        return shootContext.executeShoot(this.getLocationX(), this.getLocationY(), direction, this.getspeedX(), this.getSpeedY(), power, bulletCreator);
    }

    @Override
    public int getScore(){
        return 15;
    }
    
    @Override
    public List<BaseProp> drop() {
        List<BaseProp> props = new ArrayList<>(3);
        int x = getLocationX();
        int y = getLocationY();
        props.add(null);
        props.add(null);
        props.add(propFactory.createProp(x, y));
        return props;
    }

    @Override
    public void bombSupplyActive() {
        this.hp = 0;
        this.vanish();
    }
}
