package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class Turret extends AbstractGameObject {

	public Circle killingAura = new Circle();
	public List<Projectile> projectiles = new ArrayList<Projectile>();

	public Turret() {
		sprite = new Sprite(Assets.manager.get(Assets.turretImg));

	}

	public void update() {
		for (Enemy e : L1.enemiesOnStage) {
			if (e.oRangeAura.overlaps(killingAura)) {
				float direction_x = e.position.x - position.x;
				float direction_y = e.position.y - position.y;

				Projectile p = new Projectile(new Vector2(100, 100));
				p.setPosition(new Vector2(position.x + direction_x / 100 - 8,
						position.y + direction_y / 100 - 8));

				float length = (float) Math.sqrt(direction_x * direction_x
						+ direction_y * direction_y);
				direction_x /= length;
				direction_y /= length;

				p.setDirection(direction_x, direction_y);
				p.effect = EffectCarriers.NONE;
				this.projectiles.add(p);

			}
		}
		if (projectiles != null && !projectiles.isEmpty()) {
			for (Projectile p : projectiles) {
				p.update();
			}
		}
	}

}
