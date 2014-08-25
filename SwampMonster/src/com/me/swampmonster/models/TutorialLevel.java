package com.me.swampmonster.models;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.screens.MenuScreen;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.ScreenContainer;

public class TutorialLevel extends L1 {
	
	public static boolean gotoGame;
	public static int step;
	public static Sprite movehere;
	private AnimationControl aControl;
	
	
	public TutorialLevel(String tileSet, String tileMap) {
		super(tileSet, tileMap, false);
		props = new ArrayList<Prop>();
		Player.shootingSwitch = false;
		step = 1;
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		super.update(aiming, touchPos, V3point, collisionLayer, cameraHelper, dx, dy);
		
		
		if(step == 4){
			aControl = new AnimationControl(Assets.manager.get(Assets.moveHere), 1, 4, 7);
			aControl.animate(0);
			movehere = new Sprite(aControl.getCurrentFrame());
			movehere.setX(208);
			movehere.setY(400);
		}
		if(step == 3){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 3");
		}
		if(step == 2){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 2");
		}
		if(step == 1){
			TheController.pausedTutorial = true;
			System.out.println("drawing Text 1");
		}
		
		if(TheController.showFeedback){
			MenuScreen.tutorialFinished = true;
			if (MenuScreen.showTutorialButton){
				MenuScreen.showTutorialButton = true;
				TheController.showFeedback = false;
				TheController.gotoToMenu = true;
			} else {
				TheController.gotoToMenu = false;
				L1.player = new Player(null);
				Player.score = 0;
				TheController.showFeedback = false;
				MenuScreen.showTutorialButton = true;
				((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
			}
		}
		
	}
}
