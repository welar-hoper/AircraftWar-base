package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.BulletCreator;

import java.util.LinkedList;
import java.util.List;

public class CirclingShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> shoot(int locationX, int locationY, int direction, int speedX, int speedY, int power, BulletCreator bulletCreator) {
        LinkedList<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY;
        int shootNum = 20;
        for (int i = 0; i < shootNum; i++) {
            double angle = 2 * Math.PI * i / shootNum; // 弧度制
            // 子弹基础速度模长
            int bulletSpeed = 7;
            int sx = (int) Math.round(bulletSpeed * Math.cos(angle));
            int sy = (int) Math.round(bulletSpeed * Math.sin(angle));
            res.add(bulletCreator.create(x, y, sx, sy, power));
        }
        return res;
    }
}
