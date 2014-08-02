package com.me.swampmonster.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class ToxicPuddle extends Prop{

	public ToxicPuddle (Vector2 position){
		this.position = position;
		sprite = new Sprite(Assets.manager.get(Assets.POISON_TRAP));
	}
	
	public void catchObject(AbstractGameObject abstractGameObject) {
		if (abstractGameObject instanceof Enemy || abstractGameObject instanceof Player){
			try {
				Method method = abstractGameObject.getClass().getMethod("setNegativeEffect", NegativeEffects.class);
				method.invoke(null, NegativeEffects.POISONED);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

}
