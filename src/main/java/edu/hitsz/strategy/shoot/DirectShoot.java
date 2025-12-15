package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.bullet.BulletCreator;

import java.util.LinkedList;
import java.util.List;
public class DirectShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> shoot(int locationX, int locationY, int direction, int speedX, int speedY, int power, BulletCreator bulletCreator) {
        LinkedList<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction*2;
        int sx = 0;
        int sy = speedY+direction*10;
        int shootNum = 1;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            bullet = bulletCreator.create(x + (i*2 - shootNum + 1)*10, y, sx, sy, power);
            res.add(bullet);
        }
        return res;
    }
}
