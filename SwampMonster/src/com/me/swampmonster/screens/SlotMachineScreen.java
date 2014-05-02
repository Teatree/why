package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.swampmonster.models.Player;

public class SlotMachineScreen extends AbstractGameScreen {
	SpriteBatch batch;
	Texture slotMachineCaseTexture;
	Texture slotMachineNextButtonTexture;
	BitmapFont font;
	public Player player;

	public SlotMachineScreen(Game game) {
		super(game);
		
		player = new Player(null);
		
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
				Gdx.files.internal("data/font_0.tga"), false);
		slotMachineCaseTexture = new Texture("data/slotMachineCase.png");
		slotMachineNextButtonTexture = new Texture(
				"data/slotMachineNextButton.png");
		batch = new SpriteBatch();
	}

	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(slotMachineCaseTexture, 144, 112);
		batch.draw(slotMachineNextButtonTexture, 535, 125, 0.10f*Gdx.graphics.getWidth(), 0.20f*Gdx.graphics.getHeight());
		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
		font.draw(batch, "MaxHP: "
				+ player.getMaxHealth(), 284, 215);
		font.draw(batch, "MaxO2: "
				+ player.maxOxygen, 284, 200);
		font.draw(batch, "Damage: "
				+ player.getDamage(), 284, 185);
		font.draw(batch, "AS: "
				+ player.getShotCoolDown(), 284, 170);
		font.draw(batch, "Score: "
				+ player.getPoints(), 156, 338);
		batch.end();
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
