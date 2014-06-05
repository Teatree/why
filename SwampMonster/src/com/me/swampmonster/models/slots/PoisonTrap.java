package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class PoisonTrap extends Trap{
	public Vector2 position;
	
	public PoisonTrap() {
		lifeTime = 112;
		trapSprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.POISON_TRAP));
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.POISONED_TRAP_ICON));
	}
	
	public void execute (Player player){
		player.trap = new PoisonTrap();
	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.POISONED);
		System.out.println("Poison trap says "+enemy.negativeEffect);
	}

	public Sprite getTrapSprite() {
		return trapSprite;
	}
	
	
}
