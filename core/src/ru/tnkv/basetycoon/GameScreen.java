package ru.tnkv.basetycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.tnkv.basetycoon.logic.MemoryManager;
import ru.tnkv.basetycoon.logic.processors.CookieInputProcessor;

public class GameScreen implements Screen {
    private final Tycoon tycoon;
    private final Viewport view;
    public static Camera camera;

    private BitmapFont countFont;
    private BitmapFont buttonFont;

    private final FileHandle fontFile = Gdx.files.internal("opensans.ttf");
    private final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(fontFile);
    private final FreeTypeFontGenerator.FreeTypeFontParameter countFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private final FreeTypeFontGenerator.FreeTypeFontParameter buttonFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private final GlyphLayout countFontLayout = new GlyphLayout();

    private final Texture cookieTexture = new Texture("cookie.png");
    private final Texture upgradeTexture = new Texture("upgrade.png");

    public static Sprite cookieSprite;
    public static Sprite upgradeAutoSprite;
    public static Sprite upgradeClickSprite;


    GameScreen(Tycoon tycoon) {
        this.tycoon = tycoon;

        this.cookieTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        this.upgradeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cookieSprite = new Sprite(cookieTexture);
        upgradeAutoSprite = new Sprite(upgradeTexture);
        upgradeClickSprite = new Sprite(upgradeTexture);

        this.view = new ScreenViewport(tycoon.camera);

        countFont = new BitmapFont();
        countFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonFont = new BitmapFont();
        buttonFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        camera = this.tycoon.camera;
        Gdx.input.setInputProcessor(new CookieInputProcessor());
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#FFFACD"), true);

        float cookieDiameter = Math.min(tycoon.camera.viewportWidth / 2, tycoon.camera.viewportHeight / 2);
        float buttonWidth = (float) (camera.viewportWidth / 1.5);


        countFontLayout.setText(countFont, String.valueOf(MemoryManager.getCookies()));

        tycoon.camera.update();
        tycoon.batch.setProjectionMatrix(tycoon.camera.combined);
        tycoon.batch.begin();

        cookieSprite.setPosition(camera.position.x - (cookieDiameter / 2), camera.position.y - (cookieDiameter / 2));
        cookieSprite.setSize(cookieDiameter, cookieDiameter);
        cookieSprite.draw(tycoon.batch);

        upgradeClickSprite.setBounds(
                camera.position.x - (buttonWidth / 2),
                camera.position.y - (cookieDiameter) - (buttonWidth / 8),
                buttonWidth,
                buttonWidth / 4
        );
        upgradeClickSprite.draw(tycoon.batch);

        buttonFont.draw(
                tycoon.batch,
                "Улучшение клика\nДоход: " + MemoryManager.getBooster() + " \nУлучшить за: " + MemoryManager.getBooster() * 10,
                camera.position.x - (buttonWidth / 2) + (buttonWidth / 3),
                camera.position.y - (cookieDiameter) + buttonWidth / 16
        );

        upgradeAutoSprite.setBounds(
                camera.position.x - (buttonWidth / 2),
                camera.position.y - (cookieDiameter * 3 / 2) - (buttonWidth / 8),
                buttonWidth,
                buttonWidth / 4
        );
        upgradeAutoSprite.draw(tycoon.batch);

        buttonFont.draw(
                tycoon.batch,
                "Авто-Кликер\nДоход: " + MemoryManager.getAutoClicker() + " \nУлучшить за: " + Math.max(MemoryManager.getAutoClicker() * 10, 10),
                camera.position.x - (buttonWidth / 2) + (buttonWidth / 3),
                camera.position.y - (cookieDiameter * 3 / 2) + buttonWidth / 16
        );


        countFont.draw(tycoon.batch, String.valueOf(MemoryManager.getCookies()), camera.position.x - (countFontLayout.width / 2), (float) (camera.position.y + cookieDiameter / 1.15));
        tycoon.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        countFontParameter.size = (int) Math.min(camera.viewportWidth, camera.viewportHeight) / 5;
        countFontParameter.color = Color.BLACK;
        countFont.dispose();

        buttonFontParameter.size = (int) (camera.viewportWidth / 1.5) / 25;
        buttonFontParameter.color = Color.BLACK;
        buttonFontParameter.incremental = true;
        buttonFont.dispose();

        countFont = fontGenerator.generateFont(countFontParameter);
        buttonFont = fontGenerator.generateFont(buttonFontParameter);
        view.update(width, height);
        tycoon.camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        cookieTexture.dispose();
        upgradeTexture.dispose();
        countFont.dispose();
        buttonFont.dispose();
    }
}
