package com.me.swampmonster.models;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.GShape;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.screens.MenuScreen;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.ScreenContainer;

public class TutorialLevel extends L1 {
	
	public static boolean gotoGame;
	public static int step;
	public static Sprite movehere;
	public static Sprite aFingure;
	public static Sprite greenArrow;
	private AnimationControl aControl;
	static int fingerAnimCounter;
	public static boolean animating;
	public Table windowForText;
	public Label tutText;
	public Image dudesFace;
	public Label goalText;
	
	public TutorialLevel(String tileSet, String tileMap) {
		super(tileSet, tileMap, false);
		props = new ArrayList<Prop>();
		Player.shootingSwitch = false;
		step = 1;
		aControl = new AnimationControl(Assets.manager.get(Assets.moveHere), 4, 1, 2);
		dudesFace = new Image(Assets.manager.get(Assets.dudeFace));
		windowForText = new Table();
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		super.update(aiming, touchPos, V3point, collisionLayer, cameraHelper, dx, dy);
		
		
		if(step == 7){
			aFingure = null;
			Player.shootingSwitch = true;
		}
		if(step == 6){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 5");
			aFingure = new Sprite(Assets.manager.get(Assets.aFinger));
			aFingure.setSize(90, 90);
			animating = true;
		}
		if(step == 5){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 4");
		}
		if(step == 4){
			aControl.animate(0);
			movehere = new Sprite(aControl.getCurrentFrame());
			movehere.setX(535);
			movehere.setY(400);
			if(player.sprite.getBoundingRectangle().overlaps(movehere.getBoundingRectangle())){
				step++;
				movehere = null;
			}
		}
		if(step == 3){
			TheController.pausedTutorial = true;
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			tutText = new Label(
					"To walk simply tap where to go and the merc will go there",
					GShape.skin, "white");
			tutText.setWrap(true);
			windowForText = new Table();
			windowForText.setX(dudesFace.getX()+110);
			windowForText.setY(dudesFace.getY()+10);
			windowForText.setWidth(205);
			windowForText.setHeight(95);
			windowForText.add(tutText).height(100).width(205);
			L1Renderer.stage.addActor(windowForText);
			System.out.println("drawing Text 3");
		}
		if(step == 2){
			greenArrow = new Sprite(Assets.manager.get(Assets.greenPointerArrow));
			greenArrow.setX(160);
			greenArrow.setY(350);
			greenArrow.setSize(64, 64);
			greenArrow.rotate(45);
			TheController.pausedTutorial = true;
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			tutText = new Label(
					"These two bars represent your HEALTH and your OXYGEN level. ",
					GShape.skin, "white");
			tutText.setWrap(true);
			windowForText = new Table();
			windowForText.setX(dudesFace.getX()+110);
			windowForText.setY(dudesFace.getY()+10);
			windowForText.setWidth(205);
			windowForText.setHeight(95);
			windowForText.add(tutText).height(100).width(205);
			L1Renderer.stage.addActor(windowForText);
			System.out.println("drawing Text 2");
		}
		if(step == 1){
			TheController.pausedTutorial = true;
			dudesFace.setX(450);
			dudesFace.setY(30);
			dudesFace.setScale(0.5f);
			tutText = new Label(
					"Hello and welcome to the tutorial level, I am going to be your guide. [Tap to continue]",
					GShape.skin, "white");
			tutText.setWrap(true);
			windowForText = new Table();
			windowForText.setX(dudesFace.getX()+110);
			windowForText.setY(dudesFace.getY()+10);
			windowForText.setWidth(205);
			windowForText.setHeight(95);
			windowForText.add(tutText).height(100).width(205);
			L1Renderer.stage.addActor(dudesFace);
			L1Renderer.stage.addActor(windowForText);
			windowForText.debug();
			dudesFace.debug();
			System.out.println("drawing Text 1");
		}
		
		if(TheController.showFeedback){
			MenuScreen.tutorialFinished = true;
			if (MenuScreen.showTutorialButton){
				MenuScreen.showTutorialButton = true;
				TheController.showFeedback = false;
				TheController.gotoMenu = true;
			} else {
				TheController.gotoMenu = false;
				L1.player = new Player(null);
				Player.levelsScore = 0;
				Player.absoluteScore = 0;
				TheController.showFeedback = false;
				MenuScreen.showTutorialButton = true;
				((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
			}
		}
		
	}

	public static void animating() {
		if(step == 6){
			fingerAnimCounter++;
			if(fingerAnimCounter < 40){
				aFingure.setX(390);
				aFingure.setY(170);
			}
			if(fingerAnimCounter > 40){
				if(aFingure.getX()>300){
					aFingure.setX(aFingure.getX()-0.7f);
				}else{
					animating = false;
				}
				if(aFingure.getY()>80){
					aFingure.setY(aFingure.getY()-0.7f);
				}
			}
		}
	}
}
