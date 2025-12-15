package edu.hitsz.factory.bullet;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
public class EnemyBulletCreator implements BulletCreator{
    @Override
    public BaseBullet create(int locationX, int locationY, int speedX, int speedY, int power) {
        return new EnemyBullet(locationX, locationY, speedX, speedY, power);
    }
}
