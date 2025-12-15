package edu.hitsz.application;

public class EasyGame extends Game{
    @Override
    public String getMode() {
        return "EASY";
    }

    @Override
    protected int getEnemyMaxNumber() {
        return 5;
    }
}
