package ru.tnkv.basetycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import ru.tnkv.basetycoon.logic.MemoryManager;

public class Tycoon extends Game {
    private static final Logger log = new Logger("Tycoon", Logger.INFO);
    public static final int SCR_WIDTH = 720;
    public static final int SCR_HEIGHT = 1280;

    public OrthographicCamera camera;
    GameScreen screenGame;
    SpriteBatch batch;

    private boolean gameActive = true;

    public Tycoon() {
        super();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);

        screenGame = new GameScreen(this);
        setScreen(screenGame);

        long offlinePeriod = System.currentTimeMillis() - MemoryManager.getClosed();
        if (offlinePeriod >= 900_000) {
            MemoryManager.addCookies(
                    (offlinePeriod / 30_000) * MemoryManager.getAutoClicker()
            );
        }

        CompletableFuture.runAsync(() -> {
            try {
                while (this.gameActive) {
                    TimeUnit.SECONDS.sleep(10);
                    MemoryManager.addCookies(MemoryManager.getAutoClicker());
                }
            } catch (InterruptedException e) {
                log.error("An error occurred", e);
            }
        });
    }

    @Override
    public void dispose() {
        gameActive = false;
        batch.dispose();
        screenGame.dispose();
        MemoryManager.setClosed(System.currentTimeMillis());
    }
}
