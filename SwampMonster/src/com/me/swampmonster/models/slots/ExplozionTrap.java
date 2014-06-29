package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ExplozionTrap extends Trap{
	public int lifetime = 112;
	public static int level;
	
	public ExplozionTrap() {
		lifeTime = Constants.ExplozionTrap_LifeTime_L1;
		circle = new Circle();
		circle.radius = Constants.ExplozionTrap_CircleRadius_L1;
		
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.explosiveTrap));
	}
	
	public void catchEnemy(Enemy enemy) {
		// Play explosion effect, and kill all life forms
	} 
}
