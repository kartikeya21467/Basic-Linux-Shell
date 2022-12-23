package com.mygdx.tankstars;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.tankstars.TankStars;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("TankStars");
		config.setWindowedMode(1600,900);
//		config.setResizable(false);
		config.useVsync(true);
		new Lwjgl3Application(new TankStars(), config);
	}
}
