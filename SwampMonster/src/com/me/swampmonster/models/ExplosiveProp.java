package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.utils.Assets;

public class ExplosiveProp extends Prop {
	public Explosion explosion;

	public ExplosiveProp(Vector2 position) {
		this.position = position;
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
	}

	public void toDoSomething(AbstractGameObject abs) {
		explosion = new Explosion(this.position);
		explosion.explCircle = new Circle();
		explosion.damage = 0.9f;
		explosion.position = this.position;
		explosion.explCircle.setPosition(position.x, position.y);
		explosion.explosionEffect = new ParticleEffect();
		explosion.explosionEffect.load(
				Gdx.files.local("effects/FlameEffectTemp.p"),
				Gdx.files.local("effects"));
		explosion.explosionEffect.setPosition(position.x, position.y);
		explosion.explosionEffect.start();
		L1.explosions.add(explosion);
	}

}
