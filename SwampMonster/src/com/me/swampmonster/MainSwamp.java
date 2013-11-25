package com.me.swampmonster;

import com.badlogic.gdx.Game;
import com.me.swampmonster.screens.SwampScreen;

public class MainSwamp extends Game {
	
	public void create() {		
		setScreen(new SwampScreen());
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
