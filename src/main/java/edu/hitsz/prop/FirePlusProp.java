package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.shoot.CirclingShoot;
import edu.hitsz.strategy.shoot.DirectShoot;

public class FirePlusProp extends BaseProp {
    public FirePlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public boolean isBomb(){
        return false;
    }

    @Override
    public void apply(HeroAircraft hero) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.isAlive() && "prop".equals(t.getName())) {
                t.interrupt();
            }
        }
        hero.shootContext.setShootStrategy(new DirectShoot());
        Runnable r = () -> {
            try {
                hero.shootContext.setShootStrategy(new CirclingShoot());
                Thread.sleep(5000);
                hero.shootContext.setShootStrategy(new DirectShoot());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//e.printStackTrace();
            }
        };
        System.out.println("FirePlusSupply active!");
        new Thread(r,"prop").start();
        this.vanish();
    }
}
