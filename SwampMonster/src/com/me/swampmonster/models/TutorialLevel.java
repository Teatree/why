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
	public static Sprite arrowTut;
	private AnimationControl aControl;
	static int fingerAnimCounter;
	public static boolean animating;
	public static Table windowForText;
	public static Label tutText;
	public Image dudesFace;
	public Label goalText;
	public static int timer;
	
	public TutorialLevel(String tileSet, String tileMap) {
		super(tileSet, tileMap, false);
		props = new ArrayList<Prop>();
		Player.shootingSwitch = false;
		step = 1;
		aControl = new AnimationControl(Assets.manager.get(Assets.moveHere), 4, 1, 2);
		dudesFace = new Image(Assets.manager.get(Assets.dudeFace));
		arrowTut = new Sprite(Assets.manager.get(Assets.arrow));
		windowForText = new Table();
		timer = 0;
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		super.update(aiming, touchPos, V3point, collisionLayer, cameraHelper, dx, dy);
		
		
		if(step == 10){
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(dudesFace)){
					a.remove();
				}
			}
			aFingure = null;
			Player.shootingSwitch = true;
		}
		if(step == 9){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 9");
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			tutText = new Label(
					"... And let go to shoot",
					GShape.skin, "white");
			tutText.setWrap(true);
			windowForText = new Table();
			windowForText.setX(dudesFace.getX()+110);
			windowForText.setY(dudesFace.getY()+10);
			windowForText.setWidth(205);
			windowForText.setHeight(95);
			windowForText.add(tutText).height(100).width(205);
			L1Renderer.stage.addActor(windowForText);
			aFingure = new Sprite(Assets.manager.get(Assets.aFinger));
			aFingure.setSize(128, 128);
			animating = true;
		}
		if(step == 8){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 8");
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			tutText = new Label(
					"Just slide your finger from your character in the opposite direction of where you are shooting to aim.",
					GShape.skin, "white");
			tutText.setWrap(true);
			windowForText = new Table();
			windowForText.setX(dudesFace.getX()+110);
			windowForText.setY(dudesFace.getY()+10);
			windowForText.setWidth(205);
			windowForText.setHeight(95);
			windowForText.add(tutText).height(100).width(205);
			L1Renderer.stage.addActor(windowForText);
			aFingure = new Sprite(Assets.manager.get(Assets.aFinger));
			aFingure.setSize(128, 128);
			animating = true;
		}
		if(step == 7){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 7");
			dudesFace.setX(450);
			dudesFace.setY(30);
			dudesFace.setScale(0.5f);
			tutText = new Label(
					"Oh no, an alien life form! Quick, kill that thing before it tries to communicate with you!",
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
		}
		if(step == 6){
			TheController.pausedTutorial = false;
			aControl.animate(0);
			movehere = new Sprite(aControl.getCurrentFrame());
			movehere.setX(535);
			movehere.setY(400);
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(dudesFace)){
					a.remove();
				}
			}
			if(player.sprite.getBoundingRectangle().overlaps(movehere.getBoundingRectangle())){
				step++;
				movehere = null;
				L1.enemiesOnStage.get(0).position.x = L1.player.position.x+200;
				L1.enemiesOnStage.get(0).position.y = L1.player.position.y;

			}
			System.out.println("drawing Text 6");
		}
		if(step == 5){
			TheController.pausedTutorial = true;
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			tutText = new Label(
					"Now move to that green arrow over there, you obedient chum...",
					GShape.skin, "white");
			tutText.setWrap(true);
			windowForText = new Table();
			windowForText.setX(dudesFace.getX()+110);
			windowForText.setY(dudesFace.getY()+10);
			windowForText.setWidth(205);
			windowForText.setHeight(95);
			windowForText.add(tutText).height(100).width(205);
			L1Renderer.stage.addActor(windowForText);
			System.out.println("drawing Text 5");
		}
		if(step == 4){
			animating = true;
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(dudesFace)){
					a.remove();
				}
			}
			for(Actor a : L1Renderer.stage.getActors()){
				if(a.equals(windowForText)){
					a.remove();
				}
			}
			if(timer>150){
				TheController.pausedTutorial = true;
//				step++;
				dudesFace.setX(450);
				dudesFace.setY(30);
				dudesFace.setScale(0.5f);
				tutText = new Label(
						"Well Done! Nice ehm... moves. [Tap to continue]",
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
					"To move, tap somewhere to move there. You can tap and hold to mave as well.",
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
					"Welcome to the tutorial level! I am going to be your guide. [Tap to continue]",
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
		if(step == 4){
			timer++;
			if(timer<200 && timer>150){
				animating=true;
			}else if(timer>200){
				timer = 0;
				animating=false;
			}
		}
		if(step == 8){
			fingerAnimCounter++;
			if(fingerAnimCounter < 40){
				aFingure.setX(290);
				aFingure.setY(240);
			}
			if(aFingure.getX() <= 290 && aFingure.getX()>260){
				L1.player.bow = new Sprite(L1.player.bowFrames[0][0]);
			}else if(aFingure.getX() <260 && aFingure.getX()>230){
				L1.player.bow = new Sprite(L1.player.bowFrames[1][0]);
			}else if( aFingure.getX()<230 && aFingure.getX()>200){
				L1.player.bow = new Sprite(L1.player.bowFrames[2][0]);
			}else if( aFingure.getX()<200 && aFingure.getX()>180){
				L1.player.bow = new Sprite(L1.player.bowFrames[3][0]);
			}
			if(fingerAnimCounter == 140){
				tutText.setText("Just slide your finger from your character in the opposite direction of where you are shooting to aim. [Tap to continue]");
			}
			L1.player.bow.setPosition(405, 252);
			L1.player.bow.setRotation(180);
			
			if(fingerAnimCounter > 40){
				if(aFingure.getX()>180){
					aFingure.setX(aFingure.getX()-0.9f);
				}else{
					animating = false;
				}
			}
			if(fingerAnimCounter == 164){
				fingerAnimCounter = 0;
			}
			System.out.println("finger" + fingerAnimCounter);
		}
		if(step == 9){
			System.out.println("finger" + fingerAnimCounter);
			fingerAnimCounter++;
			if(fingerAnimCounter < 40){
				aFingure.setX(180);
				aFingure.setY(240);
			}
			if(aFingure.getY() < 243 && aFingure.getY() >= 240){
				L1.player.bow = new Sprite(L1.player.bowFrames[3][0]);
			}
			else if(aFingure.getY() <= 246 && aFingure.getY() > 243){
				L1.player.bow = new Sprite(L1.player.bowFrames[1][0]);
			}
			else if(aFingure.getY() <= 250 && aFingure.getY() > 246){
				L1.player.bow = new Sprite(L1.player.bowFrames[0][0]);
			}
			L1.player.bow.setPosition(405, 252);
			L1.player.bow.setRotation(180);
			
			if(fingerAnimCounter > 40){
				if(aFingure.getY()<250){
					aFingure.setY(aFingure.getY()+1.2f);
				}else{
					animating = false;
				}
			}
		}
	}
}
