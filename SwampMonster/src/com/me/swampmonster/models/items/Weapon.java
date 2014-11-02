package com.me.swampmonster.models.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;

public class Weapon extends Item{
	
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public int damage;
	public int coolDown;
	public int force;
	public String name;
	public Sprite weaponSprite;
	
	// different force
	
	public Weapon(){
		
	}
	
	public void update(TiledMapTileLayer collisionLayer){
		updateProjectiles(collisionLayer);
	}
	
	public void shoot(Vector3 V3point){
		
		float direction_x = L1.player.shotDir.x - L1.player.V3playerPos.x;
		float direction_y = L1.player.shotDir.y - L1.player.V3playerPos.y;
		
		// : TODO This look terrible, make it better bro...
		Projectile p = new Projectile(new Vector2(L1.player.aimingArea.x
				+ direction_x / 100 - 8, L1.player.aimingArea.y + direction_y / 100
				- 8), L1.player.getRotation(L1.player.shotDir), L1.player.arrowEffectCarrier);
		L1.player.shotArrows++;
		
		p.setPosition(new Vector2(L1.player.aimingArea.x + direction_x / 100 - 8,
				L1.player.aimingArea.y + direction_y / 100 - 8));
		
		p.force = (float) Math.sqrt(Math.pow((V3point.x - L1.player.position.x), 2)
				+ Math.pow((V3point.y - L1.player.position.y), 2)) / 50;
		
		float length = (float) Math.sqrt(direction_x * direction_x
				+ direction_y * direction_y);
		direction_x /= length;
		direction_y /= length;
		
		p.setDirection(direction_x, direction_y);
		
		projectiles.add(p);
		if (L1.player.ThreeArrowsFlag) {
			float direction_x2 = L1.player.shotDir.x - 40 - L1.player.V3playerPos.x;
			float direction_y2 = L1.player.shotDir.y - 40 - L1.player.V3playerPos.y;
			float direction_x3 = L1.player.shotDir.x + 48 - L1.player.V3playerPos.x;
			float direction_y3 = L1.player.shotDir.y + 48 - L1.player.V3playerPos.y;
			
			Projectile p2 = new Projectile(new Vector2(L1.player.aimingArea.x
					+ direction_x2 / 100 - 8, L1.player.aimingArea.y + direction_y2
					/ 100 - 8), L1.player.getRotation(new Vector3(L1.player.shotDir.x - 40,
							L1.player.shotDir.y - 40, 0)), L1.player.arrowEffectCarrier);
			Projectile p3 = new Projectile(new Vector2(L1.player.aimingArea.x
					+ direction_x3 / 100 - 8, L1.player.aimingArea.y + direction_y3
					/ 100 - 8), L1.player.getRotation(new Vector3(L1.player.shotDir.x + 48,
							L1.player.shotDir.y + 48, 0)), L1.player.arrowEffectCarrier);
			
			p2.setPosition(new Vector2(L1.player.aimingArea.x + direction_x2 / 100
					- 8, L1.player.aimingArea.y + direction_y2 / 100 - 8));
			p3.setPosition(new Vector2(L1.player.aimingArea.x + direction_x3 / 100
					- 8, L1.player.aimingArea.y + direction_y3 / 100 - 8));
			
			p2.force = (float) Math.sqrt(Math.pow((V3point.x - L1.player.position.x),
					2) + Math.pow((V3point.y - L1.player.position.y), 2)) / 50;
			p3.force = (float) Math.sqrt(Math.pow((V3point.x - L1.player.position.x),
					2) + Math.pow((V3point.y - L1.player.position.y), 2)) / 50;
			
			float length2 = (float) Math.sqrt(direction_x2 * direction_x2
					+ direction_y2 * direction_y2);
			direction_x2 /= length2;
			direction_y2 /= length2;
			float length3 = (float) Math.sqrt(direction_x3 * direction_x3
					+ direction_y3 * direction_y3);
			direction_x3 /= length3;
			direction_y3 /= length3;
			
			p2.setDirection(direction_x2, direction_y2);
			p3.setDirection(direction_x3, direction_y3);
			
			projectiles.add(p2);
			projectiles.add(p3);
			L1.player.ThreeArrowsFlag = false;
		}
		L1.player.arrowEffectCarrier = EffectCarriers.NONE;
		
	}
	
	
	
	private void updateProjectiles(TiledMapTileLayer collisionLayer) {
		Iterator<Projectile> prj = projectiles.iterator();
		while (prj.hasNext()) {
			// System.err.println("player");
			Projectile p = prj.next();
			p.update();
			p.getSurfaceLevelProjectile(collisionLayer);
			if (p.isCollision(collisionLayer)) {
				if (p.effect == EffectCarriers.EXPLOSIVE) {
					TheController.skill.explode(p.position);
				}
				p.state = State.DEAD;
			} else if (L1.plasmaShield != null
					&& p.circle.overlaps(L1.plasmaShield.circle)) {
				if (!L1.plasmaShield.circle.contains(circle)) {
					p.state = State.DEAD;
				}
			} else if (p.isCollisionNBreakable(collisionLayer)
					&& L1.hasAtmosphere) {
				Explosion expl = new Explosion(new Vector2(p.position.x,
						p.position.y), Explosion.EXPLOSION_TYPE_INVERTED);
				L1.explosions.add(expl);
				L1.hasAtmosphere = false;
				p.state = State.DEAD;
			}
			if (p != null && p.state == State.DEAD) {
				prj.remove();
			}
		}
	}

	@Override
	public void pickMeUp(Player player) {
		player.weapon = this;
	}
	

}
