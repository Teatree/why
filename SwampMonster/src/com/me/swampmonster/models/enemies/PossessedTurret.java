package com.me.swampmonster.models.enemies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.Turret;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PossessedTurret extends Turret {

	public static Random random = new Random();
	public int animDespawnTimer = 0;
	
	public PossessedTurret(Vector2 position) {
		super();
		this.position = position;
		victim = L1.player;
		projectiles = new ArrayList<Projectile>();
		animControl = new AnimationControl(Assets.manager.get(Assets.badturretImg), 4, 4, 3.9f);
		minDD = Constants.TURRET_min_Damage_L3;
		maxDD = Constants.TURRET_max_Damage_L3;
		health = Constants.TURRET_Health_L3;
		attackSpeed = Constants.TURRET_AttackSpeed_L3;
		lifeTime = Constants.TURRET_LifeTime_L3;
		killingAura = new Circle(position.x, position.y, 150);
		circle.x = position.x+sprite.getBoundingRectangle().width/2;
		circle.y = position.y+sprite.getBoundingRectangle().height/2;
		circle.radius = sprite.getBoundingRectangle().width / 2;
		state = State.STANDARD;
		
	}

	@Override
	public void update() {

		System.out.println("state " + this.state + " health: " + health);
		// :TODO GET THE ANIAMTIONS RIGHT!
		if (this.state == State.DESPAWNING) {
			System.out.println("state despawning ");
			animDespawnTimer--;
			animControl.doComplexAnimation(8, 1f, 0.02f,
					Animation.PlayMode.NORMAL);
			sprite = new Sprite(animControl.getCurrentFrame());
			if (animDespawnTimer == 0) {
				this.state = State.DEAD;
			}
		}
			if (health <= 0 && state != State.DEAD && state != State.DESPAWNING) {
				animDespawnTimer = 120;
				this.state = State.DESPAWNING;
				// animControl = new
				// AnimationControl(Assets.manager.get(Assets.turretImg), 4, 4,
				// 3.9f);
				// System.out.println("[TURRET] STATE ?DESPAWNING? : " + state);
			}
			if (this.state == State.STANDARD) {
				// state = State.STANDARD;
				animControl.animate(0);
				sprite = new Sprite(animControl.getCurrentFrame());
				// System.out.println("[TURRET] STATE ?STANDARD? : " + state);
			}
		

		for (Projectile p : L1.player.weapon.projectiles) {
			if (p.circle.overlaps(circle) ) {
				hurt = true;
				health -= random
						.nextInt((int) (L1.player.weapon.maxDD - L1.player.weapon.minDD))
						+ L1.player.weapon.minDD;
				p.state = State.DEAD;
				if(state == State.DESPAWNING){
					p.state = State.DEAD;
				}
			}
		}

		if (state == State.STANDARD && victim != null) {
			float direction_x = victim.position.x - position.x;
			float direction_y = victim.position.y - position.y;

			float length = (float) Math.sqrt(direction_x * direction_x
					+ direction_y * direction_y);
			direction_x /= length;
			direction_y /= length;

			if (counter > 0) {
				counter--;
			}

			if (canAttack && counter == 0) {
				Projectile p = new Projectile(new Vector2(100, 100));
				p.sprite = new Sprite(
						Assets.manager.get(Assets.turretProjectile));
				p.setPosition(new Vector2(position.x + p.sprite.getWidth() / 2,
						position.y + p.sprite.getHeight() / 3 * 2));

				p.setDirection(direction_x, direction_y);
				p.effect = EffectCarriers.NONE;
				Projectile.resistance = 0.0008f;
				p.force = 0.8f;
				this.projectiles.add(p);

				canAttack = false;
				counter = attackSpeed;

			} else {
				if (turretAimerBot.x > victim.getPosition().x
						+ victim.sprite.getWidth() / 2 - 4
						|| turretAimerBot.x < victim.getPosition().x
								+ victim.sprite.getWidth() / 2 - 10
						|| turretAimerBot.y > victim.getPosition().y
								+ victim.sprite.getHeight() / 2 - 4
						|| turretAimerBot.y < victim.getPosition().y
								+ victim.sprite.getHeight() / 2 - 10) {
					Collidable collidable = CollisionHelper.isCollidable(
							turretAimerBot.x + 5, turretAimerBot.y + 5,
							TheController.collisionLayer);
					if (collidable == null) {
						turretAimerBot.x += direction_x * 5;
					}
					if (collidable == null) {
						turretAimerBot.y += direction_y * 5;
					}
					if (collidable != null) {
						// System.out.println("can't aimBot there!");
						turretAimerBot.x = position.x
								+ victim.sprite.getWidth() / 2;
						turretAimerBot.y = position.y
								+ victim.sprite.getHeight() / 2;

						canAttack = false;
						victim = null;
						System.out.println(turretAimerBot);
					}
				}
				if (victim != null
						&& turretAimerBot.overlaps(victim.sprite
								.getBoundingRectangle())) {
					turretAimerBot.x = position.x + victim.sprite.getWidth()
							/ 2;
					turretAimerBot.y = position.y + victim.sprite.getHeight()
							/ 2;
					System.out.println("pt can attack");
					canAttack = true;
				}
			}
		}
		if (hurt) {
			if (time < 40) {
				time++;

			} else if (time > 39) {
				hurt = false;
				time = 0;
			}
		}
		if (projectiles != null && !projectiles.isEmpty()) {
			Iterator<Projectile> itrP = projectiles.iterator();
			while (itrP.hasNext()) {
				Projectile p = itrP.next();

				p.update();
				p.force = 0.8f;
				p.getSurfaceLevelProjectile(TheController.collisionLayer);

				if (p.isCollision(TheController.collisionLayer)) {
					p.state = State.DEAD;
				}
				if (L1.plasmaShield != null
						&& p.circle.overlaps(L1.plasmaShield.circle)) {
					if (!L1.plasmaShield.circle.contains(circle)) {
						p.state = State.DEAD;
					}
				}

				if (p.circle.overlaps(L1.player.circle)) {
					L1.player.harmfulTurret = this;
					L1.player.hurt = true;
					L1.player.damageType = "turret";
					L1.player.health -= this.random
							.nextInt((int) (this.maxDD - this.minDD))
							+ this.minDD;
					p.state = State.DEAD;
				}

				if (p.state == State.DEAD) {
					itrP.remove();
				}
			}
		}
		// super.update();

		sprite.setColor(Color.RED);
		sprite.setScale(2);
	}

}
