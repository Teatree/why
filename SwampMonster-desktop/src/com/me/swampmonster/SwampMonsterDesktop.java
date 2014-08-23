package com.me.swampmonster;

import sun.applet.Main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.swampmonster.utils.ScreenContainer;

public class SwampMonsterDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SwampMonster";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
		
//		MainSwamp mainSwamp = new MainSwamp();
//        ScreenContainer.game = mainSwamp;
		new LwjglApplication(new MainSwamp(), cfg);
	}
}
