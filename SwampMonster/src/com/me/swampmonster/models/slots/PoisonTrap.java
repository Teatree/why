package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class PoisonTrap extends Trap{
	public static int level;
	public PoisonTrap() {
		lifeTime = 112;
		circle = new Circle();
		circle.radius = 16;
		
		trapSprite = new Sprite(Assets.manager.get(Assets.POISON_TRAP));
		sprite = new Sprite(Assets.manager.get(Assets.POISONED_TRAP_ICON));
	}
	
//	public void execute (Player player){
//		player.trap = this;
//		player.trap.position = new Vector2(player.position.x, player.position.y);
//		player.trap.circle.x = player.trap.position.x+8;
//		player.trap.circle.y = player.trap.position.y+4;
//	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.POISONED);
	}

	public Sprite getTrapSprite() {
		return trapSprite;
	}
	
	
}
