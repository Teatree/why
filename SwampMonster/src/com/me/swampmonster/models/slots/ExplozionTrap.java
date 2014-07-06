package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ExplozionTrap extends Trap{
	public int lifetime = 112;
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ExplozionTrap_Description_L1);
		descriptionByLevel.put(1, Constants.ExplozionTrap_Description_L2);
		descriptionByLevel.put(2, Constants.ExplozionTrap_Description_L3);
		descriptionByLevel.put(3, Constants.ExplozionTrap_Description_L4);
		descriptionByLevel.put(4, Constants.ExplozionTrap_Description_L5);
	}
	
	public ExplozionTrap() {
		circle = new Circle();
		
		switch (level) {
		case 0:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L1;
			coolDown = Constants.ExplozionTrap_CoolDown_L1;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L1;
			break;
		case 1:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L2;
			coolDown = Constants.ExplozionTrap_CoolDown_L2;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L2;
			break;
		case 2:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L3;
			coolDown = Constants.ExplozionTrap_CoolDown_L3;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L3;
			break;
		case 3:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L4;
			coolDown = Constants.ExplozionTrap_CoolDown_L4;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L4;
			break;
		case 4:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L5;
			coolDown = Constants.ExplozionTrap_CoolDown_L5;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L5;
			break;
		}
		
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.explosiveTrap));
	}
	
	public void catchEnemy(Enemy enemy) {
		effect = new ParticleEffect();
		effect.load(Gdx.files.local("effects/explosionEffect.p"), Gdx.files.local("effects"));
		effect.setPosition(position.x, position.y);
		effect.start();
		showEffect = true;
	}

	public String getDescription() {
		return descriptionByLevel.get(level);
	} 
}
