package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.BulletCreator;

import java.util.LinkedList;
import java.util.List;

public class ScatteringShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> shoot(int locationX, int locationY, int direction, int speedX, int speedY, int power, BulletCreator bulletCreator) {
        LinkedList<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction * 2;
        int baseSpeedY = speedY;
        int shootNum = 3;
        // 总散射角度（度）
        int spreadAngleDeg = 40;
        double start = -spreadAngleDeg / 2.0;
        double step = spreadAngleDeg * 1.0 / (shootNum - 1);

        for (int i = 0; i < shootNum; i++) {
            double angleDeg = start + i * step; // 相对竖直方向的偏转角
            double rad = Math.toRadians(angleDeg);
            // 子弹基础速度模长
            int bulletSpeed = 7;
            int sx = (int) Math.round(bulletSpeed * Math.sin(rad));
            int sy = baseSpeedY + direction * (int) Math.round(bulletSpeed * Math.cos(rad));
            res.add(bulletCreator.create(x, y, sx, sy, power));
        }
        return res;
    }
}
