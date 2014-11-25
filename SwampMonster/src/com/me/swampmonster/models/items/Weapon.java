package com.me.swampmonster.models.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.items.wepMods.Modificator;
import com.me.swampmonster.utils.Assets;

public class Weapon extends AbstractGameObject{
	
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public Projectile p;
	public int minDD; // minimun damage dealt
	public int maxDD; // minimun damage dealt
	public static int randBetVal; 
	public int coolDown;
	public int coolDownCounter;
	public static float coolDownStep;
	public static float coolDownAngle;
	public int force;
	public String name;
	public Sprite weaponDescSprite;
	public Modificator mod1;
	public Modificator mod2;
	Random random = new Random();
	
	// different force
	
	public Weapon(){
		super();
		
		sprite = new Sprite(Assets.manager.get(Assets.wepBOW));
		name = this.getClass().getSimpleName();
		coolDown = 120;
		
	}
	
	public void update(TiledMapTileLayer collisionLayer){
		updateProjectiles(collisionLayer);
		
		coolDownAngle = coolDownAngle - coolDownStep;
		
		if(coolDownCounter>0){
			coolDownCounter--;
			Player.shootingSwitch = false;
		}else{
			Player.shootingSwitch = true;
		}
		
	}
	
	public void shoot(Vector3 V3point){
		
		coolDownAngle = 360;
		coolDownStep = 360f / coolDown;
		coolDownCounter = coolDown;
		float direction_x = L1.player.shotDir.x - L1.player.V3playerPos.x;
		float direction_y = L1.player.shotDir.y - L1.player.V3playerPos.y;
		
		// : TODO This look terrible, make it better bro...
			p = new Projectile(new Vector2(L1.player.aimingArea.x + direction_x
					/ 100 - 8, L1.player.aimingArea.y + direction_y / 100 - 8),
					L1.player.getRotation(L1.player.shotDir),
					L1.player.arrowEffectCarrier, new Sprite(
							Assets.manager.get(Assets.arrow)));
		if(L1.player.arrowEffectCarrier!=EffectCarriers.NONE){
			p.sprite = L1.player.arrowEffectCarrier.sprite;
		}
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
		
//		int shouldApplyMod1 = random.nextInt(100);
		if (mod1 != null){
			mod1.applyModificator(p);
		} else {
//			int shouldApplyMod2 = random.nextInt(100);
			if (mod2 != null){
				mod2.applyModificator(p);
			}
		}
		projectiles.add(p);
		
		if (L1.player.ThreeArrowsFlag) {
			float direction_x2 = L1.player.shotDir.x - 40 - L1.player.V3playerPos.x;
			float direction_y2 = L1.player.shotDir.y - 40 - L1.player.V3playerPos.y;
			float direction_x3 = L1.player.shotDir.x + 48 - L1.player.V3playerPos.x;
			float direction_y3 = L1.player.shotDir.y + 48 - L1.player.V3playerPos.y;
			
			Projectile p2 = new Projectile(new Vector2(L1.player.aimingArea.x
					+ direction_x2 / 100 - 8, L1.player.aimingArea.y + direction_y2
					/ 100 - 8), L1.player.getRotation(new Vector3(L1.player.shotDir.x - 40,
							L1.player.shotDir.y - 40, 0)), L1.player.arrowEffectCarrier, new Sprite(
									Assets.manager.get(Assets.arrow)));
			Projectile p3 = new Projectile(new Vector2(L1.player.aimingArea.x
					+ direction_x3 / 100 - 8, L1.player.aimingArea.y + direction_y3
					/ 100 - 8), L1.player.getRotation(new Vector3(L1.player.shotDir.x + 48,
							L1.player.shotDir.y + 48, 0)), L1.player.arrowEffectCarrier, new Sprite(
									Assets.manager.get(Assets.arrow)));
			
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
	public String getName(){
		String resultName; 
		if(mod1!=null){
			resultName = mod1.name + " " + this.name;
		}else if(mod2!=null){
			resultName = mod2.name + " " + this.name;
		}else{
			resultName = this.name;
		}
		return resultName;
	}
	private void updateProjectiles(TiledMapTileLayer collisionLayer) {
		Iterator<Projectile> prj = projectiles.iterator();
		while (prj.hasNext()) {
			// System.err.println("player");
			Projectile p = prj.next();
			p.update();
			p.getSurfaceLevelProjectile(collisionLayer);
			if (p.isCollision(collisionLayer) && !p.effect.equals(EffectCarriers.SHADOW)) {
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
			if (EffectCarriers.SHADOW.equals(p.effect) && 
					(p.position.x <= 0 ||
					p.position.x  + sprite.getWidth() >= TheController.collisionLayer.getWidth()*TheController.collisionLayer.getTileWidth() ||
					p.position.y <= 0 ||
					p.position.y + sprite.getHeight() >= TheController.collisionLayer.getHeight()*TheController.collisionLayer.getTileHeight())){
				p.state = State.DEAD;
			}
			if (p != null && p.state == State.DEAD) {
				prj.remove();
			}
		}
	}
	public void setStats(int playerScore){
		
	}
}
