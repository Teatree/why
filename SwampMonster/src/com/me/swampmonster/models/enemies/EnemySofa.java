package com.me.swampmonster.models.enemies;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;

public class EnemySofa extends Enemy {

	public EnemySofa(Vector2 position) {
		super(position);
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		sprite.scale(1);
		movementSpeed = 0.5f;
		health = 6;
		damage = 3;
		points = 50;
		attackSpeed = 15;
		STANDART_MOVEMENT_SPEED = movementSpeed;
		yellowAura = new Circle();
		yellowAura.radius = 32;
		yellowAura.x = position.x + sprite.getWidth() / 2;
		yellowAura.y = position.y + sprite.getHeight() / 2;
	}
	@Override
	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies){
		super.update(collisionLayer, player, cameraHelper, enemies);
		yellowAura.x = position.x;
		yellowAura.y = position.y;
	}

}
