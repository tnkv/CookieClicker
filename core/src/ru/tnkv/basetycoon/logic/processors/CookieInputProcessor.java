package ru.tnkv.basetycoon.logic.processors;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import ru.tnkv.basetycoon.GameScreen;
import ru.tnkv.basetycoon.logic.MemoryManager;

public class CookieInputProcessor implements InputProcessor {
    private final Vector3 tempVector = new Vector3();

    long actionPrice;

    @Override
    public boolean touchDown(int x, int y, int i2, int i3) {
        tempVector.set(x, y, 0);
        GameScreen.camera.unproject(tempVector);
        if(GameScreen.cookieSprite.getBoundingRectangle().contains(tempVector.x, tempVector.y)) {
            MemoryManager.addCookies(MemoryManager.getBooster());
            return false;
        }

        if(GameScreen.upgradeAutoSprite.getBoundingRectangle().contains(tempVector.x, tempVector.y)) {
            actionPrice = MemoryManager.getAutoClicker() * 10;
            if (actionPrice > MemoryManager.getCookies()) {
                return false;
            }

            MemoryManager.removeCookies(actionPrice);
            MemoryManager.addAutoClicker(1);
            return false;
        }

        if(GameScreen.upgradeClickSprite.getBoundingRectangle().contains(tempVector.x, tempVector.y)) {
            actionPrice = MemoryManager.getBooster() * 10;
            if (actionPrice > MemoryManager.getCookies()) {
                return false;
            }

            MemoryManager.removeCookies(actionPrice);
            MemoryManager.addBooster(1);
            return false;
        }

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }
}
