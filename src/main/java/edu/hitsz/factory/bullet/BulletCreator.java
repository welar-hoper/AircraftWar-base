package edu.hitsz.factory.bullet;

import edu.hitsz.bullet.BaseBullet;

public interface BulletCreator {
    BaseBullet create(int locationX, int locationY, int speedX, int speedY, int power);
}
