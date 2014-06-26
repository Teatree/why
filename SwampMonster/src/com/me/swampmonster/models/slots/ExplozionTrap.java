package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class ExplozionTrap extends Trap{
	public int lifetime = 112;
	public static int level;
	
	public ExplozionTrap() {
		lifeTime = 112;
		circle = new Circle();
		circle.radius = 16;
		
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.explosiveTrap));
	}
	
	public void catchEnemy(Enemy enemy) {
		// Play explosion effect, and kill all life forms
	} 
}
