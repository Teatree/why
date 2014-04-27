package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.swampmonster.game.TheController;

public class SlotMachineScreen extends AbstractGameScreen{
	SpriteBatch batch;
	Texture slotMachineCaseTexture;
	Texture slotMachineNextButtonTexture;
	BitmapFont font;
	TheController theController; 
	
	public SlotMachineScreen(Game game){
		super(game);
		
		theController = new TheController(game);
		
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
		         Gdx.files.internal("data/font_0.tga"), false);
		slotMachineCaseTexture = new Texture("data/slotMachineCase.png");
		slotMachineNextButtonTexture = new Texture("data/slotMachineNextButton.png");
		batch = new SpriteBatch();
	}
	
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(slotMachineCaseTexture, 144, 112);
		batch.draw(slotMachineNextButtonTexture, 535, 125, 111, 111);
		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
		font.draw(batch, "MaxHP: " + theController.level1.getPlayer().getHealth(), 157, 335);
		font.draw(batch, "MaxO2: " + theController.level1.getPlayer().getOxygen(), 157, 320);
		font.draw(batch, "Damage: " + theController.level1.getPlayer().getDamage(), 157, 305);
		font.draw(batch, "AS: " + theController.level1.getPlayer().getShotCoolDown(), 157, 290);
		batch.end();
		if(Gdx.input.isTouched()){
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
