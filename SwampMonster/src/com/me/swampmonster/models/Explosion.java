package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.enemies.Enemy;

public class Explosion {
	public Circle explCircle;
	public float damage;
	public ParticleEffect explosionEffect;
	public float incrementalDamageValue;
	public float incrementalCircleValue;
	public Vector2 position;
	
	public Explosion(Vector2 position){
		this.position = position;
		damage = 1f;
		incrementalCircleValue = 0.6f;
		incrementalDamageValue = 0.6f;
		explCircle = new Circle();
		explCircle.radius = 90;
	
		
	}
	
	public void update(){
		if (explosionEffect != null && !explosionEffect.isComplete()){
			explCircle.radius += incrementalCircleValue;
			damage += incrementalDamageValue;
			
		}else if(explosionEffect != null && explosionEffect.isComplete()){
			explCircle.radius = 0;
		}
	}
	
	public void cause(AbstractGameObject ago){
		System.out.println("penis face");
		ago.position.x = ago.position.x + 0.4f;
		ago.position.y = ago.position.y + 0.4f;
		ago.health = ago.health - damage;
	}
	
}
