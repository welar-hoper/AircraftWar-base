package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.aircraft.enemy.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy createEnemy() {
        int x = (int)(Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int y = (int)(Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedY = 10;
        int hp = 60;
        return new EliteEnemy(x, y, 0, speedY, hp,EnemyFactory.propFactory);
    }
}
