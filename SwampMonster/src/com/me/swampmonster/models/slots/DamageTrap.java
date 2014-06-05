package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class DamageTrap extends Trap{
	public int lifetime = 112;

	public DamageTrap() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.DAMAGE_TRAP_ICON));
	}
	
	public void execute (Player player){
		player.trap = new DamageTrap();
	}

	public void catchEnemy(Enemy enemy) {
		enemy.health = 0;
	}
}
