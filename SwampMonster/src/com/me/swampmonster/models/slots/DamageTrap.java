package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class DamageTrap extends Trap{
	public int lifetime = 112;

	public DamageTrap() {
		lifeTime = 882;
		circle = new Circle();
		circle.radius = 8;
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.DAMAGE_TRAP_ICON));
		trapSprite = new Sprite (AssetsMainManager.manager.get(AssetsMainManager.DAMAGE_TRAP));
	}

	public void catchEnemy(Enemy enemy) {
		enemy.health = 0;
	}
}
