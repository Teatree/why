package com.playground;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "playGround";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 320;
		
		new LwjglApplication(new PeMain(), cfg);
	}
}
