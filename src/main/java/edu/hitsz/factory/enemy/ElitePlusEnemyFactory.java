package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.aircraft.enemy.ElitePlusEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class ElitePlusEnemyFactory implements EnemyFactory{
    @Override
    public AbstractEnemy createEnemy() {
        int x = (int)(Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_ENEMY_IMAGE.getWidth()));
        int y = (int)(Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = (Math.random() >= 0.5) ? 4: -4;
        int speedY = 10;
        int hp = 60;
        return new ElitePlusEnemy(x, y, speedX,speedY, hp,EnemyFactory.propFactory);
    }
}
