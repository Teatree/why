package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.utils.Assets;

public class ExplosiveProp extends Prop {
	public Explosion explosion;

	public ExplosiveProp(Vector2 position) {
		this.position = position;
		state = State.STANDARD;
		
		despawningCounter = 60;
		
		sizeW = 16;
		sizeH = 32;
		
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.propExplosiveBarrel), 4, 4, 3.6f));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.propExplosiveBarrel), 4, 4, 3.6f));
		animationsStandard.put(State.ONFIRE, new AnimationControl(Assets.manager.get(Assets.propExplosiveBarrel), 4, 4, 3.6f));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
	}
	
	public void toDoSomething() {
		explosion = new Explosion(this.position, Explosion.EXPLOSION_TYPE_STANDART);
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
		System.out.println("doing Somehting and I don't have abs as a parameter");
	}

	@Override
	public void toDoSomething(AbstractGameObject abs) {
		// TODO Auto-generated method stub
		System.out.println("doing Somehting and have abs as parameter");
		
	}

}
