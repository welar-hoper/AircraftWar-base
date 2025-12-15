package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.BulletCreator;

import java.util.List;

public interface ShootStrategy {
    List<BaseBullet> shoot(int locationX, int locationY, int direction, int speedX, int speedY, int power, BulletCreator bulletCreator);
}
