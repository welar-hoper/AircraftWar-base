package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.enemy.AbstractEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.enemy.*;
import edu.hitsz.music.MusicController;
import edu.hitsz.prop.*;
import edu.hitsz.basic.AbstractFlyingObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemy> enemyAircrafts;
    private final EnemyFactory randomEnemyFactory;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> baseProps;
    public final MusicController musicController;

    /**
     * 屏幕中出现的敌机最大数量
     */
    protected abstract int getEnemyMaxNumber();

    /**
     * 当前得分
     */
    private int score = 0;
    public int getScore() {
        return this.score;
    }
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int enemyCycleDuration(){
        return 600;
    }
    protected int heroCycleDuration(){
        return 600;
    }
    private int cycleTime = 0;
    private int enemyCycleTime = 0;
    private int heroCycleTime = 0;

    /**
     * 游戏结束标志
     */
    private volatile boolean gameOverFlag = false;
    public boolean isGameOver() {
        return gameOverFlag;
    }

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        randomEnemyFactory = new RandomEnemyFactory(this);
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        baseProps = new LinkedList<>();
        musicController = new MusicController();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                BasicThreadFactory.builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            timeCountAndPrint();

            // 新敌机产生和敌机射击
            createNewEnemyAction();

            // 英雄机射击
            shootAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                musicController.stopBossMusic();
                musicController.stopBackgroundMusic();
                musicController.playGameOverMusic();
                heroAircraft.destroyInstance();
                gameOverFlag = true;
                System.out.println("Game Over!");
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void timeCountAndPrint() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            System.out.println(time);
        }
    }

    private boolean enemyTimeCountAndNewCycleJudge() {
        enemyCycleTime += timeInterval;
        if (enemyCycleTime >= enemyCycleDuration()) {
            // 跨越到新的周期
            enemyCycleTime %= enemyCycleDuration();
            return true;
        } else {
            return false;
        }
    }

    private boolean heroTimeCountAndNewCycleJudge() {
        heroCycleTime += timeInterval;
        if (heroCycleTime >= heroCycleDuration()) {
            // 跨越到新的周期
            heroCycleTime %= heroCycleDuration();
            return true;
        } else {
            return false;
        }
    }

    private void createNewEnemyAction(){
        if (enemyTimeCountAndNewCycleJudge()) {
            if (enemyAircrafts.size() < getEnemyMaxNumber()) {
                AbstractEnemy newEnemy = randomEnemyFactory.createEnemy();
                enemyAircrafts.add(newEnemy);
                if(newEnemy.isBoss()){
                    System.out.println("Boss 出现！血量：" + newEnemy.getHp());
                    musicController.stopBackgroundMusic();
                    musicController.playBossMusic();
                }
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    private void shootAction() {
        // 敌机射击
        /*if(enemyTimeCountAndNewCycleJudge()){
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }*/
        // 英雄射击
        if(heroTimeCountAndNewCycleJudge()) heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : baseProps) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        //敌机子弹攻击英雄
        for(BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到英雄机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    musicController.playBulletHitMusic();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        if(enemyAircraft.isBoss()){
                            musicController.stopBossMusic();
                            musicController.playBackgroundMusic();
                        }
                        List<BaseProp> drops = enemyAircraft.drop();
                        for (BaseProp prop : drops) {
                            if(prop != null) baseProps.add(prop);
                        }
                        score += enemyAircraft.getScore();
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for(BaseProp prop : baseProps) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                // 英雄机获得道具，道具生效
                musicController.playGetSupplyMusic();
                if(prop.isBomb()){
                    ((BombProp) prop).addEnemy(enemyAircrafts);
                    ((BombProp) prop).addEnemyBullet(enemyBullets);
                    musicController.playBombExplosionMusic();
                }
                prop.apply(heroAircraft);
                if(prop.isBomb()){
                    ((BombProp) prop).removeEnemy(enemyAircrafts);
                    ((BombProp) prop).removeEnemyBullet(enemyBullets);
                    for(AbstractEnemy enemy: enemyAircrafts){
                        if(enemy.notValid()) score += enemy.getScore();
                    }
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        baseProps.removeIf(AbstractFlyingObject::notValid);
    }

    public String getMode() {
        return null;
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String mode = getMode();

        // 绘制背景,图片滚动
        if("EASY".equals(mode)){
            g.drawImage(ImageManager.EASY_MODE_BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.EASY_MODE_BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        }else if ("NORMAL".equals(mode)){
            g.drawImage(ImageManager.NORMAL_MODE_BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.NORMAL_MODE_BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        }else{
            g.drawImage(ImageManager.HARD_MODE_BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.HARD_MODE_BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        }

        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，再绘制道具，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, baseProps);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
