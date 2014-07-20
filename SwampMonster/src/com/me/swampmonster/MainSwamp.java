package com.me.swampmonster;

import com.badlogic.gdx.Game;
import com.me.swampmonster.screens.SwampScreen;
import com.me.swampmonster.utils.Assets;

public class MainSwamp extends Game {

	public void create() {		
		Assets.load();
		while (!Assets.manager.update()){
			System.err.println("MainSwamp");
		}
		setScreen(new SwampScreen(this));
	}
	public void dispose() {
		super.dispose();
	}
	public void render() {		
		super.render();
	}
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	public void pause() {
		super.pause();
	}
	public void resume() {
		super.resume();
	}
}