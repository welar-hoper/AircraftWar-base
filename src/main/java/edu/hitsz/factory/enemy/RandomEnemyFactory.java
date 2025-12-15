package edu.hitsz.factory.enemy;
import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.application.Game;

public class RandomEnemyFactory implements EnemyFactory{
    private final Game game;
    private int score;
    private int lastBossTier = 0;
    public RandomEnemyFactory(Game game){
        this.game = game;
    }
    private EnemyFactory[] factories;

    @Override
    public AbstractEnemy createEnemy() {
        factories = new EnemyFactory[]{
                    new MobEnemyFactory(),
                    new EliteEnemyFactory(),
                    new ElitePlusEnemyFactory(),
                    new BossEnemyFactory(game.getMode())
        };
        int index;
        score = game.getScore();
        int currentTier = score /( ("HARD".equals(game.getMode()))? 500:600);
        if (!("EASY".equals(game.getMode())) && currentTier > 0 && currentTier > lastBossTier) {//EASY模式下不生成Boss
            lastBossTier = currentTier;
            System.out.println("当前普通敌机、精英敌机、超级精英敌机产生概率为："+(Math.max((0.5 - currentTier * 0.1), 0.2))
            +":"+(Math.min((0.3 + currentTier * 0.05), 0.65))+":"+(Math.min((0.2 + currentTier * 0.05), 0.35)));
            return factories[3].createEnemy(); // Boss
        }
        if("EASY".equals(game.getMode())){
            if(Math.random() < 0.5){
                index = 0;
            } else if (Math.random() < 0.8) {
                index = 1;
            } else {
                index = 2;
            }
        }else{
            if(Math.random() < (Math.max((0.5 - currentTier * 0.1), 0.2))){
                index = 0;
            } else if (Math.random() < (Math.max((0.8 - currentTier * 0.05), 0.65))) {
                index = 1;
            } else {
                index = 2;
            }
        }

        return factories[index].createEnemy();
    }
}
