package com.me.swampmonster.models.slots;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class DamageTrap extends Trap{
	public int lifetime = 112;

	public DamageTrap() {
		lifeTime = 1589;
//		effect = new ParticleEffect();
//		effect.load(Gdx.files.local("effects\\FlameEffectTemp.p"), Gdx.files.local("effects"));
		circle = new Circle();
		circle.radius = 8;
		
		//:TODO Had to change because images didn't weren't commited and I need a working version now
		sprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP_ICON));
		trapSprite = new Sprite (Assets.manager.get(Assets.DAMAGE_TRAP));
		coolDown = 600;
	}

	public void catchEnemy(Enemy enemy) {
//		effect.load(Gdx.files.local("FlameEffectTemp.p"), Gdx.files.local("data"));
//		effect.setPosition(position.x, position.y);
//		effect.start();
		showEffect = true;
		enemy.health = 0;
	}
}
