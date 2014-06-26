package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Constants;

public class SlotMachineScreen extends AbstractGameScreen {
	SpriteBatch batch;
	public Player player;
	private Stage stage;
	private SlotMachineTextures slotMachineTextures;
	public Vector2 victor;

	public SlotMachineScreen(Game game) {
		super(game);

		player = new Player(null);

		batch = new SpriteBatch();
		stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT,
				true, batch);

		slotMachineTextures = new SlotMachineTextures(player);

		stage.addActor(slotMachineTextures);
	}

	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		victor = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT
				- Gdx.input.getY());
		for (Slot slot : slotMachineTextures.slots) {
			if (Gdx.input.justTouched()
					&& slot.sprite.getBoundingRectangle().contains(victor)) {
				if (!slotMachineTextures.peru) {
					slotMachineTextures.peru = true;
					slotMachineTextures.selectedSlot = slot;
					TheController.skill = slot;
						
				}
			} else if (Gdx.input.justTouched()
					&& slotMachineTextures.slotMachineWindowNo
							.getBoundingRectangle().contains(victor)) {

				slotMachineTextures.peru = false;
			} else if (Gdx.input.justTouched()
					&& slotMachineTextures.slotMachineWindowYes
							.getBoundingRectangle().contains(victor)) {
				
				if (slot == TheController.skill) {
					System.out.println("beep");
					try {
						int i = TheController.skill.getClass()
								.getField("level").getInt(null);
						i++;
						TheController.skill.getClass().getField("level")
								.setInt(null, i);
					} catch (Exception e) {

					}
				}
				slotMachineTextures.peru = false;
				game.setScreen(new SwampScreen(game));
			}
		}
		stage.draw();
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
