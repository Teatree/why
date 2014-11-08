package com.me.swampmonster.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.swampmonster.game.GShape.FeedBackWindow;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.slotMachineStuff.SlotDescWindow;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.slotMachineStuff.SlotMiniWindow;
import com.me.swampmonster.utils.Constants;

public class SlotMachineScreen extends AbstractGameScreen {
	
	SpriteBatch batch;
	public static Stage stage;
	public static boolean yesWasJustPressed;
	private SlotMachineTextures slotMachineTextures;
	public Vector2 victor;
	public static List<Slot> savedSlots;
	public Dialog slotDescWindow;
	public static boolean isSlotDescWindowOpen;
	private Array<Viewport> viewports;
	private Array<String> names;
	private InputMultiplexer inputMultiplexer;
	
	private SlotMiniWindow slotMiniWindow;
	
	public SlotMachineScreen(Game game) {
		super(game);
		savedSlots = new ArrayList<Slot>();
		batch = new SpriteBatch();
		stage = new Stage();
		slotMachineTextures = new SlotMachineTextures(player);
		stage.addActor(slotMachineTextures);
		viewports = getViewports(stage.getCamera());
		names = getViewportNames();
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		stage.setViewport(viewports.first());
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.justTouched()){
			for(Actor a : stage.getActors()){
				if(a instanceof SlotMiniWindow){
					a.remove();
					break;
				}
			}
		}
		victor = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		victor = stage.screenToStageCoordinates(victor);
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
//						slotDescWindow.debug();
//						slotDescWindow.getButtonTable().debug();
//						slotDescWindow.getContentTable().debug();
						slotDescWindow.setSize(Constants.SLOT_DESC_WINDOW_WIDTH, Constants.SLOT_DESC_WINDOW_HEIGHT);
						slotDescWindow.setX(200);
						slotDescWindow.setY(100);
						SlotMachineTextures.peru = false;
						isSlotDescWindowOpen = true;
					}
				}
				
			}
//		}
	}

	private void selectSlot(Slot slot) {
		if (!isSlotDescWindowOpen) {
			if (!yesWasJustPressed && !SlotMachineTextures.peru) {
				if (Gdx.input.justTouched() && slot.sprite.getBoundingRectangle().contains(victor)
						&& slotMachineTextures.notAnimating[0] && slotMachineTextures.notAnimating[1]
						&& slotMachineTextures.notAnimating[2]) {
					if (!SlotMachineTextures.peru) {
						SlotMachineTextures.peru = true;
						slotMachineTextures.selectedSlot = slot;
						slot.selected = true;
					}
				}
			}
		}
	}

	private void selectSavedSlot(Slot slot) {
		if (!isSlotDescWindowOpen) {
			if (slot.sprite.getBoundingRectangle().contains(victor)) {
				if (!(slot instanceof Perks)) {
					slotMachineTextures.selectedSlot = slot;
					for (Slot s : savedSlots) {
						s.selectedSaved = false;
					}
					slot.selectedSaved = true;
					// slot.selected = false;
				}
				slotMiniWindow = new SlotMiniWindow("", slotMachineTextures.skin, "miniWindow", slot);
				stage.addActor(slotMiniWindow);
//				slotMiniWindow.debug();
				slotMiniWindow.setSize(250, 200);
				slotMiniWindow.setX(slot.sprite.getX());
				slotMiniWindow.setY(slot.sprite.getY() + slot.sprite.getHeight() + 5);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		for(Actor a : stage.getActors()){
			if(a instanceof SlotMachineTextures){
				a.remove();
				break;
			}
		}
		for(Actor a: L1Renderer.stage.getActors()){
			if(a instanceof FeedBackWindow){
				a.remove();
			}
		}
		TheController.showFeedback = false;
		slotMachineTextures = new SlotMachineTextures(player);
		stage.addActor(slotMachineTextures);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
	}

	static public Array<String> getViewportNames () {
		Array<String> names = new Array<String>();
		names.add("FillViewport");
		names.add("StretchViewport");
		names.add("FitViewport");
		names.add("ExtendViewport: no max");
		names.add("ExtendViewport: max");
		names.add("ScreenViewport: 1:1");
		names.add("ScreenViewport: 0.75:1");
		names.add("ScalingViewport: none");
		return names;
	}

	static public Array<Viewport> getViewports (Camera camera) {
		int minWorldWidth = 800;
		int minWorldHeight = 480;
		int maxWorldWidth = 640;
		int maxWorldHeight = 480;

		Array<Viewport> viewports = new Array<Viewport>();
		viewports.add(new FillViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new StretchViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new FitViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, camera));
		viewports.add(new ScreenViewport(camera));

		ScreenViewport screenViewport = new ScreenViewport(camera);
		screenViewport.setUnitsPerPixel(0.75f);
		viewports.add(screenViewport);

		viewports.add(new ScalingViewport(Scaling.none, minWorldWidth, minWorldHeight, camera));
		return viewports;
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
