package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class PoisonTrap extends Slot implements Trap{
	public int lifetime = 112;
	
	public PoisonTrap() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.POISONED_TRAP_ICON));
	}
	
	public void execute (Player player){
		player.trap = new PoisonTrap();
	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.POISONED);
	}
}
