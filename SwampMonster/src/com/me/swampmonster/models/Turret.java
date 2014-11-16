package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class Turret extends AbstractGameObject {

	public Circle killingAura = new Circle();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public AbstractGameObject victim;
	public Rectangle turretAimerBot;
	public boolean canAttack;
	public int lifeTime;
	public int standardLifeTime;
	public int timeRemove = 0; 
	
	protected AnimationControl animControl;
	
	protected int counter;
	public int time;
	
	public Turret() {
		animControl = new AnimationControl(Assets.manager.get(Assets.turretImg), 4, 4, 3.9f);
		sprite = new Sprite(animControl.getCertainFrame(0));
		
		circle = new Circle();
		
		turretAimerBot = new Rectangle();
	}

	public void update() {
		if (!animControl.animating){
			animControl.stateTimeDoComplex = 0f;
		}
		
		//:TODO GET THE ANIAMTIONS RIGHT!
		if(state == State.DEAD){
			animControl.stateTimeDoComplex = 0f;
		}
		if(lifeTime <= 0 ){
			state = State.DEAD;
		}else if(lifeTime <= 40 || health <= 0){
			state = State.DESPAWNING;
//			animControl = new AnimationControl(Assets.manager.get(Assets.turretImg), 4, 4, 3.9f);
			animControl.doComplexAnimation(8, 1f, 0.02f, Animation.PlayMode.NORMAL);
			animControl.animating = true;
			sprite = new Sprite(animControl.getCurrentFrame());
//			System.out.println("[TURRET] STATE ?DESPAWNING? : " + state );
		}else if(lifeTime <= standardLifeTime && lifeTime >= 40){
			state = State.STANDARD;
			animControl.animate(0);
			sprite = new Sprite(animControl.getCurrentFrame());
//			System.out.println("[TURRET] STATE ?STANDARD? : " + state );
		}else{
			state = State.SPAWNING;
			animControl.doComplexAnimation(4, 1f, 0.02f, Animation.PlayMode.NORMAL);
			sprite = new Sprite(animControl.getCurrentFrame());
//			System.out.println("[TURRET] STATE ?SPAWNING? : " + state );
		}
		
		lifeTime--;
		if(state == State.DESPAWNING && lifeTime>= 100){
			//:TODO change this as well, if you otta change the speed of the despawning animation
			lifeTime = 100;
		}
		if(state == State.DESPAWNING){
			// Aniamte
		}
		if(state == State.SPAWNING){
			// Animate
			
		}
		
		if(state == State.STANDARD){
			if(victim==null || victim.state == State.DEAD){
				for (Enemy e : L1.enemiesOnStage) {
					if (e.oRangeAura.overlaps(killingAura)) {
						
						victim = e;
						canAttack = false;
					}
				}
			}else{
				float direction_x = victim.position.x - position.x;
				float direction_y = victim.position.y - position.y;
				
				float length = (float) Math.sqrt(direction_x * direction_x
						+ direction_y * direction_y);
				direction_x /= length;
				direction_y /= length;
				
				if(counter > 0){
					counter--;
				}
				
				if(canAttack && counter == 0){
					Projectile p = new Projectile(new Vector2(100, 100));
					p.sprite = new Sprite(Assets.manager.get(Assets.turretProjectile));
					p.setPosition(new Vector2(position.x + p.sprite.getWidth()/2,
							position.y + p.sprite.getHeight()/3*2));
		
					p.setDirection(direction_x, direction_y);
					p.effect = EffectCarriers.NONE;
					Projectile.resistance = 0.0008f;
					p.force = 1f;
					this.projectiles.add(p);
					
					canAttack = false;
					counter = attackSpeed;
					
				}else{
					if (turretAimerBot.x > victim.getPosition().x + victim.sprite.getWidth()/2 - 4
							|| turretAimerBot.x < victim.getPosition().x + victim.sprite.getWidth()/2 - 10
							|| turretAimerBot.y > victim.getPosition().y + victim.sprite.getHeight()/2- 4
							|| turretAimerBot.y < victim.getPosition().y + victim.sprite.getHeight()/2- 10) {
						Collidable collidable = CollisionHelper.isCollidable(turretAimerBot.x+5, turretAimerBot.y+5, TheController.collisionLayer);
						if (collidable == null){
							turretAimerBot.x += direction_x * 5;
						}
						if (collidable == null){
							turretAimerBot.y += direction_y * 5;
						}
						if (collidable != null){
//							System.out.println("can't aimBot there!");
							turretAimerBot.x = position.x + victim.sprite.getWidth()/2;
							turretAimerBot.y = position.y + victim.sprite.getHeight()/2;
							
							canAttack = false;
							victim = null;
						}
					}
					if (victim!= null && turretAimerBot.overlaps(victim.sprite.getBoundingRectangle())){
						turretAimerBot.x = position.x + victim.sprite.getWidth()/2;
						turretAimerBot.y = position.y + victim.sprite.getHeight()/2;
						
						canAttack = true;
					}
				}
			}
			if(hurt){
				if (time < 40) {
					time++;

				} else if (time > 39) {
					hurt = false;
					time = 0;
				}
			}
		}
		if (projectiles != null && !projectiles.isEmpty()) {
			Iterator<Projectile> itrP = projectiles.iterator();
			while(itrP.hasNext()){
				Projectile p = itrP.next();
				p.update();
				
				if(p.state == State.DEAD){
					itrP.remove();
				}
			}
		}
	}

}
