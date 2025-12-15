package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft hero;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Starting HeroAircraft tests...");
    }

    @BeforeEach
    void setUp(){
        hero = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown(){
        hero = null;
    }

    @Test
    void getHeroAircraft() {
        System.out.println("Testing getHeroAircraft()");
        HeroAircraft heroTest = HeroAircraft.getHeroAircraft();
        assertNotNull(heroTest);
        HeroAircraft anotherHero = HeroAircraft.getHeroAircraft();
        assertSame(heroTest, anotherHero); // 确保创建英雄机只能存在一个实例
        System.out.println("Testing getHeroAircraft() passed.");
    }

    @Test
    void increaseHp() {
        System.out.println("Testing increaseHp()");
        HeroAircraft hero = HeroAircraft.getHeroAircraft();
        hero.decreaseHp(80);// 先减少生命值以测试增加功能
        int initialHp = hero.getHp();
        hero.increaseHp(20);
        assertEquals(initialHp + 20, hero.getHp());
        hero.increaseHp(1000); // 测试增加超过最大生命值
        assertEquals(hero.maxHp, hero.getHp());
        System.out.println("Testing increaseHp() passed.");
    }

    @Test
    void shoot() {
        System.out.println("Testing shoot()");
        List<BaseBullet> bullets = hero.shoot();
        assertNotNull(bullets);
        assertFalse(bullets.isEmpty());
        for (BaseBullet bullet : bullets) {
            assertTrue(bullet.getSpeedY() < 0); // 子弹速度小于0即为向上 // 子弹应向上射击
            assertEquals(hero.getLocationX(), bullet.getLocationX());
            assertTrue(bullet.getLocationY() < hero.getLocationY()); // 子弹位置应在飞机上方
        }
        System.out.println("Testing shoot() passed.");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("HeroAircraft tests completed.");
    }
}