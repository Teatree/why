package com.me.swampmonster.models;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.screens.MenuScreen;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.ScreenContainer;

public class TutorialLevel extends L1{
	
	public static boolean gotoGame;
	public static int step;
	
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
