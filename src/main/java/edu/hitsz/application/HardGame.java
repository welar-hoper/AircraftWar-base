package edu.hitsz.application;

public class HardGame extends Game{
    private int lastEnemyMaxNumber = 7;
    private int lastEnemyCycleDuration = 300;
    private int lastHeroCycleDuration = 300;
    @Override
    protected int enemyCycleDuration(){
        int initialEnemyCycleDuration = 300;
        int newEnemyCycleDuration = initialEnemyCycleDuration - (getScore()/500)*20;
        if(newEnemyCycleDuration < 200){
            newEnemyCycleDuration = 200;
        }
        if(lastEnemyCycleDuration != newEnemyCycleDuration){
            lastEnemyCycleDuration = newEnemyCycleDuration;
            System.out.println("难度提升！当前敌机子弹发射间隔：" + newEnemyCycleDuration + "毫秒");
        }
        return newEnemyCycleDuration;
    }
    @Override
    protected int heroCycleDuration(){
        int initialHeroCycleDuration = 300;
        int newHeroCycleDuration = initialHeroCycleDuration - (getScore()/500)*10;
        if(newHeroCycleDuration < 250){
            newHeroCycleDuration = 250;
        }
        if(lastHeroCycleDuration != newHeroCycleDuration){
            lastHeroCycleDuration = newHeroCycleDuration;
            System.out.println("当前英雄机子弹发射间隔：" + newHeroCycleDuration + "毫秒");
        }
        return newHeroCycleDuration;
    }
    @Override
    public String getMode() {
        return "HARD";
    }
    @Override
    protected int getEnemyMaxNumber() {
        int initialEnemyMaxNumber = 7;
        int newEnemyMaxNumber = initialEnemyMaxNumber + (getScore()/500)*2;
        if(lastEnemyMaxNumber != newEnemyMaxNumber){
            lastEnemyMaxNumber = newEnemyMaxNumber;
            System.out.println("当前敌机最大数量：" + newEnemyMaxNumber);
        }
        return newEnemyMaxNumber;
    }
}
