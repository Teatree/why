package com.me.swampmonster.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.screens.MenuScreen;
import com.me.swampmonster.utils.CameraHelper;

public class TutorialLevel extends L1{
	
	
	public TutorialLevel(String tileSet, String tileMap) {
		super(tileSet, tileMap, false);
		
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		super.update(aiming, touchPos, V3point, collisionLayer, cameraHelper, dx, dy);;
		if(TheController.showFeedback){
			MenuScreen.tutorialFinished = true;
			MenuScreen.showTutorialButton = true;
		}
	}
}
