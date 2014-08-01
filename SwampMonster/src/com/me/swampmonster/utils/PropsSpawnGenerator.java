package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.ExplosiveProp;
import com.me.swampmonster.models.HiddenStuff;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.ToxicPuddle;
import com.me.swampmonster.models.enemies.Enemy;

public class PropsSpawnGenerator {
	private Map <Integer, Class<? extends Prop>> propTypes;
	private Random random;
	public TiledMapTileLayer collisionLayer;
	int mapWith;
	int mapHeight;
	
	public PropsSpawnGenerator(){
		propTypes = new HashMap<Integer, Class<? extends Prop>>();
		propTypes.put(0, ToxicPuddle.class);
		propTypes.put(1, ExplosiveProp.class);
		propTypes.put(2, HiddenStuff.class);
		random = new Random();
	}
	
	public Prop getSomeProp(Player player) {
		int currentPropType = random.nextInt(2);
		Prop prop = null;
		try {
			prop = propTypes.get(currentPropType).getConstructor(Vector2.class).newInstance(new Vector2());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public void spawnProp(Player player, Prop prop) {
		mapWith = (int) collisionLayer.getTileWidth()
				* collisionLayer.getWidth();
		mapHeight = (int) collisionLayer.getTileHeight()
				* collisionLayer.getHeight();

		setPropPos(prop, player);

		while (!isValidTargetPosition(prop, player)) {
			setPropPos(prop, player);
		}
	}

	
	private void setPropPos(Prop prop, Player player) {
			Vector2 vector2 = new Vector2();
			vector2.x = random.nextInt((int)Constants.VIEWPORT_GUI_WIDTH);
			vector2.y = random.nextInt((int)Constants.VIEWPORT_GUI_HEIGHT);
			prop.position = vector2;
	}

	private boolean isValidTargetPosition(Prop prop, Player player) {
		if (CollisionHelper.isCollidable(prop.position.x, prop.position.y, collisionLayer) == null
				&& !Intersector.overlaps(prop.sprite.getBoundingRectangle(), player.rectanlge)) {
			return true;
		}
		return false;
	}
}
