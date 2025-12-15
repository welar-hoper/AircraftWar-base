package edu.hitsz.application;

public class NormalGame extends Game{
    private int lastEnemyMaxNumber = 6;
    private int lastEnemyCycleDuration = 450;
    private int lastHeroCycleDuration = 450;
    @Override
    protected int enemyCycleDuration(){
        int initialEnemyCycleDuration = 450;
        int newEnemyCycleDuration = initialEnemyCycleDuration - (getScore()/600)*20;
        if(newEnemyCycleDuration < 350){
            newEnemyCycleDuration = 350;
        }
        if(lastEnemyCycleDuration != newEnemyCycleDuration){
            lastEnemyCycleDuration = newEnemyCycleDuration;
            System.out.println("难度提升！当前敌机子弹发射间隔：" + newEnemyCycleDuration + "毫秒");
        }
        return newEnemyCycleDuration;
    }
    @Override
    protected int heroCycleDuration(){
        int initialHeroCycleDuration = 450;
        int newHeroCycleDuration = initialHeroCycleDuration - (getScore()/600)*10;
        if(newHeroCycleDuration < 400){
            newHeroCycleDuration = 400;
        }
        if(lastHeroCycleDuration != newHeroCycleDuration){
            lastHeroCycleDuration = newHeroCycleDuration;
            System.out.println("当前英雄机子弹发射间隔：" + newHeroCycleDuration + "毫秒");
        }
        return newHeroCycleDuration;
    }
    @Override
    public String getMode() {
        return "NORMAL";
    }

    @Override
    protected int getEnemyMaxNumber() {
        int initialEnemyMaxNumber = 6;
        int newEnemyMaxNumber = initialEnemyMaxNumber + getScore()/600;
        if(lastEnemyMaxNumber != newEnemyMaxNumber){
            lastEnemyMaxNumber = newEnemyMaxNumber;
            System.out.println("当前敌机最大数量：" + newEnemyMaxNumber);
        }
        return newEnemyMaxNumber;
    }
}
