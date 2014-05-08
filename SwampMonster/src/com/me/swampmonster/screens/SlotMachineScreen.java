package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Constants;

public class SlotMachineScreen extends AbstractGameScreen {
	SpriteBatch batch;
	public Player player;
	private BitmapFont font;
	private Stage stage;
	private SlotMachineTextures slotMachineTextures;
	
	public SlotMachineScreen(Game game) {
		super(game);
		
		player = new Player(null);
		
		batch = new SpriteBatch();
		stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, true, batch);
		
		slotMachineTextures = new SlotMachineTextures(player);
		stage.addActor(slotMachineTextures);
	}

	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.draw();
		if (Gdx.input.isTouched()) {
			game.setScreen(new SwampScreen(game));
		}
		
	}

	public void resize(int width, int height) {

	}

	public void show() {

	}

	public void hide() {

	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

}
