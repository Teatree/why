package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.ExplosiveProp;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.ToxicPuddle;
import com.me.swampmonster.models.TreasureBox;
import com.me.swampmonster.models.enemies.PossessedTurret;

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
		propTypes.put(2, TreasureBox.class);
		random = new Random();
	}
	
	public Prop getSomeProp(Player player) {
		int currentPropType = random.nextInt(3);
		Prop prop = null;
		try {
			prop = propTypes.get(currentPropType).getConstructor(Vector2.class).newInstance(new Vector2());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public void spawnProp(Player player, AbstractGameObject prop) {
		mapWith = (int) collisionLayer.getTileWidth()
				* collisionLayer.getWidth();
		mapHeight = (int) collisionLayer.getTileHeight()
				* collisionLayer.getHeight();

		prop.position = setPropPos(player);

		while (!isValidPropPosition(prop, player)) {
			prop.position = setPropPos(player);
		}
	}
	
	public PossessedTurret spawnTurret(Player player) {
		PossessedTurret pTurret;
		mapWith = (int) collisionLayer.getTileWidth()
				* collisionLayer.getWidth();
		mapHeight = (int) collisionLayer.getTileHeight()
				* collisionLayer.getHeight();
		
		
		pTurret = new PossessedTurret(setPropPos(player));
		
		while (!isValidPropPosition(pTurret, player)) {
			pTurret = new PossessedTurret(setPropPos(player));
		}
		return pTurret;
	}
	
	private Vector2 setPropPos(Player player) {
			Vector2 vector2 = new Vector2();
			vector2.x = random.nextInt(mapWith - 25) + 25;
			vector2.y = random.nextInt(mapHeight - 25) + 25;
			return vector2;
	}

	private boolean isValidPropPosition(AbstractGameObject prop, Player player) {
		if (CollisionHelper.isCollidable(prop.position.x, prop.position.y, collisionLayer) == null
				&& !Intersector.overlaps(prop.sprite.getBoundingRectangle(), player.rectanlge)) {
			return true;
		}
		return false;
	}
}
