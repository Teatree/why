package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.enemies.Enemy;

public class Trap extends Slot{
	public Circle circle;
	public Sprite trapSprite;
	public Vector2 position;
	public void catchEnemy(Enemy enemy){};
	public void putTrap(Vector2 position){
		this.position = position;
	};
}
