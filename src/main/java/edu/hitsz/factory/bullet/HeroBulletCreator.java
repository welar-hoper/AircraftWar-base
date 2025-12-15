package edu.hitsz.factory.bullet;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

public class HeroBulletCreator implements BulletCreator{
    @Override
    public BaseBullet create(int locationX, int locationY, int speedX, int speedY, int power) {
        return new HeroBullet(locationX, locationY, speedX, speedY, power);
    }
}
