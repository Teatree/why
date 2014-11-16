package com.me.swampmonster.models.enemies;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.Turret;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PossessedTurret extends Turret {

	public PossessedTurret(Vector2 position) {
		super();
		this.position = position;
		victim = L1.player;
		minDD = Constants.TURRET_min_Damage_L3;
		maxDD = Constants.TURRET_max_Damage_L3;
		health = Constants.TURRET_Health_L3;
		attackSpeed = Constants.TURRET_AttackSpeed_L3;
		lifeTime = Constants.TURRET_LifeTime_L3;
		killingAura = new Circle(position.x, position.y, 150);
	}

	public void update() {

		if (!animControl.animating) {
			animControl.stateTimeDoComplex = 0f;
		}

		// :TODO GET THE ANIAMTIONS RIGHT!
		if (state == State.DEAD) {
			animControl.stateTimeDoComplex = 0f;
		} else if (health <= 0) {
			state = State.DESPAWNING;
			// animControl = new
			// AnimationControl(Assets.manager.get(Assets.turretImg), 4, 4,
			// 3.9f);
			animControl.doComplexAnimation(8, 1f, 0.02f,
					Animation.PlayMode.NORMAL);
			animControl.animating = true;
			sprite = new Sprite(animControl.getCurrentFrame());
//			System.out.println("[TURRET] STATE ?DESPAWNING? : " + state);
		} else {
			state = State.STANDARD;
			animControl.animate(0);
			sprite = new Sprite(animControl.getCurrentFrame());
//			System.out.println("[TURRET] STATE ?STANDARD? : " + state);
		}
		if (state == State.DESPAWNING) {
			// Aniamte
		}

		System.out.println("pt: victim " + victim);
		if (state == State.STANDARD && victim !=null) {
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
				p.force = 1f;
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
					turretAimerBot.x = position.x
							+ victim.sprite.getWidth() / 2;
					turretAimerBot.y = position.y
							+ victim.sprite.getHeight() / 2;
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

				if (p.state == State.DEAD) {
					itrP.remove();
				}
			}
		}
		// super.update();

		sprite.setColor(Color.RED);
		sprite.setScale(2);
		state = State.STANDARD;
	}

}
