package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends BaseProp {

    private int blood = 30;
    public int getBlood() {
        return blood;
    }
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.blood = 30;
    }
    @Override
    public boolean isBomb() {
        return false;
    }
    @Override
    public void apply(HeroAircraft hero) {
        hero.increaseHp(getBlood());
        System.out.println("BloodSupply active!");
        this.vanish();
    }
}
