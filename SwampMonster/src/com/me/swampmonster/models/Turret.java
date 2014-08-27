package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class Turret extends AbstractGameObject {

	public Circle killingAura = new Circle();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public Enemy victimEnemy;
	public Rectangle turretAimerBot;
	public boolean canAttack;
	int counter;
	
	public Turret() {
		sprite = new Sprite(Assets.manager.get(Assets.turretImg));
		
		turretAimerBot = new Rectangle();
	}

	public void update() {
		
		if(victimEnemy==null || victimEnemy.state == State.DEAD){
			for (Enemy e : L1.enemiesOnStage) {
				if (e.oRangeAura.overlaps(killingAura)) {
					
					victimEnemy = e;
					canAttack = false;
				}
			}
		}else{
			float direction_x = victimEnemy.position.x - position.x;
			float direction_y = victimEnemy.position.y - position.y;
			
			float length = (float) Math.sqrt(direction_x * direction_x
					+ direction_y * direction_y);
			direction_x /= length;
			direction_y /= length;
			
			if(counter > 0){
				counter--;
			}
			
			if(canAttack && counter == 0){
				Projectile p = new Projectile(new Vector2(100, 100));
				p.setPosition(new Vector2(position.x + direction_x / 100 - 8,
						position.y + direction_y / 100 - 8));
	
				p.setDirection(direction_x, direction_y);
				p.effect = EffectCarriers.NONE;
				this.projectiles.add(p);
				
				canAttack = false;
				counter = attackSpeed;
				
			}else{
				if (turretAimerBot.x > victimEnemy.getPosition().x - 4
						|| turretAimerBot.x < victimEnemy.getPosition().x - 10
						|| turretAimerBot.y > victimEnemy.getPosition().y - 4
						|| turretAimerBot.y < victimEnemy.getPosition().y - 10) {
					Collidable collidable = CollisionHelper.isCollidable(turretAimerBot.x+5, turretAimerBot.y+5, TheController.collisionLayer);
					if (collidable == null){
						turretAimerBot.x += direction_x * 5;
					}
					if (collidable == null){
						turretAimerBot.y += direction_y * 5;
					}
					if (collidable != null){
						System.out.println("can't aimBot there!");
						turretAimerBot.x = position.x;
						turretAimerBot.y = position.y;
						
						canAttack = false;
					}
				}
				if (turretAimerBot.overlaps(victimEnemy.rectanlge)){
					turretAimerBot.x = position.x;
					turretAimerBot.y = position.y;
					
					canAttack = true;
				}
			}
		}
		
		if (projectiles != null && !projectiles.isEmpty()) {
			for (Projectile p : projectiles) {
				p.update();
			}
		}
	}

}
