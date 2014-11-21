package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;

public class Spear extends Weapon{

	public Spear() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.spearDesc));
		
		coolDown = 1;
		setStats(L1.player.absoluteScore);
	}
	
	@Override
	public void shoot(Vector3 V3point) {
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
						Assets.manager.get(Assets.spear)));
		if(L1.player.arrowEffectCarrier!=EffectCarriers.NONE){
			p.sprite = L1.player.arrowEffectCarrier.sprite;
		}
		L1.player.shotArrows++;
		
		p.setPosition(new Vector2(L1.player.aimingArea.x + direction_x / 100 - 8,
				L1.player.aimingArea.y + direction_y / 100 - 8));
		
		p.force = (float) Math.sqrt(Math.pow((V3point.x - L1.player.position.x), 2)
				+ Math.pow((V3point.y - L1.player.position.y), 2)) / 120;
		
		float length = (float) Math.sqrt(direction_x * direction_x
				+ direction_y * direction_y);
		direction_x /= length;
		direction_y /= length;
		
		p.setDirection(direction_x, direction_y);
		
		int shouldApplyMod1 = random.nextInt(1000);
		if (mod1 != null && shouldApplyMod1 < mod1.probability){
			mod1.applyModificator(p);
		} else {
			int shouldApplyMod2 = random.nextInt(1000);
			if (mod2 != null && shouldApplyMod2 < mod2.probability){
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
									Assets.manager.get(Assets.spear)));
			Projectile p3 = new Projectile(new Vector2(L1.player.aimingArea.x
					+ direction_x3 / 100 - 8, L1.player.aimingArea.y + direction_y3
					/ 100 - 8), L1.player.getRotation(new Vector3(L1.player.shotDir.x + 48,
							L1.player.shotDir.y + 48, 0)), L1.player.arrowEffectCarrier, new Sprite(
									Assets.manager.get(Assets.spear)));
			
			p2.setPosition(new Vector2(L1.player.aimingArea.x + direction_x2 / 100
					- 8, L1.player.aimingArea.y + direction_y2 / 100 - 8));
			p3.setPosition(new Vector2(L1.player.aimingArea.x + direction_x3 / 100
					- 8, L1.player.aimingArea.y + direction_y3 / 100 - 8));
			
			p2.force = (float) Math.sqrt(Math.pow((V3point.x - L1.player.position.x),
					2) + Math.pow((V3point.y - L1.player.position.y), 2)) / 80;
			p3.force = (float) Math.sqrt(Math.pow((V3point.x - L1.player.position.x),
					2) + Math.pow((V3point.y - L1.player.position.y), 2)) / 80;
			
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

	@Override
	public void setStats(int playerScore) {

		if(playerScore>=0 && playerScore<500){
			randBetVal = random.nextInt(16-10)+10;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(3)+1;
		}
		else if(playerScore>=500 && playerScore<1500){
			randBetVal = random.nextInt(19-13)+13;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(3)+1;
		}
		else if(playerScore>=1500 && playerScore<3000){
			randBetVal = random.nextInt(22-16)+16;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(3)+1;
		}
		else if(playerScore>=3000){
			randBetVal = random.nextInt(25-19)+19;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(3)+1;
		}
	}
	
}
