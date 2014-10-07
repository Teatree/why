package com.me.swampmonster.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.slotMachineStuff.SlotDescWindow;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.ScreenContainer;

public class SlotMachineScreen extends AbstractGameScreen {
	
	SpriteBatch batch;
	public static Stage stage;
	public static boolean yesWasJustPressed;
	private SlotMachineTextures slotMachineTextures;
	public Vector2 victor;
	public static List<Slot> savedSlots;
	public Dialog slotDescWindow;
	public static boolean isSlotDescWindowOpen;
//	public static boolean rewritenSlot = false;

	public SlotMachineScreen(Game game) {
		super(game);
		savedSlots = new ArrayList<Slot>();

		batch = new SpriteBatch();
		stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT,
				true, batch);
		slotMachineTextures = new SlotMachineTextures(player);
		stage.addActor(slotMachineTextures);
//		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		victor = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT
				- Gdx.input.getY());
		if (Gdx.input.justTouched()
				&& slotMachineTextures.goButton.getBoundingRectangle()
						.contains(victor) && yesWasJustPressed) {
			slotMachineTextures.selectedSlot = null;
			((Game) Gdx.app.getApplicationListener())
					.setScreen(ScreenContainer.SS);
		}
		if (Gdx.input.justTouched()
				&& slotMachineTextures.rerollButton.getBoundingRectangle()
						.contains(victor) && !yesWasJustPressed && !isSlotDescWindowOpen) {
			slotMachineTextures.generateSlots(player);
			yesWasJustPressed = false;
			for (int i = 0; i < slotMachineTextures.notAnimating.length; i++) {
				slotMachineTextures.notAnimating[i] = false;
			}
			slotMachineTextures.animCounter = 0;
		} else {
			for (Slot slot : slotMachineTextures.slots) {
				selectSlot(slot);
			}
			if (Gdx.input.justTouched()) {
				for (Slot savedSlot : savedSlots) {
					selectSavedSlot(savedSlot);
				}
			}
			
			
			stage.act();
			stage.draw();
			
			if (SlotMachineTextures.peru && !SlotMachineScreen.yesWasJustPressed) {
				for(Slot s: slotMachineTextures.slots){
					if(s.selected){
						slotDescWindow = new SlotDescWindow("Slot description", slotMachineTextures.skin, s);
						stage.addActor(slotDescWindow);
						slotDescWindow.setX(132);
						slotDescWindow.setY(123);
						slotDescWindow.setSize(350, 250);
						SlotMachineTextures.peru = false;
						isSlotDescWindowOpen = true;
//						Gdx.input.setInputProcessor(null);
						System.out.println("creating the fuckign slotDesc");
					}
				}
				
			}
		}
	}

	private void selectSlot(Slot slot) {
		// System.out.println("yesWasJustPressed: " + yesWasJustPressed);
		if(!isSlotDescWindowOpen){
		if (!yesWasJustPressed && !SlotMachineTextures.peru) {
			if (Gdx.input.justTouched()
					&& slot.sprite.getBoundingRectangle().contains(victor)
					&& slotMachineTextures.notAnimating[0]
					&& slotMachineTextures.notAnimating[1]
					&& slotMachineTextures.notAnimating[2]) {
				if (!SlotMachineTextures.peru) {
					SlotMachineTextures.peru = true;
					slotMachineTextures.selectedSlot = slot;
					slot.selected = true;

				}
			} else if (Gdx.input.justTouched()
					&& slotMachineTextures.slotMachineWindowNo
							.getBoundingRectangle().contains(victor)) {
				for (Slot s : slotMachineTextures.slots) {
					s.selected = false;
				}
				SlotMachineTextures.peru = false;
			} else if (Gdx.input.justTouched()
					&& slotMachineTextures.slotMachineWindowYes
							.getBoundingRectangle().contains(victor)) {
	}}
		}
	}

	private void selectSavedSlot(Slot slot) {
		if(!isSlotDescWindowOpen){
		if (slot.sprite.getBoundingRectangle().contains(victor)) {
			if (!SlotMachineTextures.peru) {
				SlotMachineTextures.peru = true;
				slotMachineTextures.selectedSlot = slot;
				slot.selectedSaved = true;
			}
		} else if (slotMachineTextures.slotMachineWindowNo
				.getBoundingRectangle().contains(victor)) {
			for (Slot s : savedSlots) {
				s.selected = false;
				s.selectedSaved = false;
			}
			SlotMachineTextures.peru = false;
		} else if (slotMachineTextures.slotMachineWindowYes
				.getBoundingRectangle().contains(victor)) {
			if (slot.selectedSaved) {
				if (slot instanceof Perks) {
					// slot.execute(player);
				} else {
					TheController.skill = slot;
//					slot.selected = false;
				}
				SlotMachineTextures.peru = false;
				if (!(slot instanceof Perks)) {
					((Game) Gdx.app.getApplicationListener())
							.setScreen(ScreenContainer.SS);
					
					yesWasJustPressed = false;
				}
			}
		}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
//		stage.addActor(slotMachineTextures);
		System.out.println("dum dum dum dum");
		for(Actor a : stage.getActors()){
			if(a instanceof SlotMachineTextures){
				a.remove();
				break;
			}
		}
		slotMachineTextures = new SlotMachineTextures(player);
//		if(slotMachineTextures.slotDescWindow != null){
//			stage.addActor(slotMachineTextures.slotDescWindow);
//		}
		stage.addActor(slotMachineTextures);
//		if(yesWasJustPressed){
//			slotMachineTextures.slotDescWindow.remove();
//		}
		Gdx.input.setInputProcessor(stage);
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
		stage.dispose();
	}

}
