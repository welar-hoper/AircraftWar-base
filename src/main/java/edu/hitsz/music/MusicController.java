package edu.hitsz.music;

public class MusicController {
    private boolean enabled = false;
    private MusicThread backgroundMusic;
    private MusicThread bossMusic;
    private MusicThread bulletHitMusic;
    private MusicThread gameOverMusic;
    private MusicThread getSupplyMusic;
    private MusicThread bombExplosionMusic;
    public void musicOn(boolean enabled) {
        this.enabled = enabled;
    }
    public void playBackgroundMusic() {
        if(!enabled) {
            return;
        }
        backgroundMusic = new MusicThread("src/main/resources/videos/bgm.wav", MusicThread.PlayMode.LOOP);
        backgroundMusic.start();
    }

    public void stopBackgroundMusic() {
        if(!enabled) {
            return;
        }
        if (backgroundMusic != null) {
            backgroundMusic.stopPlaying();
        }
    }

    public void playBossMusic() {
        if(!enabled) {
            return;
        }
        bossMusic = new MusicThread("src/main/resources/videos/bgm_boss.wav", MusicThread.PlayMode.LOOP);
        bossMusic.start();
    }

    public void stopBossMusic() {
        if(!enabled) {
            return;
        }
        if (bossMusic != null) {
            bossMusic.stopPlaying();
        }
    }

    public void playBulletHitMusic() {
        if(!enabled) {
            return;
        }
        bulletHitMusic = new MusicThread("src/main/resources/videos/bullet_hit.wav", MusicThread.PlayMode.ONCE);
        bulletHitMusic.start();
    }


    public void playGameOverMusic() {
        if(!enabled) {
            return;
        }
        gameOverMusic = new MusicThread("src/main/resources/videos/game_over.wav", MusicThread.PlayMode.ONCE);
        gameOverMusic.start();
    }

    public void playGetSupplyMusic() {
        if(!enabled) {
            return;
        }
        getSupplyMusic = new MusicThread("src/main/resources/videos/get_supply.wav", MusicThread.PlayMode.ONCE);
        getSupplyMusic.start();
    }

    public void playBombExplosionMusic() {
        if(!enabled) {
            return;
        }
        bombExplosionMusic = new MusicThread("src/main/resources/videos/bomb_explosion.wav", MusicThread.PlayMode.ONCE);
        //bombExplosionMusic.sleep(100);
        bombExplosionMusic.start();
    }

}
