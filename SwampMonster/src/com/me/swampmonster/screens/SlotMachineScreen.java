package com.me.swampmonster.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.ScreenContainer;

public class SlotMachineScreen extends AbstractGameScreen {
	private static final int Max_slot_level = 4;
	SpriteBatch batch;
	private Stage stage;
	public static boolean yesWasJustPressed;
	private SlotMachineTextures slotMachineTextures;
	public Vector2 victor;
	public static List<Slot> savedSlots;
	
	public SlotMachineScreen(Game game) {
		super(game);
		savedSlots = new ArrayList<Slot>();

		batch = new SpriteBatch();
		stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT,
				true, batch);

		slotMachineTextures = new SlotMachineTextures(player);

		stage.addActor(slotMachineTextures);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		victor = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT
				- Gdx.input.getY());
		if (Gdx.input.justTouched() 
				&& slotMachineTextures.goButton.getBoundingRectangle().contains(victor)){
			((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
		}
		if (Gdx.input.justTouched() 
				&& slotMachineTextures.rerollButton.getBoundingRectangle().contains(victor)){
			slotMachineTextures.generateSlots(player);
			yesWasJustPressed = false;
			for (int i = 0; i < slotMachineTextures.notAnimating.length; i++){
				slotMachineTextures.notAnimating[i] = false;
			}
			slotMachineTextures.animCounter = 0;
		} else {
			for (Slot slot : slotMachineTextures.slots) {
				selectSlot(slot);
			}
			if (Gdx.input.justTouched()){
				for (Slot savedSlot : savedSlots){
					selectSavedSlot(savedSlot);
				}
			}
			stage.draw();
		}
	}

	private void selectSlot(Slot slot) {
		if (Gdx.input.justTouched()
				&& slot.sprite.getBoundingRectangle().contains(victor) && slotMachineTextures.notAnimating[0] && slotMachineTextures.notAnimating[1] && slotMachineTextures.notAnimating[2]) {
			if (!slotMachineTextures.peru) {
				slotMachineTextures.peru = true;
				slotMachineTextures.selectedSlot = slot;
				slot.selected = true;
					
			}
		} else if (Gdx.input.justTouched()
				&& slotMachineTextures.slotMachineWindowNo
						.getBoundingRectangle().contains(victor)) {
			for(Slot s : slotMachineTextures.slots){
				s.selected = false;
			}
			slotMachineTextures.peru = false;
		} else if (Gdx.input.justTouched()
				&& slotMachineTextures.slotMachineWindowYes
						.getBoundingRectangle().contains(victor)) {
			yesWasJustPressed = true;
			if (slot.selected){
				if (slot instanceof Perks) {
					slot.execute(player);
					try {
						int i = slot.getClass().getField("level")
								.getInt(null);
						if (i < Max_slot_level) {
							i++;
							slot.getClass().getField("level")
									.setInt(null, i);
						}
					} catch (Exception e) {
					}
				
				} else {
					TheController.skill = slot;
					try {
						int i = TheController.skill.getClass()
								.getField("level").getInt(null);
						if (i < Max_slot_level) {
							i++;
							TheController.skill.getClass()
									.getField("level").setInt(null, i);
						}
					} catch (Exception e) {
					}
				}
				slotMachineTextures.peru = false;
				boolean rewritenSlot = false;
				for(Slot s : savedSlots){
					if(s.getClass().equals(slot.getClass())){
						s = slot;
						rewritenSlot = true;
					}
				}
				if(!rewritenSlot){
					savedSlots.add(slot);
				}
//				((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
			}
		}
		
		
	}
	
	private void selectSavedSlot(Slot slot) {
		if (slot.sprite.getBoundingRectangle().contains(victor)) {
			if (!slotMachineTextures.peru) {
				slotMachineTextures.peru = true;
				slotMachineTextures.selectedSlot = slot;
				slot.selectedSaved = true;
			}
		} else if (slotMachineTextures.slotMachineWindowNo.getBoundingRectangle().contains(victor)) {
			for(Slot s : savedSlots){
				s.selected = false;
				s.selectedSaved = false;
			}
			slotMachineTextures.peru = false;
		} else if (slotMachineTextures.slotMachineWindowYes
						.getBoundingRectangle().contains(victor)) {
			if (slot.selectedSaved){
				if (slot instanceof Perks) {
//					slot.execute(player);
				} else {
					TheController.skill = slot;
				}
				slotMachineTextures.peru = false;
				if (!(slot instanceof Perks)){
					((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
					yesWasJustPressed = false;
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		slotMachineTextures = new SlotMachineTextures(player);
		stage.addActor(slotMachineTextures);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
