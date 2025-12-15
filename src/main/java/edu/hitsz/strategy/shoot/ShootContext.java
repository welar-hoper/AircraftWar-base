package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.BulletCreator;

import java.util.List;

public class ShootContext {
    private ShootStrategy shootStrategy;

    public ShootContext(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public List<BaseBullet> executeShoot(int locationX, int locationY, int direction, int speedX, int speedY, int power, BulletCreator bulletCreator) {
        return shootStrategy.shoot(locationX, locationY, direction, speedX, speedY, power, bulletCreator);
    }
}
