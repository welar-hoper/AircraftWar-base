package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
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
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
    @Override
    public int getScore(){
        return 10;
    }
    @Override
    public List<BaseProp> drop() {
        List<BaseProp> props = new ArrayList<>(3);
        props.add(null);
        props.add(null);
        props.add(null);
        return props;
    }

    @Override
    public void bombSupplyActive() {
        this.hp = 0;
        this.vanish();
    }
}
