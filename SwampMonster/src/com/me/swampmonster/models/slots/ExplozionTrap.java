package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class ExplozionTrap extends Trap{
	public int lifetime = 112;
	
	public ExplozionTrap() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.EXPLOSIVE_TRAP_ICON));
	}
	
	public void catchEnemy(Enemy enemy) {
		// Play explosion effect, and kill all life forms
	} 
}
