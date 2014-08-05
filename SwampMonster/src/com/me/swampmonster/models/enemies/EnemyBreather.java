package com.me.swampmonster.models.enemies;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyBreather extends Enemy {

	public EnemyBreather(Vector2 position) {
		super(position);
		points = 25;
		health = 1;
		damage = 1;
	}
	
	@Override
	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies) {
		super.update(collisionLayer, player, cameraHelper, enemies);
		if (!L1.hasAtmosphere){
			health -= 0.4;
		}
	}

}
