package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.enemy.BossEnemy;
public class BossEnemyFactory implements EnemyFactory{
    private String mode;
    private static int hp = 600;
    public BossEnemyFactory(String mode){
        this.mode = mode;
    }
    @Override
    public AbstractEnemy createEnemy() {
        int x = (int)(Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth()));
        int y = ((ImageManager.BOSS_ENEMY_IMAGE.getHeight())/2);
        int speedX = (Math.random() >= 0.5) ? 2: -2;
        int speedY = 0;
        hp += "HARD".equals(mode)? 200:0;
        return new BossEnemy(x, y, speedX,speedY, hp,EnemyFactory.propFactory);
    }
}
