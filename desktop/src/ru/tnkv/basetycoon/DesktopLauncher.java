package ru.tnkv.basetycoon;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setWindowedMode(Tycoon.SCR_WIDTH / 2, Tycoon.SCR_HEIGHT / 2);
        config.setTitle("BaseTycoon");
        config.setResizable(false);
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 3);


        new Lwjgl3Application(new Tycoon(), config);

    }
}
